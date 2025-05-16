package mx.itson.usuariologin.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mx.itson.usuariologin.R;
import mx.itson.usuariologin.models.ResponseModel;
import mx.itson.usuariologin.network.RetrofitClient;
import mx.itson.usuariologin.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombres, etApellidos, etCorreo, etContrasena, etTelefono;
    private Button btnRegistrar, btnRegresar;
    private ImageButton btnMostrarContrasena;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        etTelefono = findViewById(R.id.etTelefono);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnMostrarContrasena = findViewById(R.id.btnMostrarContrasena);

        btnRegistrar.setOnClickListener(view -> registrarUsuario());

        // Listener para mostrar/ocultar contraseña
        btnMostrarContrasena.setOnClickListener(view -> {
            if (isPasswordVisible) {
                // Ocultar contraseña
                etContrasena.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btnMostrarContrasena.setImageResource(android.R.drawable.ic_menu_view); // icono para mostrar
            } else {
                // Mostrar contraseña
                etContrasena.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                btnMostrarContrasena.setImageResource(android.R.drawable.ic_menu_close_clear_cancel); // icono para ocultar
            }
            isPasswordVisible = !isPasswordVisible;

            // Mantener el cursor al final del texto
            etContrasena.setSelection(etContrasena.getText().length());
        });

        // Listener para regresar a la vista de login
        btnRegresar.setOnClickListener(view -> {
            // Simplemente finaliza esta actividad y regresa a la anterior (login)
            finish();
        });
    }

    private void registrarUsuario() {
        String nombres = etNombres.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();
        String contrasena = etContrasena.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();
        String role = "cliente"; // puedes cambiarlo si se requiere otro rol

        if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseModel> call = apiService.registrarUsuario(nombres, apellidos, correo, contrasena, telefono, role);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseModel result = response.body();
                    Toast.makeText(RegisterActivity.this, result.getMessage(), Toast.LENGTH_LONG).show();

                    if (result.isSuccess()) {
                        finish(); // Finaliza esta actividad (puedes volver al login)
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

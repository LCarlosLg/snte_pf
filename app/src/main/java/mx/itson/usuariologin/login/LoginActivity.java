package mx.itson.usuariologin.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;
import mx.itson.usuariologin.R;
import mx.itson.usuariologin.models.UsuarioModel;
import mx.itson.usuariologin.network.ApiService;
import mx.itson.usuariologin.network.RetrofitClient;
import mx.itson.usuariologin.pantallaCliente.ClienteActivity;
import mx.itson.usuariologin.pantallaEmpleado.EmpleadoActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo, edContrasenia;
    private CheckBox cbEmpleado, cbCliente;
    private Button btnIniciar;
    private TextView tvRegistro;
    private ImageButton btnMostrarContraseña;

    private boolean mostrarContrasenia = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etCorreo);
        edContrasenia = findViewById(R.id.edContrasenia);
        cbEmpleado = findViewById(R.id.cbEmpleado);
        cbCliente = findViewById(R.id.cbCliente);
        btnIniciar = findViewById(R.id.btnIniciar);
        tvRegistro = findViewById(R.id.tvRegistro);
        btnMostrarContraseña = findViewById(R.id.btnMostrarContraseña);

        btnMostrarContraseña.setImageResource(R.drawable.ic_eye_off);

        btnMostrarContraseña.setOnClickListener(v -> {
            if (mostrarContrasenia) {
                edContrasenia.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btnMostrarContraseña.setImageResource(R.drawable.ic_eye_off);
            } else {
                edContrasenia.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                btnMostrarContraseña.setImageResource(R.drawable.ic_eye);
            }
            mostrarContrasenia = !mostrarContrasenia;
            edContrasenia.setSelection(edContrasenia.getText().length()); // Mantener cursor al final
        });

        btnIniciar.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String contrasena = edContrasenia.getText().toString().trim();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor complete los campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!cbEmpleado.isChecked() && !cbCliente.isChecked()) {
                Toast.makeText(LoginActivity.this, "Seleccione un rol: Empleado o Cliente.", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<List<UsuarioModel>> call = apiService.obtenerUsuarios();

            call.enqueue(new Callback<List<UsuarioModel>>() {
                @Override
                public void onResponse(Call<List<UsuarioModel>> call, Response<List<UsuarioModel>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<UsuarioModel> usuarios = response.body();

                        // Buscar usuario por correo
                        UsuarioModel usuarioEncontrado = null;
                        for (UsuarioModel usuario : usuarios) {
                            if (usuario.getEmail() != null && usuario.getEmail().equalsIgnoreCase(correo)) {
                                usuarioEncontrado = usuario;
                                break;
                            }
                        }

                        if (usuarioEncontrado == null) {
                            Toast.makeText(LoginActivity.this, "Correo no registrado.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Validar contraseña con bcrypt
                        if (!verificarContrasena(contrasena, usuarioEncontrado.getPassword().trim())) {
                            Toast.makeText(LoginActivity.this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Validar rol
                        String roleUsuario = usuarioEncontrado.getRole();
                        if (cbEmpleado.isChecked() && roleUsuario.equalsIgnoreCase("empleado")) {
                            startActivity(new Intent(LoginActivity.this, EmpleadoActivity.class));
                            finish();
                        } else if (cbCliente.isChecked() && roleUsuario.equalsIgnoreCase("cliente")) {
                            startActivity(new Intent(LoginActivity.this, ClienteActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "El rol no coincide con el usuario.", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(LoginActivity.this, "Error al obtener usuarios.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<UsuarioModel>> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        tvRegistro.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private boolean verificarContrasena(String passwordPlano, String hashBcrypt) {
        BCrypt.Result result = BCrypt.verifyer().verify(passwordPlano.toCharArray(), hashBcrypt);
        return result.verified;
    }
}

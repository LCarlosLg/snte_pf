package mx.itson.usuariologin.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mx.itson.usuariologin.R;
import mx.itson.usuariologin.pantallaCliente.ClienteActivity;
import mx.itson.usuariologin.pantallaEmpleado.EmpleadoActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo, edContrasenia;
    private CheckBox cbEmpleado, cbCliente;
    private Button btnIniciar, btnGestionarUsuarios;
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
                edContrasenia.setInputType(129);
                btnMostrarContraseña.setImageResource(R.drawable.ic_eye_off);
            } else {
                edContrasenia.setInputType(1);
                btnMostrarContraseña.setImageResource(R.drawable.ic_eye);
            }
            mostrarContrasenia = !mostrarContrasenia;
        });

        btnIniciar.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String contrasena = edContrasenia.getText().toString().trim();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor complete los campos.", Toast.LENGTH_SHORT).show();
            } else {
                // Verificar usuario (ejemplo, implementar validación en la base de datos)
                if (cbEmpleado.isChecked()) {
                    startActivity(new Intent(LoginActivity.this, EmpleadoActivity.class));
                } else if (cbCliente.isChecked()) {
                    startActivity(new Intent(LoginActivity.this, ClienteActivity.class));
                }
            }
        });


        tvRegistro.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }
}

package mx.itson.usuariologin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pantallaCliente.ClienteActivity;
import pantallaEmpleado.EmpleadoActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo, edContrasenia;
    private CheckBox cbEmpleado, cbCliente;
    private Button btnIniciar;
    private TextView tvRegistro;  // Cambié el tipo a TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Asignación de los elementos de la interfaz
        etCorreo = findViewById(R.id.etCorreo);
        edContrasenia = findViewById(R.id.edContrasenia);
        cbEmpleado = findViewById(R.id.cbEmpleado);
        cbCliente = findViewById(R.id.cbCliente);

        btnIniciar = findViewById(R.id.btnIniciar);
        tvRegistro = findViewById(R.id.tvRegistro);  // Actualicé la referencia

        // Evento para el botón de inicio de sesión
        btnIniciar.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String contrasena = edContrasenia.getText().toString().trim();

            // Validación de los campos
            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor complete los campos.", Toast.LENGTH_SHORT).show();
            } else {
                // Aquí podrías añadir lógica de autenticación (como verificar las credenciales en una base de datos)

                // Simulación de inicio de sesión exitoso
                if (cbEmpleado.isChecked()) {
                    Toast.makeText(LoginActivity.this, "Bienvenido, empleado.", Toast.LENGTH_SHORT).show();
                    // Redirigir a la pantalla de empleado
                    startActivity(new Intent(LoginActivity.this, EmpleadoActivity.class));
                } else if (cbCliente.isChecked()) {
                    Toast.makeText(LoginActivity.this, "Bienvenido, cliente.", Toast.LENGTH_SHORT).show();
                    // Redirigir a la pantalla de cliente
                    startActivity(new Intent(LoginActivity.this, ClienteActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Por favor, seleccione un rol.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Evento para el TextView "Regístrate aquí"
        tvRegistro.setOnClickListener(v -> {
            // Redirigir a la actividad de registro
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}

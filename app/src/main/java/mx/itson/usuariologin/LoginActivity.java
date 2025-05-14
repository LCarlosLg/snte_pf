package mx.itson.usuariologin;

import android.content.Intent;
import android.os.Bundle;
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
    private TextView tvRegistro; // Cambiado de Button a TextView

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
        tvRegistro = findViewById(R.id.tvRegistro); // Correcto ahora

        // Evento para el botón de inicio de sesión
        btnIniciar.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String contrasena = edContrasenia.getText().toString().trim();

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Por favor complete los campos.", Toast.LENGTH_SHORT).show();
            } else {
                if (cbEmpleado.isChecked()) {
                    Toast.makeText(LoginActivity.this, "Bienvenido, empleado.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, EmpleadoActivity.class));
                } else if (cbCliente.isChecked()) {
                    Toast.makeText(LoginActivity.this, "Bienvenido, cliente.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, ClienteActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Por favor, seleccione un rol.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Evento para el texto "Regístrate aquí"
        tvRegistro.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}

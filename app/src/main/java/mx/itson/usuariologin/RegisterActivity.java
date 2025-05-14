package mx.itson.usuariologin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText edNombres, edApellidos, edCorreo, edContraseña, edTelefono;
    private Button btnRegistro, btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Asignación de los elementos de la interfaz
        edNombres = findViewById(R.id.edNombres);
        edApellidos = findViewById(R.id.edApellidos);
        edCorreo = findViewById(R.id.edCorreo);
        edContraseña = findViewById(R.id.edContraseña);
        edTelefono = findViewById(R.id.edTelefono);

        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Evento para el botón de registro
        btnRegistro.setOnClickListener(v -> {
            String nombres = edNombres.getText().toString().trim();
            String apellidos = edApellidos.getText().toString().trim();
            String correo = edCorreo.getText().toString().trim();
            String contrasena = edContraseña.getText().toString().trim();
            String telefono = edTelefono.getText().toString().trim();

            // Validación de los campos
            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || telefono.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            } else {
                // Simulación de registro exitoso
                Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish();  // Cerrar la actividad actual
            }
        });

        // Evento para el botón de regresar
        btnRegresar.setOnClickListener(v -> finish());  // Regresar a la actividad anterior
    }
}

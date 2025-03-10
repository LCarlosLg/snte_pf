package mx.itson.usuariologin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Registro");
        getSupportActionBar().setTitle("Registro");

        // Habilitar el botón de retroceso en la barra de acción (ActionBar)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Configurar el botón de regresar
        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear un Intent para regresar a LoginActivity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Cierra RegisterActivity para evitar regresar a esta pantalla con el botón de atrás
            }
        });

        // Configurar el botón de registrarse
        Button btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener los valores de los campos del formulario
                String nombres = ((EditText) findViewById(R.id.edNombres)).getText().toString();
                String apellidos = ((EditText) findViewById(R.id.edApellidos)).getText().toString();
                String correo = ((EditText) findViewById(R.id.edCorreo)).getText().toString();
                String contrasena = ((EditText) findViewById(R.id.edContraseña)).getText().toString();
                String telefono = ((EditText) findViewById(R.id.edTelefono)).getText().toString();

                // Verificar que los campos no estén vacíos
                if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || telefono.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
                } else {
                    // Simulación de registro exitoso
                    Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                    // Después del registro exitoso, redirigir a la actividad de inicio de sesión
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Cierra RegisterActivity para evitar regresar a esta pantalla con el botón de atrás
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Comportamiento al presionar el botón de retroceso en la barra de acción
        if(item.getItemId() == android.R.id.home) {
            finish(); // Cierra RegisterActivity cuando se presiona el botón de retroceso
        }
        return super.onOptionsItemSelected(item);
    }
}

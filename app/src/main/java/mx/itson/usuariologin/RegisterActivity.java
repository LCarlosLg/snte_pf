package mx.itson.usuariologin;

import android.os.Bundle;
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

        edNombres = findViewById(R.id.edNombres);
        edApellidos = findViewById(R.id.edApellidos);
        edCorreo = findViewById(R.id.edCorreo);
        edContraseña = findViewById(R.id.edContraseña);
        edTelefono = findViewById(R.id.edTelefono);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnRegistro.setOnClickListener(v -> {
            if (edNombres.getText().toString().trim().isEmpty()
                    || edApellidos.getText().toString().trim().isEmpty()
                    || edCorreo.getText().toString().trim().isEmpty()
                    || edContraseña.getText().toString().trim().isEmpty()
                    || edTelefono.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnRegresar.setOnClickListener(v -> finish());
    }
}

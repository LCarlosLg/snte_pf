package mx.itson.usuariologin;



import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo, etContraseña;
    private ImageView ivShowPassword;
    private CheckBox cbEmpleado, cbCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Iniciar sesión");
        getSupportActionBar().setTitle("Iniciar sesión");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inicializar elementos de la UI
        etCorreo = findViewById(R.id.etCorreo);
        etContraseña = findViewById(R.id.edContrasenia);
        ivShowPassword = findViewById(R.id.ivShowPassword);
        Button btnIniciar = findViewById(R.id.btnIniciar);
        Button btnRegistrar = findViewById(R.id.tvRegistro);
        cbEmpleado = findViewById(R.id.cbEmpleado);
        cbCliente = findViewById(R.id.cbCliente);

        ivShowPassword.setImageResource(R.drawable.ic_eye_off);

        ivShowPassword.setOnClickListener(view -> {
            if (etContraseña.getTransformationMethod().equals(android.text.method.PasswordTransformationMethod.getInstance())) {
                etContraseña.setTransformationMethod(null);
                ivShowPassword.setImageResource(R.drawable.ic_eye);
            } else {
                etContraseña.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                ivShowPassword.setImageResource(R.drawable.ic_eye_off);
            }
        });

        btnIniciar.setOnClickListener(view -> {
            String correo = etCorreo.getText().toString();
            String contrasena = etContraseña.getText().toString();
            boolean isEmpleado = cbEmpleado.isChecked();
            boolean isCliente = cbCliente.isChecked();

            if (correo.isEmpty() || contrasena.isEmpty() || (!isEmpleado && !isCliente)) {
                Toast.makeText(LoginActivity.this, "Por favor complete todos los campos y seleccione un rol.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();

                if (isCliente) {
                    Intent intent = new Intent(LoginActivity.this, pantallaCliente.ClienteActivity.class);
                    startActivity(intent);
                } else if (isEmpleado) {
                    Intent intent = new Intent(LoginActivity.this, pantallaEmpleado.EmpleadoActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        });

        btnRegistrar.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}


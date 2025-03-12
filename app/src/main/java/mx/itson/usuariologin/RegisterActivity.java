package mx.itson.usuariologin;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText edContraseña;
    private ImageView ivShowPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Registro");
        getSupportActionBar().setTitle("Registro");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Botón de regresar

        // Elementos de UI
        Button btnRegresar = findViewById(R.id.btnRegresar);
        Button btnRegistro = findViewById(R.id.btnRegistro);
        edContraseña = findViewById(R.id.edContraseña);
        ivShowPassword = findViewById(R.id.ivShowPassword);

        // Inicializar el ícono como cerrado
        ivShowPassword.setImageResource(R.drawable.ic_eye_off);

        // Botón de regresar
        btnRegresar.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });

        // Botón de registro
        btnRegistro.setOnClickListener(view -> {
            String nombres = ((EditText) findViewById(R.id.edNombres)).getText().toString();
            String apellidos = ((EditText) findViewById(R.id.edApellidos)).getText().toString();
            String correo = ((EditText) findViewById(R.id.edCorreo)).getText().toString();
            String contrasena = edContraseña.getText().toString();
            String telefono = ((EditText) findViewById(R.id.edTelefono)).getText().toString();

            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || telefono.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            } else {
                // Encriptar la contraseña
                String hashedPassword = hashPassword(contrasena);

                // Simulación de registro exitoso
                Toast.makeText(RegisterActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                // Redirigir a LoginActivity
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        // Mostrar/Ocultar contraseña
        ivShowPassword.setOnClickListener(view -> {
            if (edContraseña.getTransformationMethod().equals(android.text.method.PasswordTransformationMethod.getInstance())) {
                // Mostrar contraseña (cambiar a ojo abierto)
                edContraseña.setTransformationMethod(null);
                ivShowPassword.setImageResource(R.drawable.ic_eye); // Ojo abierto
            } else {
                // Ocultar contraseña (cambiar a ojo cerrado)
                edContraseña.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                ivShowPassword.setImageResource(R.drawable.ic_eye_off); // Ojo cerrado
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // metodo para encriptar contraseñas con SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}

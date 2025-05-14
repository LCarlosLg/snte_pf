package mx.itson.usuariologin.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

import mx.itson.usuariologin.R;
import mx.itson.usuariologin.db.UsuarioDBHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText edNombres, edApellidos, edCorreo, edContraseña, edConfirmarContrasena, edTelefono;
    private Button btnRegistro, btnRegresar;
    private ImageButton btnMostrarContraseña, btnMostrarConfirmarContraseña;

    private static final Pattern CONTRASENA_SEGURA = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$");
    private static final Pattern SOLO_NUMEROS = Pattern.compile("^\\d{10}$");

    // Definir la instancia de UsuarioDBHelper
    private UsuarioDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edNombres = findViewById(R.id.edNombres);
        edApellidos = findViewById(R.id.edApellidos);
        edCorreo = findViewById(R.id.edCorreo);
        edContraseña = findViewById(R.id.edContraseña);
        edConfirmarContrasena = findViewById(R.id.edConfirmarContrasena);
        edTelefono = findViewById(R.id.edTelefono);

        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnMostrarContraseña = findViewById(R.id.btnMostrarContraseña);
        btnMostrarConfirmarContraseña = findViewById(R.id.btnMostrarConfirmarContraseña);

        // Inicializa la instancia de dbHelper
        dbHelper = new UsuarioDBHelper(this);

        // Mostrar/Ocultar Contraseña
        btnMostrarContraseña.setOnClickListener(v -> togglePasswordVisibility(edContraseña, btnMostrarContraseña));

        // Mostrar/Ocultar Confirmar Contraseña
        btnMostrarConfirmarContraseña.setOnClickListener(v -> togglePasswordVisibility(edConfirmarContrasena, btnMostrarConfirmarContraseña));

        btnRegistro.setOnClickListener(v -> {
            String nombres = edNombres.getText().toString().trim();
            String apellidos = edApellidos.getText().toString().trim();
            String correo = edCorreo.getText().toString().trim();
            String contrasena = edContraseña.getText().toString().trim();
            String confirmar = edConfirmarContrasena.getText().toString().trim();
            String telefono = edTelefono.getText().toString().trim();

            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || confirmar.isEmpty() || telefono.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos.", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                Toast.makeText(this, "Ingrese un correo electrónico válido.", Toast.LENGTH_SHORT).show();
            } else if (!CONTRASENA_SEGURA.matcher(contrasena).matches()) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres, una mayúscula, una minúscula y un número.", Toast.LENGTH_LONG).show();
            } else if (!contrasena.equals(confirmar)) {
                Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            } else if (!SOLO_NUMEROS.matcher(telefono).matches()) {
                Toast.makeText(this, "Ingrese un número de teléfono válido de 10 dígitos.", Toast.LENGTH_SHORT).show();
            } else {
                // Verificar si el correo ya existe
                if (dbHelper.correoExiste(correo)) {
                    Toast.makeText(this, "Este correo ya está registrado.", Toast.LENGTH_SHORT).show();
                } else {
                    // Insertar el nuevo usuario
                    dbHelper.insertarUsuario(nombres, apellidos, correo, contrasena, telefono);
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
            }
        });

        btnRegresar.setOnClickListener(v -> finish());
    }

    // Método para alternar la visibilidad de la contraseña
    private void togglePasswordVisibility(EditText editText, ImageButton button) {
        if (editText.getInputType() == 129) {  // 129 = textPassword
            editText.setInputType(1);  // 1 = textVisible
            button.setImageResource(android.R.drawable.ic_menu_close_clear_cancel); // Ojo abierto
        } else {
            editText.setInputType(129);  // textPassword
            button.setImageResource(android.R.drawable.ic_menu_view); // Ojo cerrado
        }
    }
}

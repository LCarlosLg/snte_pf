package pantallaEmpleado;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import mx.itson.usuariologin.R;

public class EmpleadoActivity extends Activity {

    private Button btnInventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleado);

        btnInventario = findViewById(R.id.btnInventario);

        btnInventario.setOnClickListener(v -> {
            Intent intent = new Intent(EmpleadoActivity.this, InventarioActivity.class);
            startActivity(intent);
        });
    }
}

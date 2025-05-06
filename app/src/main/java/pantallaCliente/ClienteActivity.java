package pantallaCliente;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import mx.itson.usuariologin.R;

public class ClienteActivity extends AppCompatActivity {

    private ListView lvProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        lvProductos = findViewById(R.id.lvProductos);

        List<ProductoModel> productosmodel = new ArrayList<>();
        productosmodel.add(new ProductoModel(1, "Sofá", 7999.99, 5, "Mueblería"));
        productosmodel.add(new ProductoModel(2, "Refrigerador", 12499.99, 2, "Electrodomésticos"));
        productosmodel.add(new ProductoModel(3, "Silla", 999.99, 20, "Mueblería"));
        productosmodel.add(new ProductoModel(4, "Microondas", 2499.50, 10, "Electrodomésticos"));

        ProductoAdapter adapter = new ProductoAdapter(this, productosmodel);
        lvProductos.setAdapter(adapter);
    }
}


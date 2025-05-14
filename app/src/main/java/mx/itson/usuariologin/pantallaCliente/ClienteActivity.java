package mx.itson.usuariologin.pantallaCliente;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mx.itson.usuariologin.R;

public class ClienteActivity extends AppCompatActivity {

    private ArrayList<ProductoModel> carrito = new ArrayList<>();
    private RecyclerView rvProductos;
    private Button btnEliminarItem, btnVaciarCarrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        // Referencias a la vista
        rvProductos = findViewById(R.id.rvProductos);
        btnEliminarItem = findViewById(R.id.btnEliminarItem);
        btnVaciarCarrito = findViewById(R.id.btnVaciarCarrito);

        // Lista simulada de productos disponibles
        List<ProductoModel> listaProductos = new ArrayList<>();
        listaProductos.add(new ProductoModel(1, "Sofá", 7999.99, 5, "Mueblería"));
        listaProductos.add(new ProductoModel(2, "Refrigerador", 12499.99, 2, "Electrodomésticos"));
        listaProductos.add(new ProductoModel(3, "Silla", 999.99, 20, "Mueblería"));
        listaProductos.add(new ProductoModel(4, "Microondas", 2499.50, 10, "Electrodomésticos"));

        // Adaptador con listener para agregar al carrito
        ProductoAdapter adapter = new ProductoAdapter(this, listaProductos, producto -> {
            carrito.add(producto);
            Toast.makeText(this, producto.nombre + " agregado al carrito", Toast.LENGTH_SHORT).show();
        });

        rvProductos.setLayoutManager(new LinearLayoutManager(this));
        rvProductos.setAdapter(adapter);

        // Botón: eliminar último item del carrito
        btnEliminarItem.setOnClickListener(v -> {
            if (!carrito.isEmpty()) {
                ProductoModel eliminado = carrito.remove(carrito.size() - 1);
                Toast.makeText(this, "Eliminado: " + eliminado.nombre, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Carrito vacío", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón: vaciar todo el carrito
        btnVaciarCarrito.setOnClickListener(v -> {
            if (!carrito.isEmpty()) {
                carrito.clear();
                Toast.makeText(this, "Carrito vaciado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El carrito ya está vacío", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

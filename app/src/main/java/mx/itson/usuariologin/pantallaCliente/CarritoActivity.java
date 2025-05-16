package mx.itson.usuariologin.pantallaCliente;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mx.itson.usuariologin.R;
import mx.itson.usuariologin.models.ProductoModel;

public class CarritoActivity extends AppCompatActivity {

    private RecyclerView rvCarrito;
    private Button btnPagar, btnRegresar;
    private TextView tvTotal;
    private ArrayList<ProductoModel> carrito;
    private CarritoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        rvCarrito = findViewById(R.id.rvCarrito);
        btnPagar = findViewById(R.id.btnPagar);
        btnRegresar = findViewById(R.id.btnRegresar);
        tvTotal = findViewById(R.id.tvTotal);

        carrito = (ArrayList<ProductoModel>) getIntent().getSerializableExtra("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        rvCarrito.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CarritoAdapter(this, carrito, this::actualizarTotal);
        rvCarrito.setAdapter(adapter);

        actualizarTotal();

        btnRegresar.setOnClickListener(v -> finish());

        btnPagar.setOnClickListener(v -> {
            Toast.makeText(this, "Función de pago pendiente", Toast.LENGTH_SHORT).show();
        });
    }

    private void actualizarTotal() {
        double total = 0.0;
        for (ProductoModel producto : carrito) {
            total += producto.getPrecio();
        }
        tvTotal.setText(String.format("Total: $%.2f", total));
    }
}

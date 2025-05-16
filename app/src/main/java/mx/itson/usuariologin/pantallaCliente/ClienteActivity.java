package mx.itson.usuariologin.pantallaCliente;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mx.itson.usuariologin.login.LoginActivity;
import mx.itson.usuariologin.R;
import mx.itson.usuariologin.models.ProductoModel;
import mx.itson.usuariologin.network.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteActivity extends AppCompatActivity {

    private ArrayList<ProductoModel> carrito = new ArrayList<>();
    private RecyclerView rvProductos;
    private Button btnVaciarCarrito, btnVerCarrito, btnCerrarSesion;
    private ProductoAdapter adapter;

    private static final String BASE_URL = "https://snte.store/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);

        rvProductos = findViewById(R.id.rvProductos);
        btnVaciarCarrito = findViewById(R.id.btnVaciarCarrito);
        btnVerCarrito = findViewById(R.id.btnVerCarrito);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        rvProductos.setLayoutManager(new LinearLayoutManager(this));

        carrito = new ArrayList<>();

        adapter = new ProductoAdapter(this, new ArrayList<>(), producto -> {
            carrito.add(producto);
            Toast.makeText(this, producto.getNombre() + " agregado al carrito", Toast.LENGTH_SHORT).show();
        });
        rvProductos.setAdapter(adapter);

        cargarProductosDesdeApi();

        btnVaciarCarrito.setOnClickListener(v -> {
            if (!carrito.isEmpty()) {
                carrito.clear();
                Toast.makeText(this, "Carrito vaciado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El carrito ya está vacío", Toast.LENGTH_SHORT).show();
            }
        });

        btnVerCarrito.setOnClickListener(v -> {
            Intent intent = new Intent(ClienteActivity.this, CarritoActivity.class);
            intent.putExtra("carrito", carrito); // Aquí pasamos el carrito serializable
            startActivity(intent);
        });

        btnCerrarSesion.setOnClickListener(v -> {
            // Regresar a pantalla login y limpiar pila de actividades para evitar volver con back
            Intent intent = new Intent(ClienteActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void cargarProductosDesdeApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<ProductoModel>> call = apiService.obtenerProductos();
        call.enqueue(new Callback<List<ProductoModel>>() {
            @Override
            public void onResponse(Call<List<ProductoModel>> call, Response<List<ProductoModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductoModel> productos = response.body();
                    adapter.setListaProductos(productos);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ClienteActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductoModel>> call, Throwable t) {
                Toast.makeText(ClienteActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

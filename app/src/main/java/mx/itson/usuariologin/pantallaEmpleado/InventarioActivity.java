package mx.itson.usuariologin.pantallaEmpleado;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mx.itson.usuariologin.R;
import mx.itson.usuariologin.models.ProductoModel;
import mx.itson.usuariologin.models.ResponseModel;
import mx.itson.usuariologin.network.ApiService;
import mx.itson.usuariologin.network.RetrofitClient;
import mx.itson.usuariologin.utils.RecyclerItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventarioActivity extends AppCompatActivity {

    private EditText etNombre, etPrecio, etStock, etCategoria;
    private Button btnAgregar, btnActualizar, btnEliminar, btnRegresar;
    private RecyclerView rvInventario;
    private ProductoEmpleadoAdapter adapter;
    private List<ProductoModel> productos;
    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etNombre = findViewById(R.id.etNombre);
        etPrecio = findViewById(R.id.etPrecio);
        etStock = findViewById(R.id.etStock);
        etCategoria = findViewById(R.id.etCategoria);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnRegresar = findViewById(R.id.btnRegresar);

        rvInventario = findViewById(R.id.rvInventario);
        rvInventario.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ProductoEmpleadoAdapter(this, null);
        rvInventario.setAdapter(adapter);

        cargarProductosDesdeApi();

        btnAgregar.setOnClickListener(v -> agregarProducto());
        btnActualizar.setOnClickListener(v -> actualizarProducto());
        btnEliminar.setOnClickListener(v -> eliminarProducto());
        btnRegresar.setOnClickListener(v -> {
            Intent intent = new Intent(InventarioActivity.this, EmpleadoActivity.class);
            startActivity(intent);
            finish();
        });

        // Click listener para seleccionar producto
        rvInventario.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rvInventario, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ProductoModel p = productos.get(position);
                        etNombre.setText(p.getNombre());
                        etPrecio.setText(String.valueOf(p.getPrecio()));
                        etStock.setText(String.valueOf(p.getStock()));
                        etCategoria.setText(p.getCategoria());
                        selectedIndex = position;
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Opcional
                    }
                })
        );
    }

    private void cargarProductosDesdeApi() {
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        Call<List<ProductoModel>> call = api.obtenerProductos();

        call.enqueue(new Callback<List<ProductoModel>>() {
            @Override
            public void onResponse(Call<List<ProductoModel>> call, Response<List<ProductoModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productos = response.body();
                    adapter.setListaProductos(productos);
                    limpiarCampos();
                    selectedIndex = -1;
                } else {
                    Toast.makeText(InventarioActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductoModel>> call, Throwable t) {
                Toast.makeText(InventarioActivity.this, "Fallo en la conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private ProductoModel getProductoFromInputs() {
        String nombre = etNombre.getText().toString().trim();
        double precio = 0;
        try {
            precio = Double.parseDouble(etPrecio.getText().toString());
        } catch (NumberFormatException ignored) {}

        int stock = 0;
        try {
            stock = Integer.parseInt(etStock.getText().toString());
        } catch (NumberFormatException ignored) {}

        String categoria = etCategoria.getText().toString().trim();

        ProductoModel producto = new ProductoModel();
        // Para actualizar o eliminar, usamos el ID del producto seleccionado
        if(selectedIndex >= 0 && productos != null && selectedIndex < productos.size()){
            producto.setId(productos.get(selectedIndex).getId());
        }
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setCategoria(categoria);

        return producto;
    }

    private void agregarProducto() {
        ProductoModel p = getProductoFromInputs();
        if (p.getNombre().isEmpty()) {
            Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseModel> call = api.insertarProducto(
                p.getNombre(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria()
        );

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(InventarioActivity.this, "Producto agregado", Toast.LENGTH_SHORT).show();
                    cargarProductosDesdeApi();
                } else {
                    Toast.makeText(InventarioActivity.this, "Error al agregar producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(InventarioActivity.this, "Fallo en agregar: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarProducto() {
        if (selectedIndex < 0) {
            Toast.makeText(this, "Selecciona un producto para actualizar", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductoModel p = getProductoFromInputs();
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseModel> call = api.actualizarProducto(p.getId(), p.getNombre(), p.getPrecio(), p.getStock(), p.getCategoria());

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(InventarioActivity.this, "Producto actualizado", Toast.LENGTH_SHORT).show();
                    cargarProductosDesdeApi();
                } else {
                    Toast.makeText(InventarioActivity.this, "Error al actualizar producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(InventarioActivity.this, "Fallo en actualizar: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eliminarProducto() {
        if (selectedIndex < 0) {
            Toast.makeText(this, "Selecciona un producto para eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = productos.get(selectedIndex).getId();
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        Call<ResponseModel> call = api.eliminarProducto(id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(InventarioActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                    cargarProductosDesdeApi();
                } else {
                    Toast.makeText(InventarioActivity.this, "Error al eliminar producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(InventarioActivity.this, "Fallo en eliminar: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiarCampos() {
        etNombre.setText("");
        etPrecio.setText("");
        etStock.setText("");
        etCategoria.setText("");
        selectedIndex = -1;
    }
}

package pantallaEmpleado;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

import mx.itson.usuariologin.R;
import pantallaCliente.ProductoModel;

public class InventarioActivity extends Activity {

    private EditText etId, etNombre, etPrecio, etStock, etCategoria;
    private Button btnAgregar, btnActualizar, btnEliminar;
    private ListView lvInventario;

    private List<ProductoModel> productos = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        etId = findViewById(R.id.etId);
        etNombre = findViewById(R.id.etNombre);
        etPrecio = findViewById(R.id.etPrecio);
        etStock = findViewById(R.id.etStock);
        etCategoria = findViewById(R.id.etCategoria);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        lvInventario = findViewById(R.id.lvInventario);

        actualizarLista();

        lvInventario.setOnItemClickListener((parent, view, position, id) -> {
            ProductoModel p = productos.get(position);
            etId.setText(String.valueOf(p.id));
            etNombre.setText(p.nombre);
            etPrecio.setText(String.valueOf(p.precio));
            etStock.setText(String.valueOf(p.stock));
            etCategoria.setText(p.categoria);
            selectedIndex = position;
        });

        btnAgregar.setOnClickListener(v -> {
            ProductoModel p = getProductoFromInputs();
            productos.add(p);
            actualizarLista();
            limpiarCampos();
        });

        btnActualizar.setOnClickListener(v -> {
            if (selectedIndex >= 0) {
                ProductoModel p = getProductoFromInputs();
                productos.set(selectedIndex, p);
                actualizarLista();
                limpiarCampos();
            }
        });

        btnEliminar.setOnClickListener(v -> {
            if (selectedIndex >= 0) {
                productos.remove(selectedIndex);
                actualizarLista();
                limpiarCampos();
            }
        });
    }

    private ProductoModel getProductoFromInputs() {
        int id = Integer.parseInt(etId.getText().toString());
        String nombre = etNombre.getText().toString();
        double precio = Double.parseDouble(etPrecio.getText().toString());
        int stock = Integer.parseInt(etStock.getText().toString());
        String categoria = etCategoria.getText().toString();
        return new ProductoModel(id, nombre, precio, stock, categoria);
    }

    private void actualizarLista() {
        List<String> datos = new ArrayList<>();
        for (ProductoModel p : productos) {
            datos.add(p.id + " - " + p.nombre + " ($" + p.precio + ") - Stock: " + p.stock);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        lvInventario.setAdapter(adapter);
    }

    private void limpiarCampos() {
        etId.setText("");
        etNombre.setText("");
        etPrecio.setText("");
        etStock.setText("");
        etCategoria.setText("");
        selectedIndex = -1;
    }
}

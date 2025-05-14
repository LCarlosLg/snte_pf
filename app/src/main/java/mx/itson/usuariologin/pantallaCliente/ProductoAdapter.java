package mx.itson.usuariologin.pantallaCliente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mx.itson.usuariologin.R;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private final List<ProductoModel> productos;
    private final Context context;
    private final OnAgregarClickListener listener;

    // Interfaz para manejar clics en el botón "Agregar al carrito"
    public interface OnAgregarClickListener {
        void onAgregarClick(ProductoModel producto);
    }

    public ProductoAdapter(Context context, List<ProductoModel> productos, OnAgregarClickListener listener) {
        this.context = context;
        this.productos = productos;
        this.listener = listener;
    }

    // ViewHolder que representa cada ítem del RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvDescripcion;
        Button btnAgregar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionProducto);
            btnAgregar = itemView.findViewById(R.id.btnAgregarCarrito);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductoModel producto = productos.get(position);

        holder.tvNombre.setText(producto.nombre);
        holder.tvDescripcion.setText("Precio: $" + producto.precio + " | Stock: " + producto.stock);

        holder.btnAgregar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAgregarClick(producto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
}

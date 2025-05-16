package mx.itson.usuariologin.pantallaCliente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mx.itson.usuariologin.R;
import mx.itson.usuariologin.models.ProductoModel;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    private Context context;
    private List<ProductoModel> listaCarrito;
    private Runnable onCarritoActualizado;

    public CarritoAdapter(Context context, List<ProductoModel> listaCarrito, Runnable onCarritoActualizado) {
        this.context = context;
        this.listaCarrito = listaCarrito;
        this.onCarritoActualizado = onCarritoActualizado;
    }

    @NonNull
    @Override
    public CarritoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto_carrito, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdapter.ViewHolder holder, int position) {
        ProductoModel producto = listaCarrito.get(position);
        holder.tvNombre.setText(producto.getNombre());
        holder.tvPrecio.setText("$ " + producto.getPrecio());
        holder.tvCantidad.setText("Cantidad: 1");

        Picasso.get()
                .load(producto.getImagen())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.ivProductoImagen);

        holder.btnEliminarProducto.setOnClickListener(v -> {
            listaCarrito.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listaCarrito.size());
            onCarritoActualizado.run();
        });
    }

    @Override
    public int getItemCount() {
        return listaCarrito.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvPrecio, tvCantidad;
        ImageView ivProductoImagen;
        Button btnEliminarProducto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
            tvCantidad = itemView.findViewById(R.id.tvCantidadProducto);
            ivProductoImagen = itemView.findViewById(R.id.ivProductoImagen);
            btnEliminarProducto = itemView.findViewById(R.id.btnEliminarProducto);
        }
    }
}

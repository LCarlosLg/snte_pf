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

import java.util.ArrayList;
import java.util.List;

import mx.itson.usuariologin.R;
import mx.itson.usuariologin.models.ProductoModel;

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    public interface OnProductoClickListener {
        void onAgregarAlCarrito(ProductoModel producto);
    }

    private Context context;
    private List<ProductoModel> listaProductos;
    private OnProductoClickListener listener;

    public ProductoAdapter(Context context, List<ProductoModel> listaProductos, OnProductoClickListener listener) {
        this.context = context;
        this.listaProductos = listaProductos != null ? listaProductos : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ViewHolder holder, int position) {
        ProductoModel producto = listaProductos.get(position);

        holder.tvNombre.setText(producto.getNombre());
        holder.tvCategoria.setText("Categoría: " + producto.getCategoria());
        holder.tvPrecio.setText("$ " + producto.getPrecio());
        holder.tvStock.setText("Stock: " + producto.getStock());

        String urlImagen = producto.getImagen();

        if (urlImagen != null && !urlImagen.isEmpty()) {
            Picasso.get()
                    .load(urlImagen)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(holder.ivProductoImagen);
        } else {
            holder.ivProductoImagen.setImageResource(R.drawable.ic_launcher_foreground);
        }

        holder.btnAgregar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAgregarAlCarrito(producto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public void setListaProductos(List<ProductoModel> lista) {
        listaProductos.clear();
        listaProductos.addAll(lista);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombre, tvCategoria, tvPrecio, tvStock;
        Button btnAgregar;
        ImageView ivProductoImagen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvCategoria = itemView.findViewById(R.id.tvCategoriaProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
            tvStock = itemView.findViewById(R.id.tvStockProducto);
            btnAgregar = itemView.findViewById(R.id.btnAgregarCarrito);
            ivProductoImagen = itemView.findViewById(R.id.ivProductoImagen);
        }
    }
}

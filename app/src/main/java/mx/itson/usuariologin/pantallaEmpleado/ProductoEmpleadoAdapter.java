package mx.itson.usuariologin.pantallaEmpleado;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import mx.itson.usuariologin.R;
import mx.itson.usuariologin.models.ProductoModel;

public class ProductoEmpleadoAdapter extends RecyclerView.Adapter<ProductoEmpleadoAdapter.ViewHolder> {

    private Context context;
    private List<ProductoModel> listaProductos;

    public ProductoEmpleadoAdapter(Context context, List<ProductoModel> listaProductos) {
        this.context = context;
        this.listaProductos = listaProductos != null ? listaProductos : new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_empleado, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductoModel producto = listaProductos.get(position);
        holder.tvId.setText("ID: " + producto.getId());
        holder.tvNombre.setText("Nombre: " + producto.getNombre());
        holder.tvCategoria.setText("Categoría: " + producto.getCategoria());
        holder.tvPrecio.setText("Precio: $" + producto.getPrecio());
        holder.tvStock.setText("Stock: " + producto.getStock());
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
        TextView tvId, tvNombre, tvCategoria, tvPrecio, tvStock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvIdProducto);
            tvNombre = itemView.findViewById(R.id.tvNombreProducto);
            tvCategoria = itemView.findViewById(R.id.tvCategoriaProducto);
            tvPrecio = itemView.findViewById(R.id.tvPrecioProducto);
            tvStock = itemView.findViewById(R.id.tvStockProducto);
        }
    }
}

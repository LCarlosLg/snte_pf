package pantallaCliente;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mx.itson.usuariologin.R;

public class ProductoAdapter extends BaseAdapter {

    private final Context context;
    private final List<ProductoModel> productos;

    public ProductoAdapter(Context context, List<ProductoModel> productos) {
        this.context = context;
        this.productos = productos;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productos.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductoModel producto = productos.get(position);

        holder.tvNombre.setText(producto.nombre);
        holder.tvPrecio.setText(String.format("Precio: $%.2f", producto.precio));
        holder.tvStock.setText("Stock: " + producto.stock);
        holder.tvCategoria.setText("Categoría: " + producto.categoria);

        return convertView;
    }

    static class ViewHolder {
        TextView tvNombre, tvPrecio, tvStock, tvCategoria;

        ViewHolder(View view) {
            tvNombre = view.findViewById(R.id.tvNombre);
            tvPrecio = view.findViewById(R.id.tvPrecio);
            tvStock = view.findViewById(R.id.tvStock);
            tvCategoria = view.findViewById(R.id.tvCategoria);
        }
    }
}




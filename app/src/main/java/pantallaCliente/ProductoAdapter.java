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

    private Context context;
    private List<ProductoModel> productos;

    public ProductoAdapter(Context context, List<ProductoModel> productos) {
        this.context = context;
        this.productos = productos;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int i) {
        return productos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return productos.get(i).id;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        }

        ProductoModel p = productos.get(i);

        ((TextView) view.findViewById(R.id.tvNombre)).setText(p.nombre);
        ((TextView) view.findViewById(R.id.tvPrecio)).setText("Precio: $" + p.precio);
        ((TextView) view.findViewById(R.id.tvStock)).setText("Stock: " + p.stock);
        ((TextView) view.findViewById(R.id.tvCategoria)).setText("Categoría: " + p.categoria);

        return view;
    }
}



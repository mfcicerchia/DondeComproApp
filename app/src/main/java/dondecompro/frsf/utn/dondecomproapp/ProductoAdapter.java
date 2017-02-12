package dondecompro.frsf.utn.dondecomproapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import dondecompro.frsf.utn.dondecomproapp.modelo.Producto;

/**
 * Created by Pablo Paletto on 12/2/2017.
 */

public class ProductoAdapter extends BaseAdapter {
    /**Inflater**/
    ArrayList<Producto> items = new ArrayList<Producto>();
    Context context;
    LayoutInflater inflater;
    ProductoAdapter(Context context, ArrayList<Producto> items){
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Producto getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View fila=convertView;
        if(fila==null){
            fila = inflater.inflate(R.layout.fila_producto, parent, false);
        }
        ViewHolder holder = (ViewHolder)fila.getTag();
        if(holder==null){
            holder=new ViewHolder(fila);
            fila.setTag(holder);
        }

        holder.nombre.setText(this.getItem(position).getNombre());
        holder.precio.setText(this.getItem(position).getPrecio().toString());
        holder.categoria.setText(this.getItem(position).getCategoria());

        return (fila);
    }

    class ViewHolder{
        TextView nombre;
        TextView precio;
        TextView categoria;

        ViewHolder(View base){
            this.nombre = (TextView)base.findViewById(R.id.tvProducto);
            this.precio = (TextView)base.findViewById(R.id.tvPrecio);
            this.categoria = (TextView)base.findViewById(R.id.tvCategoria);
        }
    }
}

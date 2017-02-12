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
    private ArrayList<Producto> items = new ArrayList<Producto>();
    private Context context;
    private LayoutInflater inflater;

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
        holder.precio.setText(Float.toString(this.getItem(position).getPrecio()));
        holder.categoria.setText(this.getItem(position).getCategoria());

        return (fila);
    }

    class ViewHolder{
        private TextView nombre;
        private   TextView precio;
        private  TextView categoria;

        ViewHolder(View base){
            this.nombre = (TextView)base.findViewById(R.id.tvProducto);
            this.precio = (TextView)base.findViewById(R.id.tvPrecio);
            this.categoria = (TextView)base.findViewById(R.id.tvCategoria);
        }
    }
}

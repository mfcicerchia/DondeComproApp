package dondecompro.frsf.utn.dondecomproapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dondecompro.frsf.utn.dondecomproapp.modelo.Producto;

/**
 * Created by Pablo Paletto on 12/2/2017.
 */

public class ProductoAdapter extends BaseAdapter  implements Filterable{
    /**Inflater**/
    private ArrayList<Producto> items = new ArrayList<Producto>();
    private ArrayList<Producto> itemsFiltrados = new ArrayList<>();
    private CustomFilter mFilter;
    private Context context;
    private LayoutInflater inflater;

    ProductoAdapter(Context context, ArrayList<Producto> items){
        this.items = items;
        this.context = context;
        this.itemsFiltrados.addAll(items);
        this.mFilter = new CustomFilter(ProductoAdapter.this);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemsFiltrados.size();
    }

    @Override
    public Producto getItem(int position) {
        return itemsFiltrados.get(position);
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

    @Override
    public Filter getFilter() {
        return mFilter;
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

    public class CustomFilter extends Filter{
        private ProductoAdapter productoAdapter;

        private CustomFilter(ProductoAdapter productoAdapter){
            super();
            this.productoAdapter = productoAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            itemsFiltrados.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0){
                itemsFiltrados.addAll(items);
            }
            else{
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final Producto producto : items){
                    if (producto.getNombre().toLowerCase().contains(filterPattern)){
                        itemsFiltrados.add(producto);
                    }
                }
            }

            results.values = itemsFiltrados;
            results.count = itemsFiltrados.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.productoAdapter.notifyDataSetChanged();
        }
    }

}

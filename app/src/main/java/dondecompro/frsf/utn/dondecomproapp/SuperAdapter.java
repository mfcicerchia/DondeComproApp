package dondecompro.frsf.utn.dondecomproapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import dondecompro.frsf.utn.dondecomproapp.modelo.Supermercado;

/**
 * Created by Pablo Paletto on 12/2/2017.
 */

public class SuperAdapter extends BaseAdapter  implements Filterable{
    /**Inflater**/
    private ArrayList<Supermercado> items = new ArrayList<Supermercado>();
    private ArrayList<Supermercado> itemsFiltrados = new ArrayList<>();
    private CustomFilter mFilter;
    private Context context;
    private LayoutInflater inflater;

    SuperAdapter(Context context, ArrayList<Supermercado> items){
        this.items = items;
        this.context = context;
        this.itemsFiltrados.addAll(items);
        this.mFilter = new CustomFilter(SuperAdapter.this);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemsFiltrados.size();
    }

    @Override
    public Supermercado getItem(int position) {
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
            fila = inflater.inflate(R.layout.fila_super_ver, parent, false);
        }
        ViewHolder holder = (ViewHolder)fila.getTag();
        if(holder==null){
            holder=new ViewHolder(fila);
            fila.setTag(holder);
        }

        holder.nombre.setText(this.getItem(position).getNombre());
        holder.direccion.setText(this.getItem(position).getDireccion());
        holder.latitud.setText(this.getItem(position).getLatitud());
        holder.longitud.setText(this.getItem(position).getLongitud());

        return (fila);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    class ViewHolder{
        private TextView nombre;
        private   TextView direccion;
        private  TextView latitud;
        private  TextView longitud;

        ViewHolder(View base){
            this.nombre = (TextView)base.findViewById(R.id.tvFiltroSuper);
            this.direccion = (TextView)base.findViewById(R.id.tvDireccion);
            this.latitud = (TextView)base.findViewById(R.id.tvLatitud);
            this.longitud = (TextView)base.findViewById(R.id.tvLongitud);
        }
    }

    public class CustomFilter extends Filter{
        private SuperAdapter superAdapter;

        private CustomFilter(SuperAdapter superAdapter){
            super();
            this.superAdapter = superAdapter;
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
                for (final Supermercado producto : items){
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
            this.superAdapter.notifyDataSetChanged();
        }
    }

}

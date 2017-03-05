package dondecompro.frsf.utn.dondecomproapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import dondecompro.frsf.utn.dondecomproapp.dao2.PedidosDAO;
import dondecompro.frsf.utn.dondecomproapp.modelo.Producto;

public class ProductosActivity extends AppCompatActivity {

    ArrayList<Producto> listaProductos = new ArrayList<Producto>();
    private ProductoAdapter adapter; // No borrar
    private ArrayAdapter<Producto> adapterTwo;
    ListView lvProductos;
    EditText filtroProducto;

    PedidosDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        filtroProducto = (EditText)findViewById(R.id.etFiltroProducto);
        lvProductos = (ListView)findViewById(R.id.lvProductos);

        /*Obtengo todos los productos de la BD */
        dao = new PedidosDAO(this);
        dao.open();
        listaProductos = dao.getAllProductos();
        dao.close();
        // Nota: los productos fueron cargados previamente  ala BD, cuando inicio la aplicacion en la
        //       clase: InicioSplashActivity


        /**Creo el adaptador del ListView y le paso la lista de productos*/
        // adapterTwo = new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_multiple_choice, listaProductos);
        adapter = new ProductoAdapter(ProductosActivity.this, listaProductos); // No borrar

        /**Asigno el adaptador al ListView*/
        lvProductos.setAdapter(adapter);

        /// No borrar
        filtroProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


//    private void cargarProductosBD(PedidosDAO dao, ArrayList<Producto> listaProdutos){
//        for(Producto p: listaProdutos){
//            dao.crearProducto(p.getNombre(),p.getPrecio(),p.getCategoria());
//        }
//
//    }

}

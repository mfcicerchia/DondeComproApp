package dondecompro.frsf.utn.dondecomproapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import dondecompro.frsf.utn.dondecomproapp.dao2.SuperDAO;
import dondecompro.frsf.utn.dondecomproapp.modelo.Supermercado;

public class SupermecadoActivity extends AppCompatActivity {

    ArrayList<Supermercado> listaSupermercados = new ArrayList<Supermercado>(); // en esta lista se van a cargar los datos recuperados de la B
    ArrayList<Supermercado> listaSupersHC = new ArrayList<Supermercado>(); // en esta lista se van a crear las instancias de los datos a cargar en la BD
    ArrayList<Supermercado> listaAux;
    ArrayAdapter<Supermercado> lvProductosAdapter;
   // private SuperAdapter adapter;
    ArrayAdapter <String> lvAdapter;
    Context context = SupermecadoActivity.this;
    ListView lista_supermercados;
    EditText filtroSupermercado;


    SuperDAO dao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supermercados);
        filtroSupermercado = (EditText)findViewById(R.id.etFiltroSuper);

        this.inicializarListaSupermercadosHarcodeados();  // se inicializan los productos que estan harcodeados y se agregan a la listas

        /*crea una instancia de la BD y se abre*/
        dao = new SuperDAO(this);
        dao.open();

        Log.d("Supers en Memoria",Integer.toString(listaSupersHC.size()));
        //dao.cargarProductos(listaSupersHC);  /// se le pasan la lista con los productos al dao y se agregan a la BD

      //  if (contieneRegistrosSupersBD(dao) == 0){
            this.cargarProductosBD(dao,listaSupersHC);
       // }

        /// Se obtienen los productos de la base de datos
        //listaSupermercados = (ArrayList<Supermercado>) dao.getAllProductos();

       // listaAux =  dao.getAllProductos();

        //Log.d("TAMAÑO LISTA RECUPER", Integer.toString(listaAux.size()));
       listaSupermercados = dao.getAllSupermercados();


        dao.close();

        //listaSupermercados = listaSupersHC;

        for(Supermercado p: listaSupermercados){
            Log.d("Supermercado recuperado",p.getNombre());
        }

        //ArrayAdapter<Pedido> adapter = new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1, listaPedidos);
        ArrayList<String> ssupermercafos = new ArrayList<>();
        for (Supermercado s: listaSupermercados){
            ssupermercafos.add(s.toString());
        }
        lvAdapter  = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ssupermercafos);

       // adapter = new SuperAdapter( SupermecadoActivity.this, listaSupermercados);

        lista_supermercados = (ListView)findViewById(R.id.lvSupers);
        lista_supermercados.setAdapter(lvAdapter);

 /*       filtroSupermercado.addTextChangedListener(new TextWatcher() {
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
        });*/
    }


    private void inicializarListaSupermercadosHarcodeados(){
        // Datos obtenidos de
        this.listaSupersHC.add(new Supermercado("COTO CICSA","Rivadavia 3396","-31.637308","-60.7017017"));
        this.listaSupersHC.add(new Supermercado("LA ANONIMA","Belgrano 1998","-31.445650","-60.943220"));
        this.listaSupersHC.add(new Supermercado("Walmart SuperCenter","Ruta Nac 168 Km 474","-31.644722","-60.692778"));

    }

    private void cargarProductosBD(SuperDAO dao, ArrayList<Supermercado> listaProdutos){
        for(Supermercado p: listaProdutos){
            dao.crearSuper(p.getNombre(),p.getDireccion(),p.getLatitud(), p.getLongitud());
        }
    }

    private int contieneRegistrosSupersBD(SuperDAO dao){
        // consulta la cantidad de registros
        //Si da vacio retorna 0 sino 1
        long registros=0;

        registros = dao.cantcantidadSupers() ;
        if(registros ==0){
            return 0;
        }else{
            return 1;
        }
    }

}

package dondecompro.frsf.utn.dondecomproapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedList;

import dondecompro.frsf.utn.dondecomproapp.dao2.PedidosDAO;
import dondecompro.frsf.utn.dondecomproapp.modelo.Producto;

public class ProductosToPedido extends AppCompatActivity {

    ArrayList<Producto> listaProductos = new ArrayList<Producto>();
    ArrayList<Producto> listaProductosHC = new ArrayList<Producto>();
    ArrayList<Producto> listaAux;
    ArrayAdapter<Producto> lvProductosAdapter;
    private ProductoAdapter adapter;
    Context context = ProductosToPedido.this;
    ListView lista_productos;
    EditText filtroProducto;
    PedidosDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_to_pedido);
        filtroProducto = (EditText)findViewById(R.id.etFilterProdToPed);
        this.inicializarListaProductosHarcodeados();  // se inicializan los productos que estan harcodeados y se agregan a la listas

        /*crea una instancia de la BD y se abre*/
        dao = new PedidosDAO(this);
        dao.open();
        Log.d("CANTIDAD DE PRODUCTOS",Integer.toString(listaProductosHC.size()));
        //dao.cargarProductos(listaProductosHC);  /// se le pasan la lista con los productos al dao y se agregan a la BD

        this.cargarProductosBD(dao,listaProductosHC);
        for(Producto p: listaProductosHC){
            dao.crearProducto(p.getNombre().toString(), p.getPrecio(),p.getCategoria());
            Log.d("Producto Agregado", "Nombre: " + p.getNombre() +" Precio: "+ p.getPrecio()+" Categoria: "+ p.getCategoria());
        }
/*
        for(Producto p: dao.getAllProductos()){
            Log.d("Producto",p.getNombre());
        }*/
        /// Se obtienen los productos de la base de datos
        //listaProductos = (ArrayList<Producto>) dao.getAllProductos();

        //listaAux =  dao.getAllProductos();

        //Log.d("TAMAÑO LISTA RECUPER", Integer.toString(listaAux.size()));

        dao.close();

        listaProductos = listaProductosHC;

        adapter = new ProductoAdapter(ProductosToPedido.this, listaProductos);
        lista_productos = (ListView)findViewById(R.id.lvProductosToPedido);
        lista_productos.setAdapter(adapter);

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


    private void inicializarListaProductosHarcodeados(){



        listaProductosHC.add(new Producto("Te Dharamsala",346,"Bebidas"));
        listaProductosHC.add(new Producto("Cerveza tibetana Barley",305,"Bebidas"));
        listaProductosHC.add(new Producto("Sirope de regaliz",853,"Condimentos"));
        listaProductosHC.add(new Producto("Especias Cajun del chef Anton",732,"Condimentos"));
        listaProductosHC.add(new Producto("Mezcla Gumbo del chef Anton",156,"Condimentos"));
        listaProductosHC.add(new Producto("Mermelada de grosellas de la abuela",600,"Condimentos"));
        listaProductosHC.add(new Producto("Peras secas organicas del tio Bob",217,"Frutas Verduras"));
        listaProductosHC.add(new Producto("Salsa de arandanos Northwoods",189,"Condimentos"));
        listaProductosHC.add(new Producto("Buey Mishi Kobe",923,"Carnes"));
        listaProductosHC.add(new Producto("Pez espada",139,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Queso Cabrales",141,"Lacteos"));
        listaProductosHC.add(new Producto("Queso Manchego La Pastora",323,"Lacteos"));
        listaProductosHC.add(new Producto("Algas Konbu",326,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Cuajada de judias",446,"Frutas Verduras"));
        listaProductosHC.add(new Producto("Salsa de soja baja en sodio",623,"Condimentos"));
        listaProductosHC.add(new Producto("Postre de merengue Pavlova",706,"Reposteria"));
        listaProductosHC.add(new Producto("Cordero Alice Springs",253,"Carnes"));
        listaProductosHC.add(new Producto("Langostinos tigre Carnarvon",457,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Pastas de te de chocolate",759,"Reposteria"));
        listaProductosHC.add(new Producto("Mermelada de Sir Rodney",702,"Reposteria"));
        listaProductosHC.add(new Producto("Bollos de Sir Rodney",659,"Reposteria"));
        listaProductosHC.add(new Producto("Pan de centeno crujiente estilo Gustaf",889,"Granos Cereales"));
        listaProductosHC.add(new Producto("Pan fino",865,"Granos Cereales"));
        listaProductosHC.add(new Producto("Refresco Guarana Fantastica",923,"Bebidas"));
        listaProductosHC.add(new Producto("Crema de chocolate y nueces NuNuCa",128,"Reposteria"));
        listaProductosHC.add(new Producto("Ositos de goma Gumbar",251,"Reposteria"));
        listaProductosHC.add(new Producto("Chocolate Schoggi",905,"Reposteria"));
        listaProductosHC.add(new Producto("Col fermentada Rossle",78,"Frutas Verduras"));
        listaProductosHC.add(new Producto("Salchicha Thuringer",668,"Carnes"));
        listaProductosHC.add(new Producto("Arenque blanco del noroeste",343,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Queso gorgonzola Telino",816,"Lacteos"));
        listaProductosHC.add(new Producto("Queso Mascarpone Fabioli",72,"Lacteos"));
        listaProductosHC.add(new Producto("Queso de cabra",674,"Lacteos"));
        listaProductosHC.add(new Producto("Cerveza Sasquatch",724,"Bebidas"));
        listaProductosHC.add(new Producto("Cerveza negra Steeleye",301,"Bebidas"));
        listaProductosHC.add(new Producto("Escabeche de arenque",73,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Salmon ahumado Gravad",806,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Vino Côte de Blaye",463,"Bebidas"));
        listaProductosHC.add(new Producto("Licor verde Chartreuse",946,"Bebidas"));
        listaProductosHC.add(new Producto("Carne de cangrejo de Boston",293,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Crema de almejas estilo Nueva Inglaterra",772,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Tallarines de Singapur",590,"Granos Cereales"));
        listaProductosHC.add(new Producto("Cafe de Malasia",808,"Bebidas"));
        listaProductosHC.add(new Producto("Azucar negra Malacca",442,"Condimentos"));
        listaProductosHC.add(new Producto("Arenque ahumado",991,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Arenque salado",576,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Galletas Zaanse",316,"Reposteria"));
        listaProductosHC.add(new Producto("Chocolate holandes",690,"Reposteria"));
        listaProductosHC.add(new Producto("Regaliz",425,"Reposteria"));
        listaProductosHC.add(new Producto("Chocolate blanco",229,"Reposteria"));
        listaProductosHC.add(new Producto("Manzanas secas Manjimup",110,"Frutas Verduras"));
        listaProductosHC.add(new Producto("Cereales para Filo",165,"Granos Cereales"));
        listaProductosHC.add(new Producto("Empanada de carne",929,"Carnes"));
        listaProductosHC.add(new Producto("Empanada de cerdo",254,"Carnes"));
        listaProductosHC.add(new Producto("Pate chino",84,"Carnes"));
        listaProductosHC.add(new Producto("Gnocchi de la abuela Alicia",778,"Granos Cereales"));
        listaProductosHC.add(new Producto("Raviolis Angelo",175,"Granos Cereales"));
        listaProductosHC.add(new Producto("Caracoles de Borgoña",681,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Raclet de queso Courdavault",320,"Lacteos"));
        listaProductosHC.add(new Producto("Camembert Pierrot",801,"Lacteos"));
        listaProductosHC.add(new Producto("Sirope de arce",491,"Condimentos"));
        listaProductosHC.add(new Producto("Tarta de azucar",560,"Reposteria"));
        listaProductosHC.add(new Producto("Sandwich de vegetales",646,"Condimentos"));
        listaProductosHC.add(new Producto("Bollos de pan de Wimmer",699,"Granos Cereales"));
        listaProductosHC.add(new Producto("Salsa de pimiento picante de Luisiana",969,"Condimentos"));
        listaProductosHC.add(new Producto("Especias picantes de Luisiana",953,"Condimentos"));
        listaProductosHC.add(new Producto("Cerveza Laughing Lumberjack",645,"Bebidas"));
        listaProductosHC.add(new Producto("Barras de pan de Escocia",505,"Reposteria"));
        listaProductosHC.add(new Producto("Queso Gudbrandsdals",527,"Lacteos"));
        listaProductosHC.add(new Producto("Cerveza Outback",16,"Bebidas"));
        listaProductosHC.add(new Producto("Crema de queso Flotemys",60,"Lacteos"));
        listaProductosHC.add(new Producto("Queso Mozzarella Giovanni",325,"Lacteos"));
        listaProductosHC.add(new Producto("Caviar rojo",811,"Pescado Marisco"));
        listaProductosHC.add(new Producto("Queso de soja Longlife",8,"Frutas Verduras"));
        listaProductosHC.add(new Producto("Cerveza Klosterbier Rhonbrau",157,"Bebidas"));
        listaProductosHC.add(new Producto("Licor Cloudberry",243,"Bebidas"));
        listaProductosHC.add(new Producto("Salsa verde original Frankfurter",623,"Condimentos"));

    }
   /* private void inicializarListaProductos(){
        //listaProductos.add(new Producto(id,"nombre",precio,categoria))
        listaProductos.add(new Producto(3864,"Té Dharamsala",858,"Bebidas"));
        listaProductos.add(new Producto(5805,"Cerveza tibetana Barley",719,"Bebidas"));
        listaProductos.add(new Producto(9987,"Sirope de regaliz",937,"Condimentos"));
        listaProductos.add(new Producto(1179,"Especias Cajun del chef Anton",405,"Condimentos"));
        listaProductos.add(new Producto(5874,"Mezcla Gumbo del chef Anton",211,"Condimentos"));
        listaProductos.add(new Producto(1944,"Mermelada de grosellas de la abuela",857,"Condimentos"));
        listaProductos.add(new Producto(6261,"Peras secas orgánicas del tío Bob",414,"Frutas/Verduras"));
        listaProductos.add(new Producto(7796,"Salsa de arándanos Northwoods",76,"Condimentos"));
        listaProductos.add(new Producto(2365,"Buey Mishi Kobe",485,"Carnes"));
        listaProductos.add(new Producto(2852,"Pez espada",602,"Pescado/Marisco"));
        listaProductos.add(new Producto(4729,"Queso Cabrales",674,"Lácteos"));
        listaProductos.add(new Producto(3656,"Queso Manchego La Pastora",332,"Lácteos"));
        listaProductos.add(new Producto(3673,"Algas Konbu",208,"Pescado/Marisco"));
        listaProductos.add(new Producto(8338,"Cuajada de judías",212,"Frutas/Verduras"));
        listaProductos.add(new Producto(8767,"Salsa de soja baja en sodio",989,"Condimentos"));
        listaProductos.add(new Producto(2249,"Postre de merengue Pavlova",68,"Repostería"));
        listaProductos.add(new Producto(7619,"Cordero Alice Springs",495,"Carnes"));
        listaProductos.add(new Producto(7719,"Langostinos tigre Carnarvon",163,"Pescado/Marisco"));
        listaProductos.add(new Producto(6664,"Pastas de té de chocolate",152,"Repostería"));
        listaProductos.add(new Producto(7584,"Mermelada de Sir Rodney",569,"Repostería"));
        listaProductos.add(new Producto(4233,"Bollos de Sir Rodney",957,"Repostería"));
        listaProductos.add(new Producto(6143,"Pan de centeno crujiente estilo Gustaf",76,"Granos/Cereales"));
        listaProductos.add(new Producto(4196,"Pan fino",579,"Granos/Cereales"));
        listaProductos.add(new Producto(5564,"Refresco Guaraná Fantástica",554,"Bebidas"));
        listaProductos.add(new Producto(1313,"Crema de chocolate y nueces NuNuCa",165,"Repostería"));
        listaProductos.add(new Producto(6808,"Ositos de goma Gumbär",682,"Repostería"));
        listaProductos.add(new Producto(183,"Chocolate Schoggi",3,"Repostería"));
        listaProductos.add(new Producto(8154,"Col fermentada Rössle",621,"Frutas/Verduras"));
        listaProductos.add(new Producto(5968,"Salchicha Thüringer",164,"Carnes"));
        listaProductos.add(new Producto(8561,"Arenque blanco del noroeste",38,"Pescado/Marisco"));
        listaProductos.add(new Producto(5027,"Queso gorgonzola Telino",931,"Lácteos"));
        listaProductos.add(new Producto(1975,"Queso Mascarpone Fabioli",111,"Lácteos"));
        listaProductos.add(new Producto(1498,"Queso de cabra",936,"Lácteos"));
        listaProductos.add(new Producto(5356,"Cerveza Sasquatch",56,"Bebidas"));
        listaProductos.add(new Producto(5836,"Cerveza negra Steeleye",91,"Bebidas"));
        listaProductos.add(new Producto(9337,"Escabeche de arenque",949,"Pescado/Marisco"));
        listaProductos.add(new Producto(7227,"Salmón ahumado Gravad",63,"Pescado/Marisco"));
        listaProductos.add(new Producto(9423,"Vino Côte de Blaye",882,"Bebidas"));
        listaProductos.add(new Producto(3800,"Licor verde Chartreuse",970,"Bebidas"));
        listaProductos.add(new Producto(1930,"Carne de cangrejo de Boston",224,"Pescado/Marisco"));
        listaProductos.add(new Producto(6313,"Crema de almejas estilo Nueva Inglaterra",528,"Pescado/Marisco"));
        listaProductos.add(new Producto(1613,"Tallarines de Singapur",427,"Granos/Cereales"));
        listaProductos.add(new Producto(598,"Café de Malasia",360,"Bebidas"));
        listaProductos.add(new Producto(6952,"Azúcar negra Malacca",924,"Condimentos"));
        listaProductos.add(new Producto(5407,"Arenque ahumado",146,"Pescado/Marisco"));
        listaProductos.add(new Producto(8532,"Arenque salado",573,"Pescado/Marisco"));
        listaProductos.add(new Producto(6448,"Galletas Zaanse",505,"Repostería"));
        listaProductos.add(new Producto(697,"Chocolate holandés",467,"Repostería"));
        listaProductos.add(new Producto(7025,"Regaliz",811,"Repostería"));
        listaProductos.add(new Producto(5539,"Chocolate blanco",172,"Repostería"));
        listaProductos.add(new Producto(9162,"Manzanas secas Manjimup",217,"Frutas/Verduras"));
        listaProductos.add(new Producto(4279,"Cereales para Filo",610,"Granos/Cereales"));
        listaProductos.add(new Producto(7511,"Empanada de carne",86,"Carnes"));
        listaProductos.add(new Producto(9960,"Empanada de cerdo",876,"Carnes"));
        listaProductos.add(new Producto(3428,"Paté chino",447,"Carnes"));
        listaProductos.add(new Producto(3082,"Gnocchi de la abuela Alicia",986,"Granos/Cereales"));
        listaProductos.add(new Producto(2164,"Raviolis Angelo",163,"Granos/Cereales"));
        listaProductos.add(new Producto(3845,"Caracoles de Borgoña",890,"Pescado/Marisco"));
        listaProductos.add(new Producto(8002,"Raclet de queso Courdavault",49,"Lácteos"));
        listaProductos.add(new Producto(7801,"Camembert Pierrot",3,"Lácteos"));
        listaProductos.add(new Producto(5923,"Sirope de arce",582,"Condimentos"));
        listaProductos.add(new Producto(8283,"Tarta de azúcar",92,"Repostería"));
        listaProductos.add(new Producto(2372,"Sandwich de vegetales",578,"Condimentos"));
        listaProductos.add(new Producto(1616,"Bollos de pan de Wimmer",308,"Granos/Cereales"));
        listaProductos.add(new Producto(529,"Salsa de pimiento picante de Luisiana",709,"Condimentos"));
        listaProductos.add(new Producto(620,"Especias picantes de Luisiana",944,"Condimentos"));
        listaProductos.add(new Producto(4129,"Cerveza Laughing Lumberjack",695,"Bebidas"));
        listaProductos.add(new Producto(5592,"Barras de pan de Escocia",893,"Repostería"));
        listaProductos.add(new Producto(9376,"Queso Gudbrandsdals",18,"Lácteos"));
        listaProductos.add(new Producto(6017,"Cerveza Outback",265,"Bebidas"));
        listaProductos.add(new Producto(3938,"Crema de queso Fløtemys",676,"Lácteos"));
        listaProductos.add(new Producto(9717,"Queso Mozzarella Giovanni",765,"Lácteos"));
        listaProductos.add(new Producto(5576,"Caviar rojo",98,"Pescado/Marisco"));
        listaProductos.add(new Producto(2599,"Queso de soja Longlife",413,"Frutas/Verduras"));
        listaProductos.add(new Producto(1944,"Cerveza Klosterbier Rhönbräu",896,"Bebidas"));
        listaProductos.add(new Producto(2333,"Licor Cloudberry",474,"Bebidas"));
        listaProductos.add(new Producto(3421,"Salsa verde original Frankfurter",112,"Condimentos"));

    }*/

    /*

    */


    private void cargarProductosBD(PedidosDAO dao, ArrayList<Producto> listaProdutos){
        for(Producto p: listaProdutos){
            dao.crearProducto(p.getNombre(),p.getPrecio(),p.getCategoria());
        }

    }

}

package dondecompro.frsf.utn.dondecomproapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Window;

import dondecompro.frsf.utn.dondecomproapp.dao2.PedidosDAO;
import dondecompro.frsf.utn.dondecomproapp.modelo.Producto;

public class InicioSplashActivity extends AppCompatActivity {

    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 1000;
    ArrayList<Producto> listaProductosHC = new ArrayList<Producto>();
    PedidosDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_inicio_splash);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                // Start the next activity
                Intent mainIntent = new Intent().setClass(InicioSplashActivity.this, MainActivity.class);
                startActivity(mainIntent);

                /*El tiempo que demora el screen splsh es el que demora en cargar la BD*/
                /**Antes de realizar cualquier accion se cargan a la Base de datos los registros de pruebas*/
                cargarProductosDePrueba();

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        // Simulate a long loading process on application startup.
       Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);





    }

    private void cargarProductosDePrueba() {


        /*crea una instancia de la BD y se abre*/
        dao = new PedidosDAO(this);
        dao.open();
        Log.d("CANTIDAD DE PRODUCTOS", Integer.toString(listaProductosHC.size()));

        /*Cheque el tamaño de la tabla productos de la BD, si es cero los cargo si no no hace nada*/
        if (dao.getAllProductos().size() == 0) {

             /*se crean las instancias de los productos harcodeados*/
            this.inicializarListaProductosHarcodeados();



        /*se cargan los productos a la BD*/
            //  this.cargarProductosBD(dao, listaProductosHC);

            for (Producto p : listaProductosHC) {
                dao.crearProducto(p.getNombre().toString(), p.getPrecio(), p.getCategoria());
                Log.d("Producto Agregado", "Nombre: " + p.getNombre() + " Precio: " + p.getPrecio() + " Categoria: " + p.getCategoria());
            }
        }
    }

  /*  private void cargarProductosBD(PedidosDAO dao, ArrayList<Producto> listaProdutos){
        for(Producto p: listaProdutos){
            dao.crearProducto(p.getNombre(),p.getPrecio(),p.getCategoria());
        }
    }*/


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


        /*listaProductosHC.add(new Producto("Algas Konbu",326,"Pescado Marisco"));
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
        listaProductosHC.add(new Producto("Salsa verde original Frankfurter",623,"Condimentos"));*/

    }
}

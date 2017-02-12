package dondecompro.frsf.utn.dondecomproapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import dondecompro.frsf.utn.dondecomproapp.modelo.Producto;

public class ProductosActivity extends AppCompatActivity {

    ArrayList<Producto> listaProductos = new ArrayList<Producto>();
    ArrayAdapter<Producto> lvProductosAdapter;
    private ProductoAdapter adapter;
    Context context = ProductosActivity.this;
    ListView lista_productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        this.inicializarListaProductos();
        adapter = new ProductoAdapter(ProductosActivity.this, listaProductos);
        lista_productos = (ListView)findViewById(R.id.lvProductos);
        lista_productos.setAdapter(adapter);

    }

    private void inicializarListaProductos(){
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

    }
}

package dondecompro.frsf.utn.dondecomproapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import dondecompro.frsf.utn.dondecomproapp.dao2.PedidosDAO;
import dondecompro.frsf.utn.dondecomproapp.modelo.Pedido;

public class ListarProductosDePedido extends AppCompatActivity {



    private ListView lvListaProductosDePedido;

//    ArrayAdapter<Pedido> adapter;
    ArrayAdapter<Integer> adapter;
    private PedidosDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_productos_de_pedido);

        /// recibo el id del pedido que me mando ListarPedidoActivity

        int idPedido =  getIntent().getExtras().getInt("pedido");
        Toast.makeText(getApplicationContext(), "El pedido seleccionado es: " + Integer.toString(idPedido), Toast.LENGTH_SHORT).show();

        lvListaProductosDePedido = (ListView) findViewById(R.id.lvListaProductosDePedido);


        dao = new PedidosDAO(this);
        dao.open();
        // Cargamos la lista de Pedidos de la BD y la asignamos el ListView de Pedidos
        List<Integer> listaIdProductos = dao.getProductosDePedido(idPedido);
        //List<Producto> listaPedidos = dao.getAllPedidos();
        dao.close();


        adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, listaIdProductos);
        lvListaProductosDePedido .setAdapter(adapter);


    }
}

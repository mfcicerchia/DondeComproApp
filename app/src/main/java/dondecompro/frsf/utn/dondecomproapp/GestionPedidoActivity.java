package dondecompro.frsf.utn.dondecomproapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dondecompro.frsf.utn.dondecomproapp.dao2.PedidosDAO;
import dondecompro.frsf.utn.dondecomproapp.modelo.Pedido;
import dondecompro.frsf.utn.dondecomproapp.modelo.Producto;

import static android.R.id.list;

public class GestionPedidoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private int requestCode1 = 1;
    private int requestCode2 = 0; // esta variable representará el id del pedido y se utilizara como
                                   // parametro para seleccionar los productos del mismo
    private ListView lvPedidos;
    private Button btnNuevoPedido;
    ArrayAdapter<Pedido> adapter;

    private PedidosDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_pedido);

        // Ipstapciamos PedidosDAO para
        // poder realizar acciopes cop la base de datos
        dao = new PedidosDAO(this);
        dao.open();



        lvPedidos = (ListView) findViewById(R.id.lvPedidos);

        btnNuevoPedido = (Button) findViewById(R.id.btnNuevoPedido);
        btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // se lanza la nueva actividad para crear el nuevo pedido
                Intent i = new Intent(v.getContext(), NuevoPedidoActivity.class);
                startActivityForResult(i, requestCode1);
            }
        });




        // Cargamos la lista de Pedidos de la BD y la asignamos el ListView de Pedidos
        List<Pedido> listaPedidos = dao.getAllPedidos();

        adapter = new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1, listaPedidos);
        lvPedidos.setAdapter(adapter);

        lvPedidos.setOnItemClickListener(this);

    }




    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, long id) {


    // Mensaje Toast del elemento seleccionado
    Pedido pedido = (Pedido) adapterView.getItemAtPosition(position);
    String item = Integer.toString(pedido.getId());
    requestCode2 = pedido.getId(); //Actualizamos el requestCode para saber a que pedido pertenece la lista de retorno
    Toast.makeText(getApplicationContext(), "ID PEDIDO: " + item, Toast.LENGTH_SHORT).show();



        // TODO Auto-geperated method stub
       AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Gestionar Pedido")
                .setMessage("¿Elija una Opcion?")

                .setPositiveButton("BORRAR PEDIDO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-geperated method stub


                                Pedido pedido = (Pedido) adapterView.getItemAtPosition(position);
                                Log.d("Posicion en la lista", Integer.toString(position));

                                dao.borrarPedido(pedido);

                                // Refrescamos la lista
                                refrescarListaConPedidos();
                            }
                        })

                .setNeutralButton("CARGAR PRODUCTOS",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub

                                Intent i = new Intent(view.getContext(), ProductosToPedido.class);

                                startActivityForResult(i, requestCode2);

                            }
                        });


          /*      .setNeutralButton("Ver Productos",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub

                                Intent i = new Intent(view.getContext(), ProductosToPedido.class);
                                *//*requestCode2 = id;*//*
                                startActivityForResult(i, requestCode2);
                                //lvPedidos.dispatchSetSelected(false);
                            }
                        });*/
        builder.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        Log.d("Result", "Se ejecuta onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode1 && resultCode == RESULT_OK) {
            // Actualizar el Adapter
            dao.open();
            refrescarListaConPedidos();

        }

       if(requestCode == this.requestCode2 && resultCode == RESULT_OK){

           // lista para traer los id de productos de un pedido
           ArrayList<Integer> listPrueba = new ArrayList<>();

           ArrayList<Integer> listaRetorno = (ArrayList<Integer>) data.getIntegerArrayListExtra("productos");


           System.out.println("tamaños de la list obtenida del intent: " + listaRetorno.size());

           dao = new PedidosDAO(this);
           dao.open();
           dao.cargarProductosAlPedido(listaRetorno,requestCode);

           listPrueba = dao.getProductosDePedido(requestCode2); // requestCodede2 tiene el id del pedido actual
           Toast.makeText(getApplicationContext(), "Productos Grabados con Exito en Pedido (ID:"+ Integer.toString(requestCode2)+")", Toast.LENGTH_LONG).show();

           //imprimirListaProductos(listaRetorno);  // datos que vienen de la Activity ProductosToPedido
           imprimirListaProductos(listPrueba); // datos recuperados de la BD


        }
    }


    private void refrescarListaConPedidos() {
        List<Pedido> listaPedidos = dao.getAllPedidos();
        ArrayAdapter<Pedido> adapter = new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1, listaPedidos);
        lvPedidos.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        // TODO Auto-geperated method stub
        dao.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-geperated method stub
        dao.open();
        super.onResume();
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Desea Cargar Productos al pedido?")
                .setTitle("Cargar Productos")
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*agregarArchivo();*/
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*Toast.makeText(MainActivity.this,
                                "no escribira en el archivo",Toast.LENGTH_LONG).show();*/
                    }
                })
        ;
        AlertDialog dialog = builder.create();
        dialog.show();

        return false;
    }

    void imprimirListaProductos (ArrayList<Integer> lista){
        for(Integer p: lista){
            System.out.println("Id recuperado de la BD" + p.toString());
        }
    }
}

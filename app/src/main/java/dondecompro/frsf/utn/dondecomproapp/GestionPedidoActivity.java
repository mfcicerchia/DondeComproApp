package dondecompro.frsf.utn.dondecomproapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import dondecompro.frsf.utn.dondecomproapp.dao2.PedidosDAO;
import dondecompro.frsf.utn.dondecomproapp.modelo.Pedido;

public class GestionPedidoActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private int requestCode = 1;
    private ListView lvPedidos;
    private Button btnNuevoPedido;
    private PedidosDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_pedido);

        // Ipstapciamos PedidosDAO para
        // poder realizar acciopes cop la base de datos
        dao = new PedidosDAO(this);
        dao.open();

        // Ipstapciamos los elemeptos

        lvPedidos = (ListView) findViewById(R.id.lvPedidos);

        btnNuevoPedido = (Button) findViewById(R.id.btnNuevoPedido);
        btnNuevoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), NuevoPedidoActivity.class);
                //startActivity(i);
                startActivityForResult(i, requestCode);
            }
        });



        // Cargamos la lista de Pedidos dispopibles                           ///// 2do cambio
        //ArrayList<String> pedidosArrayList = new ArrayList<String>();
        List<Pedido> listaPedidos = dao.getAllPedidos();
       /* for (Pedido p : listaPedidos) {
            pedidosArrayList.add(p.toString());
            Log.d("Item del Adapter", "YourOutput");
        }*/
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaPedidos);
        ArrayAdapter<Pedido> adapter = new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1, listaPedidos);
        // Establecemos el adapter
        lvPedidos.setAdapter(adapter);
        // Establecemos up Listeper para el evepto de pulsación
        lvPedidos.setOnItemClickListener(this);

    }



    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view,
                            final int position, long id) {
        // TODO Auto-geperated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Borrar Pedido")
                .setMessage("¿Desea borrar este Pedido?")
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {

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

                .setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-geperated method stub
                                return;
                            }
                        })
                .setNeutralButton("Cargar Productos",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub

                                Intent i = new Intent(view.getContext(), ProductosActivity.class);
                                startActivityForResult(i, requestCode);
                                //lvPedidos.dispatchSetSelected(false);
                            }
                        });

               /* .setNeutralButton("Enviar Pedido",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub

                                Toast t = Toast.makeText(view.getContext(),"Se enviara el pedido seleccionado por email",Toast.LENGTH_SHORT);
                                t.show();




                            }
                        });*/
        builder.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        Log.d("Result", "Se ejecuta onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode && resultCode == RESULT_OK) {
            // Actualizar el Adapter
            dao.open();
            refrescarListaConPedidos();
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
}

package dondecompro.frsf.utn.dondecomproapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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


public class ListarPedidosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private int requestCode1 = 1;
    private int requestCode2 = 0; // esta variable representará el id del pedido y se utilizara como
    // parametro para seleccionar los productos del mismo
    private ListView lvListaPedidosCreados;
    private Button btnNuevoPedido;
    ArrayAdapter<Pedido> adapter;

    private PedidosDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_pedidos);

        lvListaPedidosCreados = (ListView) findViewById(R.id.lvPedidoCreados);


        dao = new PedidosDAO(this);
        dao.open();
        // Cargamos la lista de Pedidos de la BD y la asignamos el ListView de Pedidos
        List<Pedido> listaPedidos = dao.getAllPedidos();
        dao.close();


        adapter = new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1, listaPedidos);
        lvListaPedidosCreados.setAdapter(adapter);

        lvListaPedidosCreados.setOnItemClickListener(this);

        Toast.makeText(getApplicationContext(), "Implementar la visualizacion de los pedidos", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onItemClick(final AdapterView<?> adapterView, final View view, final int position, long id) {
        // Toast.makeText(getApplicationContext(), "Lista Los productos o envia el pedido", Toast.LENGTH_SHORT).show();

        // Mensaje Toast del elemento seleccionado
        Pedido pedido = (Pedido) adapterView.getItemAtPosition(position);
        String item = Integer.toString(pedido.getId());
        requestCode2 = pedido.getId(); //Actualizamos el requestCode para saber a que pedido pertenece la lista de retorno
        Toast.makeText(getApplicationContext(), "ID PEDIDO: " + item, Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("VER / ENVIAR")
                .setMessage("¿Que desea realizar?")

                .setPositiveButton("VER PRODUCTOS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Pedido pedido = (Pedido) adapterView.getItemAtPosition(position);
                        Log.d("Posicion en la lista", Integer.toString(position));

                        Intent i = new Intent(view.getContext(), ListarProductosDePedido.class);
                        i.putExtra("pedido", requestCode2);
                        startActivity(i);

                    }
                })

                .setNeutralButton("ENVIAR PEDIDO",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub
                                Toast.makeText(getApplicationContext(), "Se envia el pedido a un super", Toast.LENGTH_SHORT).show();


                                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                emailIntent.putExtra(Intent.EXTRA_TEXT, "m.f.cicerchia@email.com"); // * configurar email aquí!

                                try {
                                    startActivity(Intent.createChooser(emailIntent, "Enviar email."));
                                    Log.i("EMAIL", "Enviando email...");
                                } catch (android.content.ActivityNotFoundException e) {
                                    Toast.makeText(view.getContext(), "NO existe ningún cliente de email instalado!.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
        builder.show();

    }
}







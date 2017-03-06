package dondecompro.frsf.utn.dondecomproapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dondecompro.frsf.utn.dondecomproapp.dao2.PedidosDAO;
import dondecompro.frsf.utn.dondecomproapp.modelo.Producto;

public class ProductosToPedido extends AppCompatActivity {

    ArrayAdapter<Producto> adapter;


    ListView listViewProductos;
    List<Producto> listaProductos;

    ArrayList<Integer> listaProductoSeleccionados;

    Button btnAceptar;
    PedidosDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_to_pedido);


        listViewProductos = (ListView) findViewById(R.id.lvProductosToPedido);

        /*Obtengo todos los productos de la BD */
        dao = new PedidosDAO(this);
        dao.open();
        listaProductos = dao.getAllProductos();
        //dao.close();  // Nota: los productos fueron cargados previamente  ala BD, cuando inicio la aplicacion en la
        //       clase: InicioSplashActivity
        int longitud = listaProductos.size();
        Toast.makeText(getApplicationContext(), "cantidad de productos: " + Integer.toString(longitud), Toast.LENGTH_SHORT).show();

        listViewProductos.setChoiceMode(listViewProductos.CHOICE_MODE_MULTIPLE);
        adapter = new ArrayAdapter<Producto>(this, android.R.layout.simple_list_item_multiple_choice, listaProductos);

        listViewProductos.setAdapter(adapter);


        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            /// Si se da Aceptar retorna la lista de productos seleccionados
            /// a la actividad que lo invocó.

            @Override
            public void onClick(View v) {

                ArrayList<Integer> ids = new ArrayList<Integer>();

                Producto producto;

                int tam = listViewProductos.getCount();

                if (tam == 0) {
                    ids.add(0);
                } else {
                    for (int i = 0; i < tam; i++) {
                        if (listViewProductos.isItemChecked(i)) {
                            producto = (Producto) listViewProductos.getItemAtPosition(i);
                            ids.add(producto.getId()); // lo que guardo en la lista, son los IDs de los producctos elegidos
                        }
                    }
                }


                Intent intentRetorno = new Intent();
                intentRetorno.putExtra("productos", ids);

                setResult(RESULT_OK, intentRetorno);
                finish();
            }
        });
    }

}

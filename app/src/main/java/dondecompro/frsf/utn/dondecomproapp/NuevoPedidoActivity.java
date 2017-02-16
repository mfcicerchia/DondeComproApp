package dondecompro.frsf.utn.dondecomproapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dondecompro.frsf.utn.dondecomproapp.dao2.PedidosDAO;

public class NuevoPedidoActivity extends AppCompatActivity {

    public static int resultCode = 10;
    private Button btnAgregarPedido;
    private EditText etNombrePedido;
    private PedidosDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_pedido);



        dao = new PedidosDAO(this);
        dao.open();

        etNombrePedido = (EditText) findViewById(R.id.etNom);
        btnAgregarPedido= (Button) findViewById(R.id.btnAgreP);

        btnAgregarPedido.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String nombrePedido = etNombrePedido.getText().toString();

                if (nombrePedido.length() != 0) {
                    dao.crearPedido(nombrePedido);   /// 1er modificacion
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "No ha introducido texto", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
}

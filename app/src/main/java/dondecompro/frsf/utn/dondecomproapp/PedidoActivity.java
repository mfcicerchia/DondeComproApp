package dondecompro.frsf.utn.dondecomproapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PedidoActivity extends AppCompatActivity implements Button.OnClickListener{
    Button btnCrearPedido, btnCancelarNuevoPedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_pedido);

      /*  btnCrearPedido = (Button) findViewById(R.id.btnAgregar);
        btnCrearPedido.setOnClickListener(this);

        btnCancelarNuevoPedido = (Button) findViewById(R.id.btnCancelar);
        btnCancelarNuevoPedido.setOnClickListener(this);*/

        findViewById(R.id.btnAgregar).setOnClickListener(this);
        findViewById(R.id.btnCancelar).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnAgregar:
                Toast.makeText(getApplicationContext(), "Levantar la activity para cargar ls items del pedido", Toast.LENGTH_LONG).show();
                break;

            case R.id.btnCancelar:
                Toast.makeText(getApplicationContext(), "Se canceló el nuevo pedido", Toast.LENGTH_LONG).show();

                Intent i= new Intent(this,MainActivity.class);
                startActivity(i);
                break;
        }
    }
}

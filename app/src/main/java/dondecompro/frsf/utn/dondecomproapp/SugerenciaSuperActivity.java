package dondecompro.frsf.utn.dondecomproapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import dondecompro.frsf.utn.dondecomproapp.modelo.Supermercado;

/**
 * Created by Agustin
 */

public class SugerenciaSuperActivity extends AppCompatActivity {
    private static final String TAG = "SugerenciaSuperActivity";
    public static final int REQUEST_CODE = 1;

    private Button btnCancelar;
    private Button btnAgregar;
    private EditText txtNombre;
    private EditText txtDireccion;
    private EditText txtTelefono;
    private LatLng ubicacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        this.ubicacion = (LatLng) extras.get("coordenadas");
        this.setContentView(R.layout.activity_nuevo_supermercado);

        this.btnAgregar = (Button) findViewById(R.id.btnReclamar);
        this.btnCancelar = (Button) findViewById(R.id.btnCancelar);
        this.txtNombre = (EditText) findViewById(R.id.supermercadoNombre);
        this.txtTelefono= (EditText) findViewById(R.id.supermercadoTelefono);
        this.txtDireccion= (EditText) findViewById(R.id.supermercadoDireccion);

        this.btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitud = String.valueOf(ubicacion.latitude);
                String longitud = String.valueOf(ubicacion.longitude);
                String nombre = txtNombre.getText().toString();
                String telefono = txtTelefono.getText().toString();
                String direccion = txtDireccion.getText().toString();

                Supermercado nuevoSupermercado = new Supermercado(nombre, direccion, null, null, telefono, latitud, longitud);

                Intent returnIntent = new Intent();
//                returnIntent.putExtra("result", (Parcelable) nuevoSupermercado);
                setResult(Activity.RESULT_OK, returnIntent);

                finish();
            }
        });

        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();

//                Intent myIntent = new Intent(SugerenciaSuperActivity.this, UbicacionSupersActivity.class);
//                startActivity(myIntent);
            }
        });
    }

}

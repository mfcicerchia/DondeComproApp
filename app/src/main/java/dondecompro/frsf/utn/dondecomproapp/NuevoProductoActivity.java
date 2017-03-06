package dondecompro.frsf.utn.dondecomproapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import dondecompro.frsf.utn.dondecomproapp.utils.WebScrapingProductosEAN;

public class NuevoProductoActivity extends AppCompatActivity {

    private TextView textTitulo;
    private TextView textCodigoPoducto;
    private TextView textNombreProducto;
    private ImageView imgViewProducto;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "NuevoProductoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        textTitulo = (TextView)findViewById(R.id.textTitle);
        textCodigoPoducto = (TextView)findViewById(R.id.textValorLeido);
        textNombreProducto = (TextView) findViewById(R.id.textNombreProducto);
        imgViewProducto = (ImageView) findViewById(R.id.imgViewProducto);

        findViewById(R.id.btnBarCode).setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if (v.getId() == R.id.btnBarCode) {
                            // launch barcode activity.
                            Intent intent = new Intent(NuevoProductoActivity.this, CapturadorBarcodeActivity.class);
                            intent.putExtra(CapturadorBarcodeActivity.AutoFocus, true);
                            intent.putExtra(CapturadorBarcodeActivity.UseFlash, useFlash());

                            startActivityForResult(intent, RC_BARCODE_CAPTURE);
                        }
                    }
                }
        );
    }


    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(CapturadorBarcodeActivity.BarcodeObject);
                    textCodigoPoducto.setText(barcode.displayValue);
                    this.buscarProductoPorCodigoEAN(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    Toast.makeText(this.getApplicationContext(), "No se encontro Codigo de Producto", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                Toast.makeText(this.getApplicationContext(), "No se encontro Codigo de Producto", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean useFlash(){
        //TODO: Hacer que encienda flash
        return false;
    }

    private void buscarProductoPorCodigoEAN(String codigoProductoAEN){ //Ejemplo: 7790411000050
        AsyncTask tareaAsincronica = new WebScrapingProductosEAN(NuevoProductoActivity.this, this, codigoProductoAEN);
        tareaAsincronica.execute();

    }
}

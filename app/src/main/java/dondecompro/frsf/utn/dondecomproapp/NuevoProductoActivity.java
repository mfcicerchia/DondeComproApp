package dondecompro.frsf.utn.dondecomproapp;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
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
    private static final String TAG = "NuevoProductoActivity";
    private final float SENSOR_LIGHT_ILUMINACION_ESTANDAR = 30;

    private SensorManager sensorManager;
    private Sensor sensorLight;
    private SensorEventListener sensorEventListener;
    private float unidadesIluminacionActual = 100;

    private TextView textTitulo;
    private TextView textCodigoPoducto;
    private TextView textNombreProducto;
    private ImageView imgViewProducto;

    private static final int RC_BARCODE_CAPTURE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        this.textTitulo = (TextView) findViewById(R.id.textTitle);
        this.textCodigoPoducto = (TextView) findViewById(R.id.textValorLeido);
        this.textNombreProducto = (TextView) findViewById(R.id.textNombreProducto);
        this.imgViewProducto = (ImageView) findViewById(R.id.imgViewProducto);

        this.findViewById(R.id.btnBarCode).setOnClickListener(
                new View.OnClickListener() {
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
        // Administrador de Sensores y Sensor de luz
        this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (this.sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null) {
            this.sensorLight = this.sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        // Seteo de configuracion de luz en pantalla de modo manual.
        int brightnessMode = 0;
        try {
            brightnessMode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE);
            if (brightnessMode == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC) {
                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        this.sensorEventListener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                    unidadesIluminacionActual = event.values[0];
                    Log.v(NuevoProductoActivity.TAG,"Sensor LIGHT: "+unidadesIluminacionActual);
                }
            }
        };
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
        if(this.unidadesIluminacionActual < this.SENSOR_LIGHT_ILUMINACION_ESTANDAR){
            return true;
        }
        return false;
    }

    private void buscarProductoPorCodigoEAN(String codigoProductoAEN){ //Ejemplo: 7790411000050
        AsyncTask tareaAsincronica = new WebScrapingProductosEAN(NuevoProductoActivity.this, this, codigoProductoAEN);
        tareaAsincronica.execute();

    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        //This prevents a sensor from continually sensing data and draining the battery
        super.onPause();
        this.sensorManager.unregisterListener(this.sensorEventListener);
    }

    @Override
    protected void onResume() {
        // Register a listener for the sensor.
        super.onResume();
        this.sensorManager.registerListener(this.sensorEventListener, this.sensorLight, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onDestroy() {
        if (this.sensorLight != null) {
            this.sensorManager.unregisterListener(this.sensorEventListener);
        }
        super.onDestroy();
    }

}

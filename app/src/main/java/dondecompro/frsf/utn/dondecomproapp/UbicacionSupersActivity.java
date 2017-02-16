package dondecompro.frsf.utn.dondecomproapp;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Agustin on 15/02/2017.
 */

public class UbicacionSupersActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private GoogleMap myMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_supermercados);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        this.askForLocationPermission();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
            this.myMap = googleMap;
            this.iniciarMapa();
    }

    private void askForLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Permiso Dinamico
            // Chequear Permisos
            int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) { //No tengo el permiso

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //Explicar el Permiso
                    AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(UbicacionSupersActivity.this,R.style.myDialog));
                    builder.setTitle("Permisos de Localizacion!");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Puedo acceder al permiso de localizacion?");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            ActivityCompat.requestPermissions(UbicacionSupersActivity.this,
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                    UbicacionSupersActivity.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                        }
                    });
                    builder.show();

                } else { // Si no puedo pedir el permiso
                    ActivityCompat.requestPermissions(UbicacionSupersActivity.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            UbicacionSupersActivity.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                }
            }
            // Tengo el permiso
        }
        // Permiso Estatico no se puede cambiar.
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode) {
            case UbicacionSupersActivity.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (myMap != null){
                        iniciarMapa();
                    }
                } else {
                    // No tengo el permiso
                    Toast.makeText(this.getApplicationContext(), "Se requieren permisos GPS para funcionar", Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }
            // Otros permisos (case)
        }
    }


    private void iniciarMapa() {
        try {  // Habilitar Funciones
            this.myMap.setMyLocationEnabled(true);
            this.myMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {

                    myMap.addMarker(new MarkerOptions().position(latLng)
                            .title("Nuevo Super:")
                            .snippet("¿Desea agregar un Super en esta Ubicacion?")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_super_small)));

                    //Todo: sugerir super
                    //Intent myIntent = new Intent(UbicacionSupersActivity.this, SugerenciaSuperActivity.class);
                    //myIntent.putExtra("coordenadas", latLng);
                    //startActivity(myIntent);
                }
            });
        }catch (SecurityException exception){
            Toast.makeText(getApplicationContext(), "No posee permisos GPS", Toast.LENGTH_SHORT).show();
            Log.v("SecurityException", exception.getMessage());
        }
    }

}

package dondecompro.frsf.utn.dondecomproapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import dondecompro.frsf.utn.dondecomproapp.dao.DondeComproDAO;
import dondecompro.frsf.utn.dondecomproapp.modelo.Supermercado;
import dondecompro.frsf.utn.dondecomproapp.utils.ClienteAPIRestFoursquare;

/**
 * Created by Agustin on 15/02/2017.
 */

public class UbicacionSupersActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener {
    private static final String TAG = "UbicacionSupersActivity";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int ZOOM_GOOGLEMAPS_INICIAL = 14;

    private GoogleMap myMap;
    private ArrayList<Marker> listaMarkers;
    private ArrayList<Supermercado> listaSupermercados;
    private GoogleApiClient client;
    private LocationManager locationManager;
    private Location ubicacionActual;
    private DondeComproDAO dao;
    private ClienteAPIRestFoursquare superAPIRestFoursquare;
    private Marker nuevoSupermercadoMarker = null;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion_supermercados);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.listaMarkers = new ArrayList<Marker>();

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(
                        new GoogleApiClient.OnConnectionFailedListener() {
                            @Override
                            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                            }
                        })
                .addApi(LocationServices.API)
                .build();

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
                        Log.v("onRequestPermResult","Permiso Obtenido");
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

    /**
     * Contiene las funciones principales para iniciar el mapa
     */
    private void iniciarMapa() {
        try {  // Habilitar Funciones
            this.myMap.setMyLocationEnabled(true);
            this.askForEnableLocalizacion();
            this.myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); // Asigno una vista al mapa
            this.myMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng latLng) {
                    if(nuevoSupermercadoMarker != null){
                        nuevoSupermercadoMarker.remove();
                    }

                    nuevoSupermercadoMarker = myMap.addMarker(
                            new MarkerOptions()
                                .position(latLng)
                                .title("Nuevo Super:")
                                .snippet("¿Desea agregar un Super en esta Ubicacion?")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_googlemaps_supers_agregar))
                    );
                }
            });

            this.myMap.setOnMarkerClickListener(
                    new GoogleMap.OnMarkerClickListener(){
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            if (marker.equals(nuevoSupermercadoMarker)){
                                Intent myIntent = new Intent(UbicacionSupersActivity.this, SugerenciaSuperActivity.class);
                                myIntent.putExtra("coordenadas", nuevoSupermercadoMarker.getPosition());
                                startActivityForResult(myIntent, SugerenciaSuperActivity.REQUEST_CODE);
                                return true;
                            }
                            return false;
                        }
                    }
            );
        }catch (SecurityException exception){
            Toast.makeText(getApplicationContext(), "No posee permisos GPS", Toast.LENGTH_SHORT).show();
            Log.v("SecurityException", exception.getMessage());
        }
    }

    /**
     * Preguntar si se puede habilitar localizacion
     */
    private void askForEnableLocalizacion() {
        if (!this.isGPS()) { // Si esta inhabilitado el GPS, pruebo habilitarlo con el usuario
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(UbicacionSupersActivity.this, R.style.myDialog));
            builder.setTitle("Habilitar Localización");
            builder.setMessage("¿Permite habilitar localizacion?");
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
                            dialog.dismiss();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create();
            builder.show();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.ubicacionActual = LocationServices.FusedLocationApi.getLastLocation(this.client);
        Log.v("onConnected","acercarToLocalizacion");
        this.acercarToLocalizacion();
        this.agregarMarkerSupers();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("onConnectionSuspended","Conexion Suspendida");
    }


    /**
     * onActivityResult:
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SugerenciaSuperActivity.REQUEST_CODE){
            if (resultCode == RESULT_OK) {
//                Bundle extras = data.getExtras();
//                Supermercado nuevoSupermercado = (Supermercado) extras.get("result");
//                this.listaSupermercados.add(nuevoSupermercado);
                this.nuevoSupermercadoMarker.remove();

//                myMap.addMarker(
//                        new MarkerOptions()
//                                .position(new LatLng(Double.valueOf(nuevoSupermercado.getLatitud()),Double.valueOf(nuevoSupermercado.getLongitud())))
//                                .title(nuevoSupermercado.getNombre())
//                                .snippet("Contacto: "+nuevoSupermercado.getDireccion()+" . "+nuevoSupermercado.getTelefono())
//                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_googlemaps_supers_agregar))
//                );

                Toast.makeText(getApplicationContext(), "Sugerencia enviada", Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Sugerencia cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     *
     */
    private void acercarToLocalizacion() {
        if (this.ubicacionActual != null) {
            LatLng ubicacionActualLatLng = new LatLng(this.ubicacionActual.getLatitude(), this.ubicacionActual.getLongitude());
            Log.v("Ubicacion Actual", ubicacionActualLatLng.toString());
            this.myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActualLatLng, UbicacionSupersActivity.ZOOM_GOOGLEMAPS_INICIAL));
            return;
        } else {
            try {
                Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (lastLocation == null) { // GPS Apagado | No existe localizacion
                    Criteria criteria = new Criteria();
                    criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                    String provider = locationManager.getBestProvider(criteria, true);
                    lastLocation = locationManager.getLastKnownLocation(provider);
                }

                if (lastLocation != null) { // Encontre localizacion
                    LatLng ubicacionAproximada = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    Log.v("Ubicacion Aproximada", ubicacionAproximada.toString());
                    this.myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionAproximada, UbicacionSupersActivity.ZOOM_GOOGLEMAPS_INICIAL));
                    return;
                }

            } catch (SecurityException exception) {
                Toast.makeText(getApplicationContext(), "No posee permisos GPS", Toast.LENGTH_SHORT).show();
                Log.v("SecurityException", exception.getMessage());
            }
        }
        Log.v("GPS", "No se puede acercar");
    }

    /**
     * Traer Super Cercanos desde API Foursquare
     */
    private void agregarMarkerSupers(){
        if(this.ubicacionActual != null && this.listaMarkers != null){
            if(this.listaMarkers.isEmpty()){
                this.superAPIRestFoursquare = new ClienteAPIRestFoursquare(UbicacionSupersActivity.this, this, this.myMap, this.ubicacionActual,
                        new ClienteAPIRestFoursquare.AsyncTaskResponse(){
                            @Override
                            public void processFinish(ArrayList<Supermercado> listaSupermecadosOutput, ArrayList<Marker> listaMarkerOutput){
                                listaMarkers = listaMarkerOutput;
                                listaSupermercados = listaSupermecadosOutput;
                            }
                        });
                superAPIRestFoursquare.execute();
            }
        }


//        try{
//            this.dao = new DondeComproDAO(this);
//            this.dao.open();
//            Cursor supersCursor = dao.getAllSupermercados();
//
//            //Nos aseguramos de que existe al menos un registro
//            if (supersCursor.moveToFirst()) {
//                //Recorremos el cursor hasta que no haya más registros
//                do {
//                    String codigo = supersCursor.getString(0);
//                    String nombre = supersCursor.getString(1);
//                    String direccion = supersCursor.getString(2);
//                    String latitud = supersCursor.getString(3);
//                    String longitud = supersCursor.getString(4);
//                    myMap.addMarker( new MarkerOptions()
//                            .position(new LatLng(Double.parseDouble(latitud),Double.parseDouble(longitud)))
//                            .title(nombre)
//                            .snippet("Direccion: "+direccion)
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_googlemaps_supers_agregar)));
//
//                } while(supersCursor.moveToNext());
//            }
//            this.dao.close();
//
//        }catch (SQLiteException exception){
//            Toast.makeText(this.getApplicationContext(), "Error DB al traer Supers", Toast.LENGTH_LONG).show();
//        }
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Ubicacion Supers")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();

        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());

    }

    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    protected void onDestroy() {
        if(this.isGPS()){
            Toast.makeText(this.getApplicationContext(), "Recuerde desactivar su GPS para ahorrar bateria", Toast.LENGTH_SHORT).show();
        }
        super.onDestroy();
    }

    /**
     * Devuelve el estado actual del GPS
     * @return estado del GPS
     */
    private boolean isGPS(){
        if(locationManager != null){
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        Log.v(UbicacionSupersActivity.TAG,"isGPS(): locationManager is null");
        return false;
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this.getApplicationContext(), "Cambio de location: "+location.getLatitude()+" , "+location.getLongitude(), Toast.LENGTH_SHORT).show();
        Log.v("LocationListener","Cambio de location: "+location.getLatitude()+" , "+location.getLongitude());
    }


}

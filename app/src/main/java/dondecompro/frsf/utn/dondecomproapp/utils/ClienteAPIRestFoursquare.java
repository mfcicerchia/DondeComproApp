package dondecompro.frsf.utn.dondecomproapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import java.net.URL;

import dondecompro.frsf.utn.dondecomproapp.R;
import dondecompro.frsf.utn.dondecomproapp.modelo.Supermercado;

/**
 * Created by Agustin on 06/03/2017.
 */

public class ClienteAPIRestFoursquare extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "ClienteAPIRestF";

    private final String URL = "https://api.foursquare.com/v2/venues/search?";

    private final String CLIENT_ID = "LHE1HXFUQRZDCNN1QM02AEUVHHRBMHG3CR2LQFDOGJLUCGMC";
    private final String CLIENT_SECRET = "TDKCJCKFDSBHMP0BB4FTK3PSLQ2MGZPG5GLH21SPN3CYSZBY";
    private final String API_VERSION = "20170101";

    private ArrayList<Supermercado> listaSupermecados;
    private ArrayList<Marker> listaMarker;
    private JSONObject jsonResponse;
    private ProgressDialog dialogoProgreso;

    private Context contexto;
    private Activity activity;
    private Location localizacion;
    private GoogleMap mapa;
    public AsyncTaskResponse delegate = null;

    public ClienteAPIRestFoursquare(Context contexto, Activity activity, GoogleMap mapa, Location location, AsyncTaskResponse delegate) {
        this.contexto = contexto;
        this.activity = activity;
        this.mapa = mapa;
        this.localizacion = location;
        this.delegate = delegate;
        this.listaSupermecados = new ArrayList<Supermercado>();
        this.listaMarker = new ArrayList<Marker>();
    }

    // you may separate this or combined to caller class.
    public interface AsyncTaskResponse {
        void processFinish(ArrayList<Supermercado> listaSupermecadosOutput, ArrayList<Marker> listaMarkerOutput);
    }

    @Override
    protected Void doInBackground(Void... params) {
        //TODO: Ver cachear la respuesta del API.Si no hay conexion cargar la cacheada.

        String urlString = this.URL+"query=Supermercado"
                +"&ll="+this.localizacion.getLatitude()+","+this.localizacion.getLongitude() //Ejemlo: "-31.653307" "-60.7156267"
                +"&client_id="+this.CLIENT_ID
                +"&client_secret="+this.CLIENT_SECRET
                +"&v="+this.API_VERSION;
        try{
            this.jsonResponse = getJSONObjectFromURL(urlString);

        } catch (IOException e) {
            Log.v(ClienteAPIRestFoursquare.TAG,"doInBackground - IOException");
            Toast.makeText(this.contexto, "doInBackground IOException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (JSONException e) {
            Log.v(ClienteAPIRestFoursquare.TAG,"doInBackground - JSONException");
            Toast.makeText(this.contexto, "doInBackground JSONException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialogoProgreso = new ProgressDialog(this.contexto);
        this.dialogoProgreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.dialogoProgreso.setTitle("Supermercados cercanos");
        this.dialogoProgreso.setMessage("Buscando...");
        this.dialogoProgreso.setIndeterminate(false);
        this.dialogoProgreso.setCancelable(false);
        this.dialogoProgreso.show();
    }

    @Override
    protected void onPostExecute(Void o) {

        if(this.jsonResponse == null){
            return;
        }

        try {
            JSONArray json_array = this.jsonResponse.getJSONObject("response").optJSONArray("venues"); // Cada uno de los elementos del arreglo venues

            for (int i = 0; i < json_array.length(); i++) {
                this.listaSupermecados.add(this.obtenerSupermercado(json_array.getJSONObject(i)));
            }

            for(Supermercado supermercado : this.listaSupermecados){
                this.listaMarker.add(this.mapa.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(supermercado.getLatitud()),Double.valueOf(supermercado.getLongitud())))
                        .title(supermercado.getNombre())
                        .snippet("Contacto: "+supermercado.getDireccion()+" . "+supermercado.getTelefono())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));
            }

            this.delegate.processFinish(this.listaSupermecados,this.listaMarker);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.v(ClienteAPIRestFoursquare.TAG,"onPostExecute - JSONException");
            Toast.makeText(this.contexto, "onPostExecute: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        } finally {
            this.dialogoProgreso.dismiss();
        }

    }

    /**
     * Conexion y descarga JSON
     * @param urlString
     * @return JSONObject
     * @throws IOException
     * @throws JSONException
     */
    private static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line+"\n");
        }

        bufferedReader.close();

        jsonString = stringBuilder.toString();

        return new JSONObject(jsonString);
    }

    /**
     *
     * @param objetoJSON
     * @return
     */
    private Supermercado obtenerSupermercado(JSONObject objetoJSON) throws JSONException {

        String id = objetoJSON.getString("id");
        String nombre = objetoJSON.getString("name");
        String telefono = null;
        String direccion = null;

        if(!objetoJSON.getJSONObject("contact").isNull("phone")){
            telefono = objetoJSON.getJSONObject("contact").getString("phone");
        }
        if(!objetoJSON.getJSONObject("location").isNull("address")){
            direccion = objetoJSON.getJSONObject("location").getString("address");
        }

        String latitud = String.valueOf(objetoJSON.getJSONObject("location").getDouble("lat"));
        String longitud = String.valueOf(objetoJSON.getJSONObject("location").getDouble("lng"));

        Supermercado supermercado = new Supermercado(nombre,direccion,null,id,telefono,latitud,longitud);

        return supermercado;
    }

}

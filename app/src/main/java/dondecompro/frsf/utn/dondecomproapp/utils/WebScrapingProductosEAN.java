package dondecompro.frsf.utn.dondecomproapp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.renderscript.Script;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;

import dondecompro.frsf.utn.dondecomproapp.R;

/** Created by Agustin */

public class WebScrapingProductosEAN extends AsyncTask <Object, Integer, Object> {
    private static final String TAG = "WebScrapingProductosEAN";

    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.116 Safari/537.36";
    private final String USER_REFERRER = "https://www.google.com/";
    private final String URI_EAN_SEARCH = "https://www.ean-search.org/perl/ean-search.pl?q=";
    private final String URI_UPC_ITEMDB = "http://www.upcitemdb.com/upc/";
    private final String URI_BARCODE_LOOKUP = "https://www.barcodelookup.com/";
    private final String URI_UPC_SCAVENGER = "http://www.upcscavenger.com/barcode/";
    private final String URI_GOOGLE_IMAGENES = "https://www.google.com/search?site=imghp&tbm=isch&source=hp&gws_rd=cr&biw=1366&bih=635&q=";

    private Context contexto;
    private Activity activity;
    private String codigoProductoEAN;
    private ProgressDialog progreso;

    private String resultadoNombreProducto = null;
    private Bitmap resultadoImagenProducto = null;

    /**
     * Constructor de WebScrapingProductosEAN
     * @param activity
     * @param codigo
     */
    public WebScrapingProductosEAN(Context contexto, Activity activity, String codigo) {
        this.contexto = contexto;
        this.activity = activity;
        this.codigoProductoEAN = codigo;
    }

    /**
     * Ejecuta la tarea asincronica antes de comenzar
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progreso = new ProgressDialog(this.contexto);
        this.progreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        this.progreso.setTitle("Agregar Producto");
        this.progreso.setMessage("Buscando producto "+ this.codigoProductoEAN +"...");
        this.progreso.setIndeterminate(false);
        this.progreso.setCancelable(false);
        this.progreso.setProgress(0);
        this.progreso.setMax(100);
        this.progreso.show();
    }

    /**
     * Actividad principal de la tarea asincronica en segundo plano.
     * @param params
     * @return
     */
    @Override
    protected Object doInBackground(Object[] params) {
        try{
            this.resultadoNombreProducto = buscarProductoWeb_eansearch();
            publishProgress(20);

            if(this.resultadoNombreProducto == null){
                this.resultadoNombreProducto = buscarProductoWeb_upcitemdb();
                publishProgress(40);
            }
            if(this.resultadoNombreProducto == null){
                this.resultadoNombreProducto = buscarProductoWeb_barcodelookup();
                publishProgress(60);
            }
            if(this.resultadoNombreProducto == null){
                this.resultadoNombreProducto = buscarProductoWeb_upcscavenger();
                publishProgress(80);
            }

            if(this.resultadoImagenProducto == null){
                buscarURLPrimerResultadoGoogleImg(this.codigoProductoEAN);
            }

            publishProgress(100);

        }catch (Exception exception){
            Log.v(WebScrapingProductosEAN.TAG,exception.getMessage());
            return null;
        }
        return null;
    }

    /**
     * Ejecuta actualizaciones periodicas de la tarea asincronica.
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progreso = values[0].intValue();
        this.progreso.setProgress(progreso);
    }

    /**
     * Ejecuta tarea asincronica cuando terminó
     * @param
     */
    @Override
    protected void onPostExecute(Object o) {
        Log.v("onPostExecute","Resultado: "+resultadoNombreProducto);

        if(resultadoNombreProducto == null){
            Toast.makeText(this.contexto, "No se encuentra el producto", Toast.LENGTH_LONG).show();
        }
        else{
            TextView textNombreProducto = (TextView) activity.findViewById(R.id.textNombreProducto);
            textNombreProducto.setText(resultadoNombreProducto);
            Log.v("onPostExecute",resultadoNombreProducto);
        }
        if(this.resultadoImagenProducto != null){
            ImageView imgViewProducto = (ImageView) this.activity.findViewById(R.id.imgViewProducto);
            imgViewProducto.setImageBitmap(this.resultadoImagenProducto);
        }

        this.progreso.dismiss();
    }

    /**
     * Se ejecuta si la tarea asincronica es cancelada
     */
    @Override
    protected void onCancelled() {
        Toast.makeText(this.contexto,"Busqueda cancelada", Toast.LENGTH_LONG).show();
        this.progreso.dismiss();
    }


    private String buscarProductoWeb_eansearch(){
        try{
            Document document = Jsoup.connect(this.URI_EAN_SEARCH+this.codigoProductoEAN).userAgent(this.USER_AGENT).get();
            String nombreProducto = document.select("#main a").first().text();

            if(nombreProducto == this.codigoProductoEAN){
                return null;
            }

            return nombreProducto;

        }catch (Exception exception){
            Log.v("WebS_eansearch",exception.getMessage()+": "+exception.toString());
            return null;
        }
    }



    private String buscarProductoWeb_upcitemdb(){
        try{
            Document document = Jsoup.connect(this.URI_UPC_ITEMDB+this.codigoProductoEAN).userAgent(this.USER_AGENT).get();
            String nombreProducto = document.select("p[class=\"detailtitle\"] b").first().text();

            if(nombreProducto == null || nombreProducto == this.codigoProductoEAN){
                return null;
            }

            Element elementImagen = document.select("a[data-target=\"#popZoom\"] img").first();
            String srcImagen = elementImagen.attr("src");

            if(srcImagen != null){
                InputStream input = new java.net.URL(srcImagen).openStream(); // Download image from URL
                this.resultadoImagenProducto = BitmapFactory.decodeStream(input); // Decode Bitmap
            }

            return nombreProducto;

        }catch (Exception exception){
            Log.v("WebS_upcitemdb",exception.getMessage()+": "+exception.toString());
            return null;
        }
    }

    private String buscarProductoWeb_barcodelookup(){
        try{
            Document document = Jsoup.connect(this.URI_BARCODE_LOOKUP+this.codigoProductoEAN).userAgent(this.USER_AGENT).get();
            String nombreProducto = document.select("#main-barcode-headers h4").first().text();

            if(nombreProducto == null || nombreProducto == this.codigoProductoEAN){
                return null;
            }

            Element elementImagen = document.select("#images img").first();
            String srcImagen = elementImagen.attr("src");

            if(srcImagen != null){
                InputStream input = new java.net.URL(srcImagen).openStream(); // Download image from URL
                this.resultadoImagenProducto = BitmapFactory.decodeStream(input); // Decode Bitmap
            }

            return nombreProducto;

        }catch (Exception exception){
            Log.v("WebS_barcodelookup",exception.getMessage()+": "+exception.toString());
            return null;
        }
    }

    private String buscarProductoWeb_upcscavenger(){
        try{
            Document document = Jsoup.connect(this.URI_UPC_SCAVENGER+this.codigoProductoEAN).userAgent(this.USER_AGENT).get();
            String nombreProducto = document.select("h1.us4241186926 div.us1881050501 span").first().text();

            if(nombreProducto == null || nombreProducto == this.codigoProductoEAN){
                return null;
            }

            Element elementImagen = document.select("div.us454701852.us642808091.us1602733941.us3939388289 div[style=\"width: 99%; text-align: center;\"] img[itemprop=\"image\"]").first();
            String srcImagen = elementImagen.attr("src");

            if(srcImagen != null){
                InputStream input = new java.net.URL(srcImagen).openStream(); // Download image from URL
                this.resultadoImagenProducto = BitmapFactory.decodeStream(input); // Decode Bitmap
            }

            return nombreProducto;

        }catch (Exception exception){
            Log.v("WebS_upcscavenger",exception.getMessage()+": "+exception.toString());
            return null;
        }
    }

    private String buscarURLPrimerResultadoGoogleImg(String query){
        try {
            Document document = Jsoup.connect(this.URI_GOOGLE_IMAGENES+query).userAgent(this.USER_AGENT).referrer(this.USER_REFERRER).get();
            Elements elements = document.select("div.rg_meta");

            Element element = elements.first();
            JSONObject jsonObject = new JSONObject(element.childNode(0).toString());
            String imageUrl = (String) jsonObject.get("ou");
            String nombrealtImagen = (String) jsonObject.get("pt");

            if(imageUrl != null){

                InputStream input = new java.net.URL(imageUrl).openStream(); // Download image from URL
                this.resultadoImagenProducto = BitmapFactory.decodeStream(input); // Decode Bitmap

                if(this.resultadoNombreProducto == null && nombrealtImagen != null){
                    this.resultadoNombreProducto = nombrealtImagen;
                }
            }

            return imageUrl;

        }catch (Exception exception){
            Log.v("WebS_GoogleImg",exception.getMessage()+": "+exception.toString());
            return null;
        }
    }




}

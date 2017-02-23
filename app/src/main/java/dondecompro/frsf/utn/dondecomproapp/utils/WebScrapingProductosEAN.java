package dondecompro.frsf.utn.dondecomproapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.renderscript.Script;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dondecompro.frsf.utn.dondecomproapp.R;

/** Created by Agustin */

public class WebScrapingProductosEAN extends AsyncTask {
    private final String NO_EXISTE_PRODUCTO = "reverse EAN lookup";
    private final String URI_EAN_SEARCH = "https://www.ean-search.org/perl/ean-search.pl?q=";
    private final String URI_UPC_ITEMDB = "http://www.upcitemdb.com/upc/";

    private Context contexto;
    private ProgressDialog progreso;
    private String resultado;
    private String codigoProductoEAN;
    private TextView textNombreProducto;

    /**
     * Constructor de WebScrapingProductosEAN
     * @param context
     */
    public WebScrapingProductosEAN(Context context, String codigo, TextView textProducto) {
        this.contexto = context;
        this.codigoProductoEAN = codigo;
        this.textNombreProducto = textProducto;
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
            Document document = Jsoup.connect(this.URI_EAN_SEARCH+this.codigoProductoEAN).get();
            this.resultado = document.select("#main a").first().text();

        }catch (Exception exception){
            Log.v("webS",exception.getMessage());
            return null;
        }
        return null;
    }

    /**
     * onProgressUpdate: Ejecuta actualizaciones periodicas de la tarea asincronica.
     * POR EL MOMENTO NO UTILIZADO.
     */

    /**
     * Ejecuta tarea asincronica cuando terminó
     * @param
     */
    @Override
    protected void onPostExecute(Object o) {
        if(resultado == null || resultado == this.NO_EXISTE_PRODUCTO){
            Toast.makeText(this.contexto, "No se encuentra el producto", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this.contexto, this.resultado, Toast.LENGTH_LONG).show();
            this.textNombreProducto.setText(this.resultado);
            Log.v("onPostExecute",resultado);
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

}

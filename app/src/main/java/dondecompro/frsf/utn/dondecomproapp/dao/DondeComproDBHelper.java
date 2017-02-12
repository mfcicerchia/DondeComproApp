package dondecompro.frsf.utn.dondecomproapp.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Martin on 04/02/2017.
 */

public class DondeComproDBHelper extends SQLiteOpenHelper {


    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DondeComproDBHelper(Context context) {
        super(context, DondeComproDBMetadata.NOMBRE_DB, null, DondeComproDBMetadata.VERSION_DB);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            ArrayList<String> tablas = this.leerTablas();
            int i=1;
            for (String sql : tablas) {
                Log.d("Linea: "+ Integer.toString ( i ), sql.toString ());
                i++;
                db.execSQL(sql);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        //db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList<String> leerTablas() throws IOException {

        InputStream is = context.getAssets().open("estructura-db.sql");
        InputStreamReader r = new InputStreamReader(is, Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(r);
        String strLine;
        ArrayList<String> res = new ArrayList<String>();
        while ((strLine = br.readLine()) != null) {
            Log.d("SQL", strLine);
            res.add(strLine);
        }
        br.close();
        r.close();
        is.close();
        return res;
    }

}

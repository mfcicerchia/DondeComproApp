package dondecompro.frsf.utn.dondecomproapp.dao2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;

import dondecompro.frsf.utn.dondecomproapp.dao2.MySQLiteOpenHelper;
import dondecompro.frsf.utn.dondecomproapp.modelo.Supermercado;

/**
 * Created by Martin on 18/02/2017.
*/


public class SuperDAO {

    private SQLiteDatabase db;
    private MySQLiteOpenHelper dbHelper;

    
    private String [] columnasSupermercado = {MySQLiteOpenHelper.TablaSupermercado.COLUMNA_ID, MySQLiteOpenHelper.TablaSupermercado.COLUMNA_NOMBRE, MySQLiteOpenHelper.TablaSupermercado.COLUMNA_DIRECCION, MySQLiteOpenHelper.TablaSupermercado.COLUMNA_LATITUD, MySQLiteOpenHelper.TablaSupermercado.COLUMNA_LONGITUD};

    public SuperDAO(Context context) {
        dbHelper = new MySQLiteOpenHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }



    //////////////////////////////////////////
    ////// Manipulacion de Supermercados /////
    //////////////////////////////////////////

    public void crearSuper(String nombre, String direccion, String latitud, String longitud) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteOpenHelper.TablaSupermercado.COLUMNA_NOMBRE, nombre);
        values.put(MySQLiteOpenHelper.TablaSupermercado.COLUMNA_DIRECCION,direccion);
        values.put(MySQLiteOpenHelper.TablaSupermercado.COLUMNA_LATITUD,latitud);
        values.put(MySQLiteOpenHelper.TablaSupermercado.COLUMNA_LONGITUD,longitud);

        try {
            db.insert(MySQLiteOpenHelper.TablaSupermercado.TABLA_SUPERMERCADO, null, values);
        }catch(SQLiteException e){
            e.printStackTrace();
        }
    }


    public ArrayList<Supermercado> getAllSupermercados() {
        ArrayList<Supermercado> listaSupermercados = new ArrayList<Supermercado>();

        Cursor cursor = db.query(MySQLiteOpenHelper.TablaSupermercado.TABLA_SUPERMERCADO, columnasSupermercado, null, null, null, null, null);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Supermercado supermercado = cursorToSuper(cursor);
            listaSupermercados.add(supermercado);
            cursor.moveToNext();
        }

        cursor.close();
        return listaSupermercados;
    }

    public void borrarSupermercado(Supermercado s) {
        String id = s.getId();

        db.delete(MySQLiteOpenHelper.TablaSupermercado.TABLA_SUPERMERCADO, MySQLiteOpenHelper.TablaSupermercado.COLUMNA_ID + " = " + id,
                null);
    }

    private Supermercado cursorToSuper(Cursor cursor) {
        Supermercado supermercado = new Supermercado(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        return supermercado;
    }

    public long cantcantidadSupers(){
        Cursor c = db.rawQuery("select * from "+ MySQLiteOpenHelper.TablaSupermercado.TABLA_SUPERMERCADO,null);
        return c.getCount();
    }
}

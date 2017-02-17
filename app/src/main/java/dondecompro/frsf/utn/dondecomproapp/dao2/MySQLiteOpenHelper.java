package dondecompro.frsf.utn.dondecomproapp.dao2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Martin on 13/02/2017.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Pedidos";
    private static final int DATABASE_VERSION = 1;



    public static class TablaPedido{
        public static String TABLA_PEDIDO = "pedido";
        public static String COLUMNA_ID = "_id";
        public static String COLUMNA_NOMBRE= "nombre";
        public static String COLUMNA_ESTADO = "estado";
    }

    public static class TablaProducto{
        public static String TABLA_PRODUCTO = "producto";
        public static String COLUMNA_ID = "_id";
        public static String COLUMNA_NOMBRE = "nombre";
        public static String COLUMNA_PRECIO = "precio";
        public static String COLUMNA_CATEGORIA = "categoria";

       // public static String COLUMNA_FK = "fk";
    }

    private static final String DATABASE_CREATE_1 = "create table "
            + TablaPedido.TABLA_PEDIDO + "(" + TablaPedido.COLUMNA_ID
            + " integer primary key autoincrement, "
            + TablaPedido.COLUMNA_NOMBRE + " text not null, "
            + TablaPedido.COLUMNA_ESTADO + " text); ";

    private static final String DATABASE_CREATE_2 = "create table "
            + TablaProducto.TABLA_PRODUCTO + "("
            + TablaProducto.COLUMNA_ID + " integer primary key autoincrement, "
            + TablaProducto.COLUMNA_NOMBRE + " text not null, "
            + TablaProducto.COLUMNA_CATEGORIA + " text, "
            + TablaProducto.COLUMNA_PRECIO + " real );";

            /*+ TablaProducto.COLUMNA_FK + " integer not null, "
            + "foreign key (" + TablaProducto.COLUMNA_FK + " ) references " + TablaPedido.TABLA_PEDIDO + "("+ TablaPedido.COLUMNA_ID +")"
            + "on update cascade"
            + "on delete cascade" ;*/

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(DATABASE_CREATE_1);
        db.execSQL(DATABASE_CREATE_2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("delete table if exists " + TablaPedido.TABLA_PEDIDO);
        db.execSQL("delete table if exists " + TablaProducto.TABLA_PRODUCTO);
        onCreate(db);
    }

}


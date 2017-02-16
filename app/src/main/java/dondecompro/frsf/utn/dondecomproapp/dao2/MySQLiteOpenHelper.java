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

    /*public static class TablaProducto{
        public static String TABLA_PRODUCTO = "producto";
        public static String COLUMNA_ID = "_id";
        public static String COLUMNA_NOMBRE = "nombre";
        public static String COLUMNA_MARCA = "marca";
        public static String COLUMNA_PRECIO = "precio";
        public static String COLUMNA_FK = "fk";
    }*/

    private static final String DATABASE_CREATE = "create table "
            + TablaPedido.TABLA_PEDIDO + "(" + TablaPedido.COLUMNA_ID
            + " integer primary key autoincrement, "
            + TablaPedido.COLUMNA_NOMBRE + " text not null, "
            + TablaPedido.COLUMNA_ESTADO + " text);"

            /*+ "create table "
            + TablaProducto.TABLA_PRODUCTO + "("
            + TablaProducto.COLUMNA_ID + " integer primary key autoincrement, "
            + TablaProducto.COLUMNA_NOMBRE + " text not null, "
            + TablaProducto.COLUMNA_MARCA + " text not null, "
            + TablaProducto.COLUMNA_PRECIO + " real not null, "
            + TablaProducto.COLUMNA_FK + " integer not null, "
            + "foreign key (" + TablaProducto.COLUMNA_FK + " ) references " + TablaPedido.TABLA_PEDIDO + "("+ TablaPedido.COLUMNA_ID +")"
            + "on update cascade"
            + "on delete cascade" */;

    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("delete table if exists " + TablaPedido.TABLA_PEDIDO);
        onCreate(db);
    }

}


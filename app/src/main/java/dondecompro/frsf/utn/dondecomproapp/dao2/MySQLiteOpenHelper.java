package dondecompro.frsf.utn.dondecomproapp.dao2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Martin on 13/02/2017.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DondeComproApp.db";
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
    }

    public static class TablaSupermercado{
        public static String TABLA_SUPERMERCADO = "supermercado";
        public static String COLUMNA_ID = "_id";
        public static String COLUMNA_NOMBRE = "nombre";
        public static String COLUMNA_DIRECCION = "direccion";
        public static String COLUMNA_TELEFONO = "telefono";
        public static String COLUMNA_LATITUD = "latitud";
        public static String COLUMNA_LONGITUD = "longitud";
    }

    public static class TablaPedidoTieneProducto {
        public static String TABLA_PEDIDO_TIENE_PRODUCTO = "pedido_tiene_producto";
        public static String COLUMNA_PEDIDO_ID = "id_pedido";
        public static String COLUMNA_PRODUCTO_ID = "id_producto";
        public static String COLUMNA_CANTIDAD_PRODUCTO = "cantidad";
    }



    private static final String DATABASE_CREATE_TABLE_PEDIDO = "create table "
            + TablaPedido.TABLA_PEDIDO + "(" + TablaPedido.COLUMNA_ID + " integer primary key autoincrement, "
            + TablaPedido.COLUMNA_NOMBRE + " text not null, "
            + TablaPedido.COLUMNA_ESTADO + " text); ";

    private static final String DATABASE_CREATE_TABLE_PRODUCTO = "create table "
            + TablaProducto.TABLA_PRODUCTO + "("
            + TablaProducto.COLUMNA_ID + " integer primary key autoincrement, "
            + TablaProducto.COLUMNA_NOMBRE + " text not null, "
            + TablaProducto.COLUMNA_CATEGORIA + " text, "
            + TablaProducto.COLUMNA_PRECIO + " real );";

    private static final String DATABASE_CREATE_TABLE_SUPERMERCADO = "create table "
            + TablaSupermercado.TABLA_SUPERMERCADO + "("
            + TablaSupermercado.COLUMNA_ID + " integer primary key autoincrement, "
            + TablaSupermercado.COLUMNA_NOMBRE + " text not null, "
            + TablaSupermercado.COLUMNA_DIRECCION + " text, "
            + TablaSupermercado.COLUMNA_TELEFONO + " text, "
            + TablaSupermercado.COLUMNA_LATITUD + " real, "
            + TablaSupermercado.COLUMNA_LONGITUD + " real );";

    private static final String DATABASE_CREATE_TABLE_PEDIDO_TO_PRODUCTO = "create table "
            + TablaPedidoTieneProducto.TABLA_PEDIDO_TIENE_PRODUCTO + "("
            + TablaPedidoTieneProducto.COLUMNA_PEDIDO_ID + " integer not null, "
            + TablaPedidoTieneProducto.COLUMNA_PRODUCTO_ID + " integer not null, "
            + TablaPedidoTieneProducto.COLUMNA_CANTIDAD_PRODUCTO + " integer );";


    public MySQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(DATABASE_CREATE_TABLE_PEDIDO);
        db.execSQL(DATABASE_CREATE_TABLE_PRODUCTO);
        db.execSQL(DATABASE_CREATE_TABLE_SUPERMERCADO);
        db.execSQL(DATABASE_CREATE_TABLE_PEDIDO_TO_PRODUCTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("delete table if exists " + TablaPedido.TABLA_PEDIDO);
        db.execSQL("delete table if exists " + TablaProducto.TABLA_PRODUCTO);
        db.execSQL("delete table if exists " + TablaSupermercado.TABLA_SUPERMERCADO);
        db.execSQL("delete table if exists " + TablaPedidoTieneProducto.TABLA_PEDIDO_TIENE_PRODUCTO);
        onCreate(db);
    }

}


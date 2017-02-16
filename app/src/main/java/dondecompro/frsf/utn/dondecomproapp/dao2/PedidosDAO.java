package dondecompro.frsf.utn.dondecomproapp.dao2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dondecompro.frsf.utn.dondecomproapp.modelo.Pedido;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Martin on 13/02/2017.
 */

public class PedidosDAO {


    private SQLiteDatabase db;
    private MySQLiteOpenHelper dbHelper;

    private String[] columnasPedido = {MySQLiteOpenHelper.TablaPedido.COLUMNA_ID, MySQLiteOpenHelper.TablaPedido.COLUMNA_NOMBRE, MySQLiteOpenHelper.TablaPedido.COLUMNA_ESTADO };

    public PedidosDAO(Context context) {
        dbHelper = new MySQLiteOpenHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }



    public void crearPedido(String nombrePedido) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteOpenHelper.TablaPedido.COLUMNA_NOMBRE, nombrePedido);
        values.put(MySQLiteOpenHelper.TablaPedido.COLUMNA_ESTADO, "pendiente de envio");
        db.insert(MySQLiteOpenHelper.TablaPedido.TABLA_PEDIDO, null, values);
    }


    public List<Pedido> getAllPedidos() {
        List<Pedido> listaPedidos = new ArrayList<Pedido>();

        Cursor cursor = db.query(MySQLiteOpenHelper.TablaPedido.TABLA_PEDIDO, columnasPedido, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Pedido nuevaPedido = cursorToPedido(cursor);
            listaPedidos.add(nuevaPedido);
            cursor.moveToNext();
        }

        cursor.close();
        return listaPedidos;
    }

    public void borrarPedido(Pedido p) {
        long id = p.getId();

        db.delete(MySQLiteOpenHelper.TablaPedido.TABLA_PEDIDO, MySQLiteOpenHelper.TablaPedido.COLUMNA_ID + " = " + id,
                null);
    }

    private Pedido cursorToPedido(Cursor cursor) {
        Pedido Pedido = new Pedido(cursor.getInt(0),cursor.getString(1));
        return Pedido;
    }
    
}



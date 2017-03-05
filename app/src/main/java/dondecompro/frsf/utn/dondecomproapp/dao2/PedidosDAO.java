package dondecompro.frsf.utn.dondecomproapp.dao2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import dondecompro.frsf.utn.dondecomproapp.modelo.Pedido;
import dondecompro.frsf.utn.dondecomproapp.modelo.Producto;



/**
 * Created by Martin on 13/02/2017.
 */

public class PedidosDAO {


    private SQLiteDatabase db;
    private MySQLiteOpenHelper dbHelper;

    private String[] columnasPedido = {MySQLiteOpenHelper.TablaPedido.COLUMNA_ID, MySQLiteOpenHelper.TablaPedido.COLUMNA_NOMBRE, MySQLiteOpenHelper.TablaPedido.COLUMNA_ESTADO };
    private String [] columnasProducto = {MySQLiteOpenHelper.TablaProducto.COLUMNA_ID, MySQLiteOpenHelper.TablaProducto.COLUMNA_NOMBRE, MySQLiteOpenHelper.TablaProducto.COLUMNA_PRECIO,MySQLiteOpenHelper.TablaProducto.COLUMNA_CATEGORIA };
    private String [] columnasPedidoTieneProducto = {MySQLiteOpenHelper.TablaPedidoTieneProducto.COLUMNA_PEDIDO_ID,MySQLiteOpenHelper.TablaPedidoTieneProducto.COLUMNA_PRODUCTO_ID};

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
        try {
            db.insert(MySQLiteOpenHelper.TablaPedido.TABLA_PEDIDO, null, values);
        }catch(SQLException ex){
            ex.printStackTrace();
        }

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


    ////////////////////////////////////////
    ////// Manipulacion de productos////////
    ////////////////////////////////////////


    public void crearProducto(String nombre, float precio, String categoria) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteOpenHelper.TablaProducto.COLUMNA_NOMBRE, nombre);
        values.put(MySQLiteOpenHelper.TablaProducto.COLUMNA_PRECIO, precio);
        values.put(MySQLiteOpenHelper.TablaProducto.COLUMNA_CATEGORIA, categoria);

        db.insert(MySQLiteOpenHelper.TablaProducto.TABLA_PRODUCTO, null, values);
    }


    public ArrayList<Producto> getAllProductos() {
        ArrayList<Producto> listaProductos = new ArrayList<Producto>();

        Cursor cursor = db.query(MySQLiteOpenHelper.TablaProducto.TABLA_PRODUCTO, columnasProducto, null, null, null, null, null);

        //Nos aseguramos de que existe al menos un registro
                /*if (cursor.moveToFirst()) {
                    //Recorremos el cursor hasta que no haya más registros
                    do {
                        String nombre= cursor.getString(0);
                        float precio = cursor.getFloat(1);
                        String categoria = cursor.getString(2);
                        Producto p = new Producto(nombre,precio,categoria);
                        listaProductos.add(p);
                    } while(cursor.moveToNext());
                }*/

     cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Producto producto = cursorToProducto(cursor);
            listaProductos.add(producto);
            cursor.moveToNext();
        }

        cursor.close();
        return listaProductos;
    }

    private Producto cursorToProducto(Cursor cursor) {
        Producto producto = new Producto(cursor.getInt(0),cursor.getString(1),cursor.getFloat(2),cursor.getString(3));
        return producto;
    }


    ////////////////////////////////////////////////////////////
    //////////////////
    ////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////
    /// Metodo para cargar asignarle productos a un pedido   ///
    ////////////////////////////////////////////////////////////


    public void cargarProductosAlPedido(ArrayList<Integer> listaProductos, Integer idPedido) {
        for (Integer idProducto : listaProductos) {

            ContentValues values = new ContentValues();
            values.put(MySQLiteOpenHelper.TablaPedidoTieneProducto.COLUMNA_PEDIDO_ID, idPedido);
            values.put(MySQLiteOpenHelper.TablaPedidoTieneProducto.COLUMNA_PRODUCTO_ID, idProducto);
            values.put(MySQLiteOpenHelper.TablaPedidoTieneProducto.COLUMNA_CANTIDAD_PRODUCTO, 1); // por defecto la cantidad insertada va a ser 1.

            try {
                db.insert(MySQLiteOpenHelper.TablaPedidoTieneProducto.TABLA_PEDIDO_TIENE_PRODUCTO, null, values);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**Modificar el query para que traiga solo los de ese pedido (esta trayedo todoo ahora)*/
    public ArrayList<Integer> getProductosDePedido(int idPedido){
        ArrayList<Integer> listaIdProductos = new ArrayList<>();
        Cursor cursor = db.query(MySQLiteOpenHelper.TablaPedidoTieneProducto.TABLA_PEDIDO_TIENE_PRODUCTO, columnasPedidoTieneProducto, Integer.toString(idPedido), null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            listaIdProductos.add(cursor.getInt(1)); // recorro el cursor y obtengo los ids de los productos
            cursor.moveToNext();
        }
        cursor.close();
        return listaIdProductos;
    }



}



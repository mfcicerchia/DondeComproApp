package dondecompro.frsf.utn.dondecomproapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import dondecompro.frsf.utn.dondecomproapp.modelo.Pedido;
import dondecompro.frsf.utn.dondecomproapp.modelo.Producto;


/**
 * Created by Martin on 12/02/2017.
 */

public class DondeComproDAO {

    /**
     * Como no existe el Join directo creo la consulta por afuera y luego la uso en el rawquery de cursor listarProductosPorPedido
     */
    private static final String _PRODUCTOS_X_PEDID_X_idPEdido = "SELECT " + DondeComproDBMetadata.TABLA_PRODUCTO_ALIAS + "." + DondeComproDBMetadata.TablaProductoMetadata._ID + ", "
            + DondeComproDBMetadata.TablaProductoMetadata.CODIGO_BARRAS + ", "
            + DondeComproDBMetadata.TablaProductoMetadata.NOMBRE + ", "
            + DondeComproDBMetadata.TablaProductoMetadata.PRECIO + ", "
            + DondeComproDBMetadata.TablaProductoMetadata.CATEGORIA + ", " +
            "FROM " + DondeComproDBMetadata.TABLA_PRODUCTO + " " + DondeComproDBMetadata.TABLA_PRODUCTO_ALIAS + ", "
            + DondeComproDBMetadata.TABLA_PEDIDO + " " + DondeComproDBMetadata.TABLA_PEDIDO_ALIAS +
            "WHERE " + DondeComproDBMetadata.TABLA_PRODUCTO_ALIAS + "." + DondeComproDBMetadata.TablaProductoMetadata.ID_PEDIDO + " = " + DondeComproDBMetadata.TABLA_PEDIDO_ALIAS + "." + DondeComproDBMetadata.TablaPedidoMetadata._ID + "AND "
            + DondeComproDBMetadata.TABLA_PEDIDO_ALIAS + "." + DondeComproDBMetadata.TablaPedidoMetadata._ID + " = ?";

    private static final String _PRODUCTOS_X_PEDID_X_nombrePEDIDO = "SELECT " + DondeComproDBMetadata.TABLA_PRODUCTO_ALIAS + "." + DondeComproDBMetadata.TablaProductoMetadata._ID + ", "
            + DondeComproDBMetadata.TablaProductoMetadata.CODIGO_BARRAS + ", "
            + DondeComproDBMetadata.TablaProductoMetadata.NOMBRE + ", "
            + DondeComproDBMetadata.TablaProductoMetadata.PRECIO + ", "
            + DondeComproDBMetadata.TablaProductoMetadata.CATEGORIA + ", " +
            "FROM " + DondeComproDBMetadata.TABLA_PRODUCTO + " " + DondeComproDBMetadata.TABLA_PRODUCTO_ALIAS + ", "
            + DondeComproDBMetadata.TABLA_PEDIDO + " " + DondeComproDBMetadata.TABLA_PEDIDO_ALIAS +
            "WHERE " + DondeComproDBMetadata.TABLA_PRODUCTO_ALIAS + "." + DondeComproDBMetadata.TablaProductoMetadata.ID_PEDIDO + " = " + DondeComproDBMetadata.TABLA_PEDIDO_ALIAS + "." + DondeComproDBMetadata.TablaPedidoMetadata._ID + "AND "
            + DondeComproDBMetadata.TABLA_PEDIDO_ALIAS + "." + DondeComproDBMetadata.TablaPedidoMetadata.NOMBRE + " = ?";


    /*select pro.*                                              + ;
    from pedido as pe, producto as pro
    where  pe.id_pedido = pro.id_pedido // reemplazar id_pedido de pro por "?" (para el rawQuery)*/

    private final Context context;
    private DondeComproDBHelper dbHelper;
    private SQLiteDatabase db;

    public DondeComproDAO(Context c) {
        context = c;
        this.dbHelper = new DondeComproDBHelper ( context );
    }

    public void open() {
        this.open ( false );
    }

    public void open(Boolean toWrite) {
        if (toWrite) {
            db = dbHelper.getWritableDatabase ();
        } else {
            db = dbHelper.getReadableDatabase ();
        }
    }

    public void close() {
        db = dbHelper.getReadableDatabase ();
    }


    //TODO: IMPLEMENTACION DE CURSORES

    // listar todos los Productos
    public Cursor getAllProductos() {
        String[] atributos = new String[]{DondeComproDBMetadata.TablaProductoMetadata._ID,
                DondeComproDBMetadata.TablaProductoMetadata.CODIGO_BARRAS,
                DondeComproDBMetadata.TablaProductoMetadata.NOMBRE,
                DondeComproDBMetadata.TablaProductoMetadata.PRECIO,
                DondeComproDBMetadata.TablaProductoMetadata.CATEGORIA};

        Cursor c = db.query ( DondeComproDBMetadata.TABLA_PRODUCTO, atributos, null, null, null, null, null );
        return c;
    }

    // Listar todos los Pedidos
    public Cursor getAllPedidos() {
        String[] atributos = new String[]{DondeComproDBMetadata.TablaPedidoMetadata._ID,
                DondeComproDBMetadata.TablaPedidoMetadata.NOMBRE,
                DondeComproDBMetadata.TablaPedidoMetadata.ESTADO};

        Cursor c = db.query ( DondeComproDBMetadata.TABLA_PEDIDO, atributos, null, null, null, null, null );
        return c;
    }

    // Lisar todos los Supermercados
    public Cursor getAllSupermercados() {

        String[] atributos = new String[]{DondeComproDBMetadata.TablaSupermercadoMetadadata._ID,
                DondeComproDBMetadata.TablaSupermercadoMetadadata.NOMBRE,
                DondeComproDBMetadata.TablaSupermercadoMetadadata.DIRECCION,
                DondeComproDBMetadata.TablaSupermercadoMetadadata.LATITUD,
                DondeComproDBMetadata.TablaSupermercadoMetadadata.LONGITUD};

        Cursor c = db.query ( DondeComproDBMetadata.TABLA_SUPERMECADO, atributos, null, null, null, null, null );
        ;
        return c;
    }

    // Lisar los productos por pedido
    public Cursor listarProductosPorPedido(int idPedido) {

        String[] atributos = new String[]{DondeComproDBMetadata.TablaSupermercadoMetadadata._ID,
                DondeComproDBMetadata.TablaSupermercadoMetadadata.NOMBRE,
                DondeComproDBMetadata.TablaSupermercadoMetadadata.DIRECCION,
                DondeComproDBMetadata.TablaSupermercadoMetadadata.LATITUD,
                DondeComproDBMetadata.TablaSupermercadoMetadadata.LONGITUD};

        Cursor c = db.query ( DondeComproDBMetadata.TABLA_SUPERMECADO, atributos, null, null, null, null, null );
        ;
        return c;


    }

    //TODO: CURSOR PARA LISTAR TODOS LOS PRODUCTOS DE UN PEDIDO (2 formas)

    public Cursor getProductosPorIdPedido(int idPedido) {
        Cursor c = null;
        try {
            c = db.rawQuery ( _PRODUCTOS_X_PEDID_X_idPEdido, new String[]{Integer.toString ( idPedido )} );
        } catch (SQLiteException ex) {
            ex.printStackTrace ();
        }
        return c;
    }

    public Cursor getProductosPorNombrePedido(String nombrePedido) {
        Cursor c = null;
        try {
            c = db.rawQuery ( _PRODUCTOS_X_PEDID_X_idPEdido, new String[]{nombrePedido} );
        } catch (SQLiteException ex) {
            ex.printStackTrace ();
        }
        return c;
    }


    //TODO: IMPLEMENTACION DE INSERCIONES (INSERTS)

    // Nuevo Producto
    public void nuevoProducto(Producto producto) {
        ContentValues nuevoProducto = new ContentValues ();

        nuevoProducto.put ( DondeComproDBMetadata.TablaProductoMetadata._ID, producto.getId () );
        nuevoProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.CODIGO_BARRAS, producto.getCodigoBarra () );
        nuevoProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.NOMBRE, producto.getNombre () );
        nuevoProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.MARCA, producto.getMarca () );
        nuevoProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.PRECIO, producto.getPrecio () );
        nuevoProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.CATEGORIA, producto.getCategoria () );
        nuevoProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.DESCRIPCION, producto.getDescripcion () );

        try {
            db.insert ( DondeComproDBMetadata.TABLA_PRODUCTO, null, nuevoProducto );
        } catch (SQLiteException ex) {
            ex.printStackTrace ();
        }
    }


    public void nuevoPedido(Pedido Pedido) {
        ContentValues nuevoPedido = new ContentValues ();

        nuevoPedido.put ( DondeComproDBMetadata.TablaPedidoMetadata._ID, Pedido.getId () );
        nuevoPedido.put ( DondeComproDBMetadata.TablaPedidoMetadata.NOMBRE, Pedido.getNombre () );
        nuevoPedido.put ( DondeComproDBMetadata.TablaPedidoMetadata.ESTADO, Pedido.getEstado () );


        try {
            db.insert ( DondeComproDBMetadata.TABLA_PEDIDO, null, nuevoPedido );
        } catch (SQLiteException ex) {
            ex.printStackTrace ();
        }
    }


    //TODO: IMPLEMENTACION DE ACTUALIZACIONES(UPDATES)
    public void actualizarProducto(Producto producto) {
        /**definimos los valores a actualizar**/
        ContentValues actProducto = new ContentValues ();
        actProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.CODIGO_BARRAS, producto.getCodigoBarra () );
        actProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.NOMBRE, producto.getNombre () );
        actProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.MARCA, producto.getMarca () );
        actProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.PRECIO, producto.getPrecio () );
        actProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.CATEGORIA, producto.getCategoria () );
        actProducto.put ( DondeComproDBMetadata.TablaProductoMetadata.DESCRIPCION, producto.getDescripcion () );

        /**Actualizamos la Base de Datos**/
        try {
            db.update ( DondeComproDBMetadata.TABLA_PRODUCTO, actProducto, DondeComproDBMetadata.TablaProductoMetadata._ID + "=" + producto.getId (), null );
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
    }


    public void actualizarPedido(Pedido pedido) {
        /**definimos los valores a actualizar**/
        ContentValues actPedido = new ContentValues ();


        actPedido.put ( DondeComproDBMetadata.TablaPedidoMetadata.NOMBRE, pedido.getNombre () );
        actPedido.put ( DondeComproDBMetadata.TablaPedidoMetadata.ESTADO, pedido.getNombre () );
        for (Producto p : pedido.getListaProductos ()) {
            actualizarProducto ( p );
        }

        /**Actualizamos la Base de Datos**/
        try {
            db.update ( DondeComproDBMetadata.TABLA_PEDIDO, actPedido, DondeComproDBMetadata.TablaPedidoMetadata._ID + "=" + pedido.getId (), null );
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }

    }


    //TODO: IMPLEMENTACION DE ELIMINACIONES(DELETES)

    public void borrarProducto(Producto producto) {
        /**Borramos el producto*/
        try {
            db.delete ( DondeComproDBMetadata.TABLA_PRODUCTO, DondeComproDBMetadata.TablaProductoMetadata._ID + " = " + producto.getId (), null );
        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
    }

    public void borrarPedido(Pedido pedido) {
        try {
            for (Producto p : pedido.getListaProductos ()) {
                /**Borramos cada producto del pedido*/
                borrarProducto ( p );
            }
            /**por ultimo borramos el pedido*/
            db.delete ( DondeComproDBMetadata.TABLA_PEDIDO, DondeComproDBMetadata.TablaPedidoMetadata._ID + " = " + pedido.getId (), null );

        } catch (SQLException ex) {
            ex.printStackTrace ();
        }
    }




}

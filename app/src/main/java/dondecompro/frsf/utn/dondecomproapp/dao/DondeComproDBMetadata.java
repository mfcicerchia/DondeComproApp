package dondecompro.frsf.utn.dondecomproapp.dao;

import android.provider.BaseColumns;

/**
 * Created by Martin on 04/02/2017.
 *
 * Esquema de la base de datos para productos o Contrato
 */


public class DondeComproDBMetadata {

    public static final String NOMBRE_DB = "DondeComproDB";
    public static final int VERSION_DB = 1;


    public static final String TABLA_PEDIDO= "PEDIDO";
    public static final String TABLA_PEDIDO_ALIAS= " ped";

    public static final String TABLA_PRODUCTO= "PRODUCTO";
    public static final String TABLA_PRODUCTO_ALIAS= " prod";

    public static final String TABLA_SUPERMECADO= "SUPERMERCADO";
    public static final String TABLA_SUPERMERCADO_ALIAS= " sup";


    public static class TablaPedidoMetadata  {
        public static String COLUMNA_ID = "_id";
        public static final String COLUMNA_NOMBRE ="nombre_pedido";
        //public static final String TIT_PEDI_ALIAS ="NOM_PED";
        public static final String COLUMNA_ESTADO = "estado";
    }

    public static class TablaProductoMetadata implements BaseColumns{
        public static final String NOMBRE ="NOMBRE";
        public static final String PRODUCTO_ALIAS ="NOM_PROD";
        public static final String ID_PEDIDO ="ID_PEDIDO";
        public static final String CODIGO_BARRAS ="CODIGO_BARRAS";
        public static final String MARCA = "MARCA";
        public static final String CATEGORIA = "CATEGORIA";
        public static final String PRECIO = "PRECIO";
        public static final String DESCRIPCION = "DESCRIPCION";
        public static final String MAIL ="CORREO_ELECTRONICO";
    }


    public static class TablaSupermercadoMetadadata implements BaseColumns{
        public static final String NOMBRE = "NOMBRE";
        public static final String DIRECCION = "DIRECCION";
        public static final String TELEFONO = "TELEFONO";
        public static final String CORREO = "CORREO";
        public static final String LATITUD = "LATITUD";
        public static final String LONGITUD = "LONGITUD";
    }
}

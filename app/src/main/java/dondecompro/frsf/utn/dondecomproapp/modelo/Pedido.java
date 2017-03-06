package dondecompro.frsf.utn.dondecomproapp.modelo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Martin on 12/02/2017.
 */

public class Pedido {

    private int id;
    private Date fecha;
    private String nombre;
    private String estado;
    ArrayList<Producto> listaProductos;

    public Pedido(int id, String nombre) {
        this.id = id;
        this.fecha = new Date();
        this.nombre = nombre;
        this.estado = "pendiente de envio";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }
    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(fecha); }

    @Override
    public String toString() {
        return " Pedido (ID:"+id+") ["+getDateTime()+"]" +
                 "\n Nombre: "+ nombre +
                 "\n Estado: "+ estado  ;
    }

}

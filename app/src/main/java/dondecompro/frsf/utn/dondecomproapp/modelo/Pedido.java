package dondecompro.frsf.utn.dondecomproapp.modelo;

import java.util.ArrayList;

/**
 * Created by Martin on 12/02/2017.
 */

public class Pedido {

    private int id;
    private String nombre;
    private String estado;
    ArrayList<Producto> listaProductos;

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


}

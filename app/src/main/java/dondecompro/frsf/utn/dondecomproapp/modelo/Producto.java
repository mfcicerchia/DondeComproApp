package dondecompro.frsf.utn.dondecomproapp.modelo;

/**
 * Created by Martin on 04/02/2017.
 */

public class Producto {

    private int id;
    private  long codigoBarra;
    private  String nombre;
    private  String marca;
    private float precio;
    private String categoria;
    private  String descripcion;

    public Producto(int id, long codigoBarra, String nombre, String marca, float precio, String categoria, String descripcion) {
        this.id = id;
        this.codigoBarra = codigoBarra;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.categoria = categoria;
        this.descripcion = descripcion;
    }

    public Producto(int id, String nombre, String marca, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.categoria = categoria;
    }

    public Producto(int id, String nombre, float precio, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public Producto(String nombre, float precio, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setCodigoBarra(long codigoBarra) {
        this.codigoBarra = codigoBarra;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public long getCodigoBarra() {
        return codigoBarra;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }

    public float getPrecio() {
        return precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

  /*  public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }*/

    @Override
    public String toString() {
        return nombre + " ("+id+")" +
                "\nPrecio: $"+ precio + " - Categoria: " + categoria ;
    }

}

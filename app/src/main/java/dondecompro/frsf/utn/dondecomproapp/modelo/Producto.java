package dondecompro.frsf.utn.dondecomproapp.modelo;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Created by Martin on 09/02/2017.
 */


public class Producto {

    private Integer id;
    private String nombre;
    private String categoria;
    private Double precio;


    DecimalFormat f = new DecimalFormat("##.00");




        public Producto(Integer i, String n, double v) {

        }

        public Producto(Integer i, String n, double p, String c) {
            this.setId(i);
            this.setNombre(n);
            this.setPrecio(p);
            this.setCategoria(c);
        }

        public Producto(Integer i, String n) {
            this(i, n, 0.0);
            Random r = new Random();
            this.precio = (r.nextInt(3) + 1) * ((r.nextDouble() * 100));
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Double getPrecio() {
            return precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public DecimalFormat getF() {
        return f;
    }

    public void setF(DecimalFormat f) {
        this.f = f;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", precio=" + precio +
                ", f=" + f +
                '}';
    }
}

 /*   class ItemPedido {
        private int cantidad;
        ElementoMenu item;

        public ItemPedido(ElementoMenu item, int cantidad) {
            this.cantidad = cantidad;
            this.item = item;
        }

        public void incrementarCantidad(){
            this.cantidad+=1;
        }

        public int getCantidadItem(){
            return this.cantidad;
        }
    }*/


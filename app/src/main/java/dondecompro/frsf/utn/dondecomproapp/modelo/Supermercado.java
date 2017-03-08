package dondecompro.frsf.utn.dondecomproapp.modelo;

/**
 * Created by Martin on 12/02/2017.
 */

public class Supermercado {

    private String nombre, direccion, correo, id, lat, lng, telefono;

    public Supermercado(String nombre, String direccion, String correo, String id, String telefono, String lat, String lng) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.id = id;
        this.telefono = telefono;
        this.lat = lat;
        this.lng = lng;
    }
    public Supermercado(String nombre, String direccion, String lat, String lng) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.lat =  lat;
        this.lng = lng;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        if(direccion == null){
            return "";
        }
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTelefono() {
        if(telefono == null){
            return "";
        }
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLatitud() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongitud() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }


}

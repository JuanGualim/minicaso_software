package model;

/**
 * Clase Sucursal - Entidad del dominio
 * Representa una sucursal de la cadena de tiendas
 */
public class Sucursal {
    private String id;
    private String nombre;
    private String direccion;
    private String telefono;

    public Sucursal(String id, String nombre, String direccion, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return String.format("Sucursal[%s - %s | %s]", id, nombre, direccion);
    }
}
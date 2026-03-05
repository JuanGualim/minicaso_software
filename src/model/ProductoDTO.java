package model;

/**
 * ProductoDTO - Patrón Data Transfer Object
 * 
 * PROPÓSITO: Transferir datos entre capas sin exponer la entidad completa.
 * Solo contiene los datos necesarios para la vista, optimizando la transferencia.
 * 
 * PRINCIPIOS APLICADOS:
 * - SRP: Responsabilidad única de transportar datos
 * - ISP: Contiene solo los atributos necesarios, no toda la entidad
 * - Encapsulamiento: Protege la estructura interna del modelo
 */
public class ProductoDTO {
    private final String id;
    private final String nombre;
    private final String categoria;
    private final double precio;
    private final int stock;
    private final boolean necesitaReabastecimiento;
    private final String nombreSucursal;

    /**
     * Constructor para crear DTO desde un Producto
     */
    public ProductoDTO(String id, String nombre, String categoria, double precio, 
                      int stock, boolean necesitaReabastecimiento, String nombreSucursal) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.necesitaReabastecimiento = necesitaReabastecimiento;
        this.nombreSucursal = nombreSucursal;
    }

    // Solo getters (inmutable)
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public boolean necesitaReabastecimiento() {
        return necesitaReabastecimiento;
    }

    public String getNombreSucursal() {
        return nombreSucursal;
    }

    @Override
    public String toString() {
        String alerta = necesitaReabastecimiento ? " [¡STOCK BAJO!]" : "";
        return String.format("%s - %s | Stock: %d | $%.2f | %s%s",
                id, nombre, stock, precio, nombreSucursal, alerta);
    }
}
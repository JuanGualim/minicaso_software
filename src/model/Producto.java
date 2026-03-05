package model;

/**
 * Clase Producto - Entidad del dominio
 * Representa un producto en el inventario
 */
public class Producto {
    private String id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private int stockMinimo;
    private String sucursalId;

    public Producto(String id, String nombre, String categoria, double precio, 
                   int stock, int stockMinimo, String sucursalId) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
        this.sucursalId = sucursalId;
    }

    // Getters
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

    public int getStockMinimo() {
        return stockMinimo;
    }

    public String getSucursalId() {
        return sucursalId;
    }

    // Setters
    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Verifica si el producto necesita reabastecimiento
     */
    public boolean necesitaReabastecimiento() {
        return stock <= stockMinimo;
    }

    @Override
    public String toString() {
        return String.format("Producto[%s - %s | Stock: %d | Precio: $%.2f | Sucursal: %s]",
                id, nombre, stock, precio, sucursalId);
    }
}
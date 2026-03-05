package model;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * GestorInventario - Patrón Singleton
 * 
 * PROPÓSITO: Garantizar una única instancia que gestione todo el inventario.
 * Mantiene consistencia de datos y evita conflictos de acceso concurrente.
 * 
 * PRINCIPIOS APLICADOS:
 * - SRP: Responsabilidad única de gestionar el inventario
 * - OCP: Puede extenderse para nuevos tipos de productos sin modificación
 * - DIP: Retorna DTOs, no entidades directas (inversión de dependencia)
 */
public class GestorInventario {
    // Instancia única (Singleton)
    private static GestorInventario instance;
    
    // Almacenamiento interno
    private Map<String, Producto> productos;
    private Map<String, Sucursal> sucursales;

    /**
     * Constructor privado (patrón Singleton)
     * Previene instanciación externa
     */
    private GestorInventario() {
        productos = new HashMap<>();
        sucursales = new HashMap<>();
        inicializarDatos();
    }

    /**
     * Método para obtener la única instancia (Singleton)
     * Thread-safe con sincronización
     */
    public static synchronized GestorInventario getInstance() {
        if (instance == null) {
            instance = new GestorInventario();
        }
        return instance;
    }

    /**
     * Inicializa datos de ejemplo
     */
    private void inicializarDatos() {
        // Crear sucursales
        sucursales.put("SUC001", new Sucursal("SUC001", "Zona 10", "10 Calle 3-17, Zona 10", "2345-6789"));
        sucursales.put("SUC002", new Sucursal("SUC002", "Cayalá", "Blvd. Cayalá, Local 105", "2456-7890"));
        sucursales.put("SUC003", new Sucursal("SUC003", "Oakland Mall", "Oakland Mall, Nivel 2", "2567-8901"));

        // Crear productos
        productos.put("PROD001", new Producto("PROD001", "Laptop HP Pavilion", "Computadoras", 
                                              8500.00, 15, 5, "SUC001"));
        productos.put("PROD002", new Producto("PROD002", "iPhone 15 Pro", "Smartphones", 
                                              12000.00, 3, 5, "SUC001"));
        productos.put("PROD003", new Producto("PROD003", "Monitor Samsung 27\"", "Periféricos", 
                                              2500.00, 20, 10, "SUC002"));
        productos.put("PROD004", new Producto("PROD004", "Teclado Mecánico", "Periféricos", 
                                              450.00, 8, 5, "SUC002"));
        productos.put("PROD005", new Producto("PROD005", "Audífonos Sony WH-1000XM5", "Audio", 
                                              2800.00, 12, 8, "SUC003"));
    }

    /**
     * Busca un producto por ID y retorna su DTO
     */
    public ProductoDTO buscarProductoPorId(String id) {
        Producto producto = productos.get(id);
        if (producto == null) {
            return null;
        }
        return convertirADTO(producto);
    }

    /**
     * Busca productos por nombre (búsqueda parcial)
     */
    public List<ProductoDTO> buscarProductosPorNombre(String nombre) {
        List<ProductoDTO> resultados = new ArrayList<>();
        for (Producto producto : productos.values()) {
            if (producto.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                resultados.add(convertirADTO(producto));
            }
        }
        return resultados;
    }

    /**
     * Busca productos por categoría
     */
    public List<ProductoDTO> buscarProductosPorCategoria(String categoria) {
        List<ProductoDTO> resultados = new ArrayList<>();
        for (Producto producto : productos.values()) {
            if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                resultados.add(convertirADTO(producto));
            }
        }
        return resultados;
    }

    /**
     * Obtiene todos los productos
     */
    public List<ProductoDTO> obtenerTodosLosProductos() {
        List<ProductoDTO> resultados = new ArrayList<>();
        for (Producto producto : productos.values()) {
            resultados.add(convertirADTO(producto));
        }
        return resultados;
    }

    /**
     * Obtiene productos con stock bajo
     */
    public List<ProductoDTO> obtenerProductosStockBajo() {
        List<ProductoDTO> resultados = new ArrayList<>();
        for (Producto producto : productos.values()) {
            if (producto.necesitaReabastecimiento()) {
                resultados.add(convertirADTO(producto));
            }
        }
        return resultados;
    }

    /**
     * Actualiza el stock de un producto
     * Retorna true si la actualización genera alerta de stock bajo
     */
    public boolean actualizarStock(String id, int nuevoStock) {
        Producto producto = productos.get(id);
        if (producto == null) {
            return false;
        }
        
        producto.setStock(nuevoStock);
        
        // Retorna true si después de actualizar el stock está bajo
        return producto.necesitaReabastecimiento();
    }

    /**
     * Convierte un Producto a ProductoDTO
     * Aplica el patrón DTO para transferencia de datos
     */
    private ProductoDTO convertirADTO(Producto producto) {
        Sucursal sucursal = sucursales.get(producto.getSucursalId());
        String nombreSucursal = sucursal != null ? sucursal.getNombre() : "Desconocida";
        
        return new ProductoDTO(
            producto.getId(),
            producto.getNombre(),
            producto.getCategoria(),
            producto.getPrecio(),
            producto.getStock(),
            producto.necesitaReabastecimiento(),
            nombreSucursal
        );
    }

    /**
     * Obtiene información de una sucursal
     */
    public Sucursal obtenerSucursal(String id) {
        return sucursales.get(id);
    }
}
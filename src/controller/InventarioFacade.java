package controller;

import model.GestorInventario;
import model.ProductoDTO;
import java.util.List;

/**
 * InventarioFacade - Patrón Facade
 * 
 * PROPÓSITO: Proporciona una interfaz simplificada para operaciones complejas
 * del sistema de inventario. Coordina entre el Modelo y el sistema de notificaciones.
 * 
 * PRINCIPIOS APLICADOS:
 * - SRP: Responsabilidad única de coordinar operaciones
 * - DIP: Depende de abstracciones (GestorInventario, NotificadorStock)
 * - Low Coupling: Reduce dependencias entre Vista y Modelo
 * - High Cohesion: Agrupa operaciones relacionadas del inventario
 */
public class InventarioFacade {
    // Componentes del sistema
    private GestorInventario gestorInventario;
    private NotificadorStock notificador;

    /**
     * Constructor - inicializa componentes
     */
    public InventarioFacade() {
        // Usa el Singleton del GestorInventario
        this.gestorInventario = GestorInventario.getInstance();
        this.notificador = new NotificadorStock();
    }

    /**
     * Registra un observador en el sistema de notificaciones
     */
    public void registrarObservador(ObservadorInventario observador) {
        notificador.agregarObservador(observador);
    }

    /**
     * Elimina un observador del sistema de notificaciones
     */
    public void eliminarObservador(ObservadorInventario observador) {
        notificador.eliminarObservador(observador);
    }

    /**
     * Busca un producto por ID
     * Operación simplificada que oculta la complejidad del modelo
     */
    public ProductoDTO buscarProducto(String id) {
        return gestorInventario.buscarProductoPorId(id);
    }

    /**
     * Busca productos por nombre
     */
    public List<ProductoDTO> buscarPorNombre(String nombre) {
        return gestorInventario.buscarProductosPorNombre(nombre);
    }

    /**
     * Busca productos por categoría
     */
    public List<ProductoDTO> buscarPorCategoria(String categoria) {
        return gestorInventario.buscarProductosPorCategoria(categoria);
    }

    /**
     * Obtiene todos los productos
     */
    public List<ProductoDTO> obtenerTodosLosProductos() {
        return gestorInventario.obtenerTodosLosProductos();
    }

    /**
     * Obtiene productos con stock bajo
     */
    public List<ProductoDTO> obtenerProductosStockBajo() {
        return gestorInventario.obtenerProductosStockBajo();
    }

    /**
     * Actualiza el stock de un producto y notifica a los observadores
     * Operación compleja coordinada: actualiza modelo + notifica vistas
     */
    public void actualizarStock(String id, int nuevoStock) {
        // 1. Actualiza en el modelo
        boolean stockBajo = gestorInventario.actualizarStock(id, nuevoStock);
        
        // 2. Obtiene el producto actualizado como DTO
        ProductoDTO productoActualizado = gestorInventario.buscarProductoPorId(id);
        
        if (productoActualizado != null) {
            // 3. Notifica a todas las vistas sobre la actualización
            notificador.notificarActualizacion(productoActualizado);
            
            // 4. Si el stock está bajo, envía alerta adicional
            if (stockBajo) {
                notificador.notificarStockBajo(productoActualizado);
            }
        }
    }

    /**
     * Genera reporte de stock bajo
     * Operación de alto nivel que coordina múltiples operaciones
     */
    public String generarReporteStockBajo() {
        List<ProductoDTO> productosStockBajo = gestorInventario.obtenerProductosStockBajo();
        
        if (productosStockBajo.isEmpty()) {
            return "No hay productos con stock bajo.";
        }
        
        StringBuilder reporte = new StringBuilder();
        reporte.append("═══════════════════════════════════════════════════════════\n");
        reporte.append("          REPORTE DE PRODUCTOS CON STOCK BAJO\n");
        reporte.append("═══════════════════════════════════════════════════════════\n\n");
        
        for (ProductoDTO producto : productosStockBajo) {
            reporte.append(String.format("⚠️  %s\n", producto.toString()));
            reporte.append(String.format("    Categoría: %s\n", producto.getCategoria()));
            reporte.append("-----------------------------------------------------------\n");
        }
        
        reporte.append(String.format("\nTotal de productos con stock bajo: %d\n", productosStockBajo.size()));
        reporte.append("═══════════════════════════════════════════════════════════\n");
        
        return reporte.toString();
    }

    /**
     * Obtiene el número de observadores activos
     */
    public int getNumeroObservadores() {
        return notificador.getNumeroObservadores();
    }
}
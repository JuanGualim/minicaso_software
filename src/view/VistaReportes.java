package view;

import controller.ObservadorInventario;
import model.ProductoDTO;
import java.util.List;

/**
 * VistaReportes - Vista del patrón MVC + Observer
 * 
 * PROPÓSITO: Genera y muestra reportes del inventario. Recibe notificaciones
 * de alertas de stock bajo para actualizar reportes en tiempo real.
 * 
 * PRINCIPIOS APLICADOS:
 * - SRP: Responsabilidad única de generar reportes
 * - DIP: Depende de abstracciones
 * - High Cohesion: Agrupa funcionalidad relacionada con reportes
 */
public class VistaReportes implements ObservadorInventario {
    private String nombre;
    private int alertasRecibidas;

    public VistaReportes(String nombre) {
        this.nombre = nombre;
        this.alertasRecibidas = 0;
    }

    /**
     * Muestra reporte general del inventario
     */
    public void mostrarReporteGeneral(List<ProductoDTO> productos) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║              VISTA REPORTES - " + nombre + "              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        
        if (productos.isEmpty()) {
            System.out.println("No hay productos para generar reporte.");
            return;
        }
        
        // Calcular estadísticas
        int totalProductos = productos.size();
        int productosStockBajo = 0;
        double valorTotalInventario = 0;
        
        for (ProductoDTO producto : productos) {
            if (producto.necesitaReabastecimiento()) {
                productosStockBajo++;
            }
            valorTotalInventario += producto.getPrecio() * producto.getStock();
        }
        
        System.out.println("\n📊 REPORTE GENERAL DEL INVENTARIO");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("Total de productos:           " + totalProductos);
        System.out.println("Productos con stock bajo:     " + productosStockBajo);
        System.out.println("Productos con stock adecuado: " + (totalProductos - productosStockBajo));
        System.out.println("Valor total del inventario:   $" + String.format("%.2f", valorTotalInventario));
        System.out.println("Alertas recibidas (sesión):   " + alertasRecibidas);
        System.out.println("═══════════════════════════════════════════════════════════════");
    }

    /**
     * Muestra reporte de productos con stock bajo
     */
    public void mostrarReporteStockBajo(List<ProductoDTO> productosStockBajo) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║           REPORTE DE PRODUCTOS CON STOCK BAJO                 ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        
        if (productosStockBajo.isEmpty()) {
            System.out.println("\n✓ No hay productos con stock bajo. Todo el inventario está bien abastecido.");
            return;
        }
        
        System.out.println("\n⚠️  PRODUCTOS QUE REQUIEREN REABASTECIMIENTO:");
        System.out.println("─────────────────────────────────────────────────────────────────");
        
        for (ProductoDTO producto : productosStockBajo) {
            System.out.println("  ⚠️  " + producto.toString());
        }
        
        System.out.println("─────────────────────────────────────────────────────────────────");
        System.out.println("Total de productos con stock bajo: " + productosStockBajo.size());
        System.out.println("═══════════════════════════════════════════════════════════════");
    }

    /**
     * Implementación del patrón Observer
     * Se ejecuta cuando un producto es actualizado
     */
    @Override
    public void actualizarProducto(ProductoDTO producto) {
        System.out.println("\n[" + nombre + "] 📊 Reporte actualizado con:");
        System.out.println("    " + producto.toString());
    }

    /**
     * Implementación del patrón Observer
     * Se ejecuta cuando se detecta stock bajo
     */
    @Override
    public void alertaStockBajo(ProductoDTO producto) {
        alertasRecibidas++;
        System.out.println("\n[" + nombre + "] ⚠️  ¡ALERTA CRÍTICA! Stock bajo detectado:");
        System.out.println("    " + producto.toString());
        System.out.println("    → Total de alertas en esta sesión: " + alertasRecibidas);
    }

    public String getNombre() {
        return nombre;
    }

    public int getAlertasRecibidas() {
        return alertasRecibidas;
    }
}
package view;

import controller.ObservadorInventario;
import model.ProductoDTO;
import java.util.List;

/**
 * VistaInventario - Vista del patrón MVC + Observer
 * 
 * PROPÓSITO: Muestra información general del inventario y recibe notificaciones
 * en tiempo real cuando hay cambios.
 * 
 * PRINCIPIOS APLICADOS:
 * - SRP: Responsabilidad única de mostrar inventario
 * - DIP: Depende de la interfaz ObservadorInventario, no de implementaciones
 * - Separation of Concerns: Separada de la lógica de negocio
 */
public class VistaInventario implements ObservadorInventario {
    private String nombre;

    public VistaInventario(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Muestra todos los productos
     */
    public void mostrarInventario(List<ProductoDTO> productos) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║              VISTA INVENTARIO - " + nombre + "              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        
        if (productos.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
            return;
        }
        
        System.out.println("\nPRODUCTOS DISPONIBLES:");
        System.out.println("─────────────────────────────────────────────────────────────────");
        for (ProductoDTO producto : productos) {
            System.out.println("📦 " + producto.toString());
        }
        System.out.println("─────────────────────────────────────────────────────────────────");
        System.out.println("Total de productos: " + productos.size());
    }

    /**
     * Implementación del patrón Observer
     * Se ejecuta cuando un producto es actualizado
     */
    @Override
    public void actualizarProducto(ProductoDTO producto) {
        System.out.println("\n[" + nombre + "] 🔄 Producto actualizado:");
        System.out.println("    " + producto.toString());
    }

    /**
     * Implementación del patrón Observer
     * Se ejecuta cuando se detecta stock bajo
     */
    @Override
    public void alertaStockBajo(ProductoDTO producto) {
        System.out.println("\n[" + nombre + "] ⚠️  ¡ALERTA DE STOCK BAJO!");
        System.out.println("    " + producto.toString());
        System.out.println("    → Se recomienda reabastecer este producto");
    }

    public String getNombre() {
        return nombre;
    }
}
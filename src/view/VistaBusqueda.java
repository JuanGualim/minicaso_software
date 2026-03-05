package view;

import controller.ObservadorInventario;
import model.ProductoDTO;
import java.util.List;

/**
 * VistaBusqueda - Vista del patrón MVC + Observer
 * 
 * PROPÓSITO: Permite búsqueda de productos y recibe notificaciones
 * cuando los productos buscados son actualizados.
 * 
 * PRINCIPIOS APLICADOS:
 * - SRP: Responsabilidad única de búsqueda y visualización
 * - DIP: Depende de abstracciones (ObservadorInventario)
 * - ISP: Implementa solo los métodos necesarios
 */
public class VistaBusqueda implements ObservadorInventario {
    private String nombre;

    public VistaBusqueda(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Muestra resultados de búsqueda
     */
    public void mostrarResultados(List<ProductoDTO> resultados, String criterio) {
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║              VISTA BÚSQUEDA - " + nombre + "              ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println("Criterio de búsqueda: " + criterio);
        System.out.println("─────────────────────────────────────────────────────────────────");
        
        if (resultados.isEmpty()) {
            System.out.println("❌ No se encontraron productos que coincidan con la búsqueda.");
            return;
        }
        
        System.out.println("\n✓ Resultados encontrados:");
        for (ProductoDTO producto : resultados) {
            System.out.println("  🔍 " + producto.toString());
        }
        System.out.println("─────────────────────────────────────────────────────────────────");
        System.out.println("Total de resultados: " + resultados.size());
    }

    /**
     * Muestra información detallada de un producto
     */
    public void mostrarDetalleProducto(ProductoDTO producto) {
        if (producto == null) {
            System.out.println("\n❌ Producto no encontrado.");
            return;
        }
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║                    DETALLE DE PRODUCTO                        ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println("ID:           " + producto.getId());
        System.out.println("Nombre:       " + producto.getNombre());
        System.out.println("Categoría:    " + producto.getCategoria());
        System.out.println("Precio:       $" + String.format("%.2f", producto.getPrecio()));
        System.out.println("Stock:        " + producto.getStock() + " unidades");
        System.out.println("Sucursal:     " + producto.getNombreSucursal());
        
        if (producto.necesitaReabastecimiento()) {
            System.out.println("Estado:       ⚠️  REQUIERE REABASTECIMIENTO");
        } else {
            System.out.println("Estado:       ✓ Stock adecuado");
        }
        System.out.println("═══════════════════════════════════════════════════════════════");
    }

    /**
     * Implementación del patrón Observer
     * Se ejecuta cuando un producto es actualizado
     */
    @Override
    public void actualizarProducto(ProductoDTO producto) {
        System.out.println("\n[" + nombre + "] 🔄 Actualización recibida:");
        System.out.println("    " + producto.toString());
    }

    /**
     * Implementación del patrón Observer
     * Se ejecuta cuando se detecta stock bajo
     */
    @Override
    public void alertaStockBajo(ProductoDTO producto) {
        System.out.println("\n[" + nombre + "] ⚠️  STOCK BAJO detectado:");
        System.out.println("    " + producto.toString());
    }

    public String getNombre() {
        return nombre;
    }
}
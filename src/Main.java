import controller.InventarioFacade;
import view.VistaInventario;
import view.VistaBusqueda;
import view.VistaReportes;
import model.ProductoDTO;
import java.util.List;

/**
 * Main - Clase principal del sistema
 * 
 * DEMOSTRACIÓN COMPLETA DE PATRONES:
 * - MVC: Arquitectura en 3 capas
 * - Singleton: GestorInventario (única instancia)
 * - Facade: InventarioFacade (simplificación de operaciones)
 * - Observer: NotificadorStock + Vistas (notificaciones en tiempo real)
 * - DTO: ProductoDTO (transferencia de datos)
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE GESTIÓN DE INVENTARIO EN TIEMPO REAL              ║");
        System.out.println("║     Demostración de Patrones de Diseño                            ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
        
        // ═══════════════════════════════════════════════════════════════════
        // PASO 1: Inicialización del Sistema (MVC + Facade)
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n━━━ PASO 1: Inicializando Sistema ━━━");
        
        // Facade: Punto de entrada único al sistema
        InventarioFacade facade = new InventarioFacade();
        
        // Vistas: Capa de presentación (MVC)
        VistaInventario vistaInventario = new VistaInventario("Panel Principal");
        VistaBusqueda vistaBusqueda = new VistaBusqueda("Búsqueda Rápida");
        VistaReportes vistaReportes = new VistaReportes("Centro de Reportes");
        
        System.out.println("✓ Sistema inicializado correctamente");
        System.out.println("✓ Patrón MVC aplicado: Modelo-Vista-Controlador separados");
        System.out.println("✓ Patrón Facade: Interfaz simplificada al sistema");
        System.out.println("✓ Patrón Singleton: GestorInventario con instancia única");
        
        // ═══════════════════════════════════════════════════════════════════
        // PASO 2: Registro de Observadores (Patrón Observer)
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n━━━ PASO 2: Registrando Observadores (Observer Pattern) ━━━");
        
        facade.registrarObservador(vistaInventario);
        facade.registrarObservador(vistaBusqueda);
        facade.registrarObservador(vistaReportes);
        
        System.out.println("✓ Patrón Observer aplicado: " + facade.getNumeroObservadores() + " vistas suscritas");
        System.out.println("  → Las vistas recibirán notificaciones automáticas de cambios");
        
        // ═══════════════════════════════════════════════════════════════════
        // PASO 3: Consulta de Inventario (DTO Pattern)
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n━━━ PASO 3: Consultando Inventario (DTO Pattern) ━━━");
        
        List<ProductoDTO> todosProductos = facade.obtenerTodosLosProductos();
        vistaInventario.mostrarInventario(todosProductos);
        
        System.out.println("\n✓ Patrón DTO aplicado: Datos transferidos sin exponer entidades");
        System.out.println("  → ProductoDTO transporta solo datos necesarios para la vista");
        
        // ═══════════════════════════════════════════════════════════════════
        // PASO 4: Búsqueda de Productos
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n━━━ PASO 4: Búsqueda de Productos ━━━");
        
        // Búsqueda por nombre
        List<ProductoDTO> resultados = facade.buscarPorNombre("iPhone");
        vistaBusqueda.mostrarResultados(resultados, "Nombre: 'iPhone'");
        
        // Búsqueda por ID con detalle
        ProductoDTO producto = facade.buscarProducto("PROD002");
        vistaBusqueda.mostrarDetalleProducto(producto);
        
        // ═══════════════════════════════════════════════════════════════════
        // PASO 5: Reporte de Stock Bajo
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n━━━ PASO 5: Generando Reporte de Stock Bajo ━━━");
        
        List<ProductoDTO> productosStockBajo = facade.obtenerProductosStockBajo();
        vistaReportes.mostrarReporteStockBajo(productosStockBajo);
        
        // ═══════════════════════════════════════════════════════════════════
        // PASO 6: Actualización de Stock (Demostración Observer)
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n━━━ PASO 6: Actualización de Stock - Demostración Observer ━━━");
        System.out.println("Actualizando stock del iPhone 15 Pro de 3 a 15 unidades...");
        System.out.println("Observa cómo TODAS las vistas reciben la notificación automáticamente:");
        
        // Esta operación notificará a TODAS las vistas registradas
        facade.actualizarStock("PROD002", 15);
        
        esperarTecla();
        
        // ═══════════════════════════════════════════════════════════════════
        // PASO 7: Actualización que genera Alerta de Stock Bajo
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n━━━ PASO 7: Actualización con Alerta de Stock Bajo ━━━");
        System.out.println("Actualizando Monitor Samsung de 20 a 2 unidades (stock mínimo: 10)...");
        System.out.println("¡Esto generará una ALERTA que se propagará a todas las vistas!");
        
        // Esta actualización generará alerta de stock bajo
        facade.actualizarStock("PROD003", 2);
        
        esperarTecla();
        
        // ═══════════════════════════════════════════════════════════════════
        // PASO 8: Verificación de Singleton
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n━━━ PASO 8: Verificando Patrón Singleton ━━━");
        System.out.println("Consultando el producto actualizado desde diferentes vistas...");
        
        ProductoDTO productoActualizado = facade.buscarProducto("PROD003");
        vistaBusqueda.mostrarDetalleProducto(productoActualizado);
        
        System.out.println("\n✓ Patrón Singleton verificado:");
        System.out.println("  → La misma instancia de GestorInventario es usada por todas las operaciones");
        System.out.println("  → Todos los cambios se reflejan consistentemente en todo el sistema");
        
        // ═══════════════════════════════════════════════════════════════════
        // PASO 9: Reporte Final
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n━━━ PASO 9: Reporte General Final ━━━");
        
        List<ProductoDTO> inventarioFinal = facade.obtenerTodosLosProductos();
        vistaReportes.mostrarReporteGeneral(inventarioFinal);
        
        // ═══════════════════════════════════════════════════════════════════
        // RESUMEN DE PATRONES APLICADOS
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║              RESUMEN DE PATRONES IMPLEMENTADOS                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
        System.out.println("\n1. MVC (Arquitectónico):");
        System.out.println("   ✓ Modelo: GestorInventario, Producto, Sucursal");
        System.out.println("   ✓ Vista: VistaInventario, VistaBusqueda, VistaReportes");
        System.out.println("   ✓ Controlador: InventarioFacade, NotificadorStock");
        
        System.out.println("\n2. Singleton:");
        System.out.println("   ✓ GestorInventario: Instancia única que garantiza consistencia");
        
        System.out.println("\n3. Facade:");
        System.out.println("   ✓ InventarioFacade: Simplifica operaciones complejas");
        System.out.println("   ✓ Coordina Modelo + Notificaciones de forma transparente");
        
        System.out.println("\n4. Observer:");
        System.out.println("   ✓ NotificadorStock: Observable que notifica cambios");
        System.out.println("   ✓ Vistas: Observers que reciben actualizaciones automáticas");
        System.out.println("   ✓ " + facade.getNumeroObservadores() + " observadores activos recibieron notificaciones");
        
        System.out.println("\n5. DTO (Data Transfer Object):");
        System.out.println("   ✓ ProductoDTO: Transfiere datos sin exponer entidades");
        System.out.println("   ✓ Optimiza comunicación entre capas");
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║          PRINCIPIOS SOLID DEMOSTRADOS                             ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝");
        System.out.println("\n✓ SRP: Cada clase tiene una única responsabilidad");
        System.out.println("✓ OCP: Sistema abierto a extensión, cerrado a modificación");
        System.out.println("✓ LSP: Observers son intercambiables sin afectar el sistema");
        System.out.println("✓ ISP: Interfaces mínimas y específicas");
        System.out.println("✓ DIP: Dependencia de abstracciones, no implementaciones");
        
        System.out.println("\n╔═══════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    FIN DE LA DEMOSTRACIÓN                         ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════════╝\n");
    }
    
    /**
     * Método auxiliar para pausar la ejecución
     */
    private static void esperarTecla() {
        System.out.println("\n[Presiona ENTER para continuar...]");
        try {
            System.in.read();
        } catch (Exception e) {
            // Ignorar
        }
    }
}
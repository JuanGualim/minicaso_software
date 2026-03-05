package controller;


import model.ProductoDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * NotificadorStock - Sujeto Observable del patrón Observer
 * 
 * PROPÓSITO: Mantiene lista de observadores y los notifica cuando hay cambios
 * en el inventario. Implementa el mecanismo de suscripción/notificación.
 * 
 * PRINCIPIOS APLICADOS:
 * - SRP: Responsabilidad única de notificar cambios
 * - OCP: Abierto a nuevos observadores sin modificar código existente
 * - LSP: Cualquier implementación de ObservadorInventario puede suscribirse
 * - Low Coupling: Desacopla la lógica de negocio de la presentación
 */
public class NotificadorStock {
    // Lista de observadores suscritos
    private List<ObservadorInventario> observadores;

    public NotificadorStock() {
        this.observadores = new ArrayList<>();
    }

    /**
     * Registra un nuevo observador
     */
    public void agregarObservador(ObservadorInventario observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
            System.out.println("✓ Observador agregado. Total: " + observadores.size());
        }
    }

    /**
     * Elimina un observador
     */
    public void eliminarObservador(ObservadorInventario observador) {
        observadores.remove(observador);
        System.out.println("✓ Observador eliminado. Total: " + observadores.size());
    }

    /**
     * Notifica a todos los observadores sobre actualización de producto
     */
    public void notificarActualizacion(ProductoDTO producto) {
        System.out.println("\n🔔 Notificando actualización a " + observadores.size() + " observadores...");
        for (ObservadorInventario observador : observadores) {
            observador.actualizarProducto(producto);
        }
    }

    /**
     * Notifica a todos los observadores sobre stock bajo
     */
    public void notificarStockBajo(ProductoDTO producto) {
        System.out.println("\n⚠️  Notificando ALERTA de stock bajo a " + observadores.size() + " observadores...");
        for (ObservadorInventario observador : observadores) {
            observador.alertaStockBajo(producto);
        }
    }

    /**
     * Obtiene el número de observadores registrados
     */
    public int getNumeroObservadores() {
        return observadores.size();
    }
}
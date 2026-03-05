package controller;

import model.ProductoDTO;

/**
 * ObservadorInventario - Interfaz del patrón Observer
 * 
 * PROPÓSITO: Define el contrato para objetos que desean ser notificados
 * cuando cambia el estado del inventario.
 * 
 * PRINCIPIOS APLICADOS:
 * - DIP: Las vistas dependen de esta abstracción, no de implementaciones concretas
 * - ISP: Interfaz mínima con solo los métodos necesarios
 * - OCP: Permite agregar nuevos observers sin modificar el sistema existente
 */
public interface ObservadorInventario {
    /**
     * Método invocado cuando se actualiza un producto
     */
    void actualizarProducto(ProductoDTO producto);
    
    /**
     * Método invocado cuando se detecta stock bajo
     */
    void alertaStockBajo(ProductoDTO producto);
}
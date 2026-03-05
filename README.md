# 📦 Sistema de Gestión de Inventario en Tiempo Real

## 🎯 Descripción del Proyecto

Sistema de gestión de inventario para una cadena de tiendas de electrónica que implementa **5 patrones de diseño** integrados en una **arquitectura MVC**. El sistema permite gestionar productos en múltiples sucursales con notificaciones en tiempo real cuando se actualiza el inventario.

### Problema que Resuelve

Una cadena de tiendas necesita:
- ✅ Gestionar inventario de múltiples sucursales
- ✅ Consultar disponibilidad de productos en tiempo real
- ✅ Recibir alertas automáticas cuando el stock está bajo
- ✅ Sincronizar información entre diferentes interfaces simultáneamente
- ✅ Actualizar stock de forma consistente desde cualquier punto del sistema

### Solución Implementada

Sistema que integra patrones de diseño para lograr:
- **Consistencia de datos** mediante Singleton
- **Simplicidad de uso** mediante Facade
- **Notificaciones en tiempo real** mediante Observer
- **Transferencia eficiente** mediante DTO
- **Separación de responsabilidades** mediante MVC

---

## 🏗️ Arquitectura del Sistema

### Diagrama General

```
┌─────────────────────────────────────────────────────────────┐
│                    CAPA VISTA (View)                         │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │VistaInv. (O) │  │VistaBusc. (O)│  │VistaRep. (O) │      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
└────────────────────────┬────────────────────────────────────┘
                         │ Interactúa con
┌────────────────────────┴────────────────────────────────────┐
│              CAPA CONTROLADOR (Controller)                   │
│  ┌──────────────────────────────────────────────────┐       │
│  │        InventarioFacade (Facade Pattern)         │       │
│  │  ┌────────────┐    ┌──────────────────────┐     │       │
│  │  │ Coordina   │    │ NotificadorStock (O) │     │       │
│  │  └────────────┘    └──────────────────────┘     │       │
│  └──────────────────────────────────────────────────┘       │
└────────────────────────┬────────────────────────────────────┘
                         │ Accede a
┌────────────────────────┴────────────────────────────────────┐
│                    CAPA MODELO (Model)                       │
│  ┌──────────────────────────────────────────────────┐       │
│  │     GestorInventario (Singleton Pattern)         │       │
│  │  - getInstance()                                 │       │
│  │  - buscarProducto()                              │       │
│  │  - actualizarStock()                             │       │
│  └──────────────────────────────────────────────────┘       │
│                                                              │
│  ┌───────────┐  ┌──────────┐  ┌─────────────────────┐      │
│  │ Producto  │  │ Sucursal │  │ ProductoDTO (DTO)   │      │
│  └───────────┘  └──────────┘  └─────────────────────┘      │
└──────────────────────────────────────────────────────────────┘

Leyenda: (O) = Observer, (S) = Singleton, (F) = Facade
```

### Estructura de Archivos

```
SistemaInventario/
├── src/
│   ├── modelo/                    # CAPA MODELO (MVC)
│   │   ├── Producto.java                    # Entidad: Producto
│   │   ├── Sucursal.java                    # Entidad: Sucursal
│   │   ├── ProductoDTO.java                 # Patrón DTO
│   │   └── GestorInventario.java            # Patrón Singleton
│   │
│   ├── controlador/               # CAPA CONTROLADOR (MVC)
│   │   ├── ObservadorInventario.java        # Patrón Observer (Interfaz)
│   │   ├── NotificadorStock.java            # Patrón Observer (Observable)
│   │   └── InventarioFacade.java            # Patrón Facade
│   │
│   ├── vista/                     # CAPA VISTA (MVC)
│   │   ├── VistaInventario.java             # Observer - Panel general
│   │   ├── VistaBusqueda.java               # Observer - Búsqueda
│   │   └── VistaReportes.java               # Observer - Reportes
│   │
│   └── Main.java                  # Demostración completa
│
├── bin/                           # Archivos compilados (.class)
├── compile.sh / compile.bat       # Scripts de compilación
├── run.sh / run.bat              # Scripts de ejecución
├── README.md                     # Este archivo
└── INSTRUCCIONES.md              # Guía de uso detallada
```

---

## 🎨 Patrones de Diseño Implementados

### 1. 🏛️ MVC (Model-View-Controller) - Patrón Arquitectónico

**Propósito:** Separar responsabilidades en tres capas independientes.

**Implementación:**
- **Modelo:** `GestorInventario`, `Producto`, `Sucursal`, `ProductoDTO`
  - Gestiona la lógica de negocio y los datos
  - No tiene conocimiento de las vistas
  
- **Vista:** `VistaInventario`, `VistaBusqueda`, `VistaReportes`
  - Presenta información al usuario
  - Recibe notificaciones del controlador
  
- **Controlador:** `InventarioFacade`, `NotificadorStock`
  - Coordina entre modelo y vistas
  - Maneja el flujo de información

**Beneficio:** Separación de responsabilidades, facilita mantenimiento y testing.

---

### 2. 🔒 Singleton

**Propósito:** Garantizar una única instancia del gestor de inventario en todo el sistema.

**Implementación:** 
```java
public class GestorInventario {
    private static GestorInventario instance;
    
    private GestorInventario() { }  // Constructor privado
    
    public static synchronized GestorInventario getInstance() {
        if (instance == null) {
            instance = new GestorInventario();
        }
        return instance;
    }
}
```

**Ubicación:** `src/modelo/GestorInventario.java`

**Beneficios:**
- ✅ Garantiza consistencia de datos (una única fuente de verdad)
- ✅ Evita conflictos de acceso concurrente
- ✅ Control centralizado del inventario

**Aplicación en el sistema:** Todas las operaciones de inventario (consulta, actualización, búsqueda) acceden a la misma instancia, garantizando que los cambios se reflejen inmediatamente en todo el sistema.

---

### 3. 🎭 Facade

**Propósito:** Simplificar la interfaz de un subsistema complejo proporcionando un punto de acceso unificado.

**Implementación:**
```java
public class InventarioFacade {
    private GestorInventario gestorInventario;
    private NotificadorStock notificador;
    
    public void actualizarStock(String id, int nuevoStock) {
        // Coordina: Modelo + Notificaciones
        gestorInventario.actualizarStock(id, nuevoStock);
        notificador.notificarActualizacion(...);
    }
}
```

**Ubicación:** `src/controlador/InventarioFacade.java`

**Beneficios:**
- ✅ Oculta la complejidad del sistema
- ✅ Reduce el acoplamiento entre vistas y modelo
- ✅ Centraliza operaciones comunes

**Aplicación en el sistema:** Las vistas no necesitan conocer los detalles de cómo funciona el modelo o el sistema de notificaciones. `InventarioFacade` coordina todo automáticamente.

---

### 4. 👀 Observer

**Propósito:** Permitir que múltiples objetos (Observers) sean notificados automáticamente cuando cambia el estado de otro objeto (Observable).

**Implementación:**

**Interfaz Observer:**
```java
public interface ObservadorInventario {
    void actualizarProducto(ProductoDTO producto);
    void alertaStockBajo(ProductoDTO producto);
}
```

**Observable:**
```java
public class NotificadorStock {
    private List<ObservadorInventario> observadores;
    
    public void notificarActualizacion(ProductoDTO producto) {
        for (ObservadorInventario obs : observadores) {
            obs.actualizarProducto(producto);
        }
    }
}
```

**Observers (Vistas):**
```java
public class VistaInventario implements ObservadorInventario {
    @Override
    public void actualizarProducto(ProductoDTO producto) {
        // Se ejecuta automáticamente cuando hay cambios
        System.out.println("Producto actualizado: " + producto);
    }
}
```

**Ubicación:** 
- Observable: `src/controller/NotificadorStock.java`
- Interfaz: `src/controller/ObservadorInventario.java`
- Observers: `src/view/*.java`

**Beneficios:**
- ✅ Desacoplamiento entre sujeto y observadores
- ✅ Notificaciones automáticas en tiempo real
- ✅ Sincronización entre múltiples vistas

**Aplicación en el sistema:** Cuando un gerente actualiza el stock de un producto, TODAS las vistas (Panel Principal, Búsqueda, Reportes) reciben la notificación automáticamente y se actualizan sin necesidad de recargar.

---

### 5. 📦 DTO (Data Transfer Object)

**Propósito:** Transferir datos entre capas del sistema sin exponer la estructura interna de las entidades.

**Implementación:**
```java
public class ProductoDTO {
    private final String id;
    private final String nombre;
    private final int stock;
    private final boolean necesitaReabastecimiento;
    // Solo getters (inmutable)
}
```

**Ubicación:** `src/model/ProductoDTO.java`

**Beneficios:**
- ✅ Protege la estructura interna del modelo
- ✅ Optimiza la transferencia de datos (solo lo necesario)
- ✅ Reduce acoplamiento entre capas
- ✅ Inmutabilidad garantiza consistencia

**Aplicación en el sistema:** Las vistas nunca acceden directamente a objetos `Producto`. En su lugar, reciben `ProductoDTO` con solo la información que necesitan mostrar.

---

## ⚖️ Principios SOLID Aplicados

### S - Single Responsibility Principle (SRP)
**"Una clase debe tener una única razón para cambiar"**

- ✅ `GestorInventario`: Solo gestiona inventario
- ✅ `InventarioFacade`: Solo coordina operaciones
- ✅ `NotificadorStock`: Solo maneja notificaciones
- ✅ `ProductoDTO`: Solo transporta datos
- ✅ Cada vista: Solo presenta su información específica

### O - Open/Closed Principle (OCP)
**"Abierto para extensión, cerrado para modificación"**

- ✅ Puedes agregar nuevas vistas (Observers) sin modificar `NotificadorStock`
- ✅ Puedes extender tipos de productos sin cambiar `GestorInventario`
- ✅ Puedes agregar nuevas operaciones en `Facade` sin afectar vistas

### L - Liskov Substitution Principle (LSP)
**"Los objetos de una superclase deben poder reemplazarse con objetos de sus subclases"**

- ✅ Cualquier implementación de `ObservadorInventario` puede sustituirse sin romper el sistema
- ✅ Todas las vistas son intercambiables

### I - Interface Segregation Principle (ISP)
**"Los clientes no deben depender de interfaces que no usan"**

- ✅ `ObservadorInventario` tiene solo dos métodos necesarios
- ✅ Las vistas no implementan métodos innecesarios
- ✅ `ProductoDTO` contiene solo atributos relevantes para las vistas

### D - Dependency Inversion Principle (DIP)
**"Depender de abstracciones, no de implementaciones concretas"**

- ✅ Las vistas dependen de `ObservadorInventario` (abstracción), no de implementaciones
- ✅ El controlador depende de interfaces del modelo
- ✅ `Facade` actúa como intermediario que invierte dependencias

---

## 🔄 Flujo del Sistema

### Escenario 1: Consulta de Producto

```
Usuario → Vista → Facade.buscarProducto()
                     ↓
         GestorInventario.getInstance().buscarProducto()
                     ↓
         Retorna ProductoDTO ← Vista muestra información
```

### Escenario 2: Actualización de Stock (Patrón Observer en Acción)

```
Gerente actualiza stock en VistaInventario
         ↓
Facade.actualizarStock(id, nuevoStock)
         ↓
    [1] GestorInventario actualiza datos (Singleton)
         ↓
    [2] NotificadorStock.notificarActualizacion() (Observable)
         ↓
    [3] Notifica a TODOS los Observers registrados:
         ├─→ VistaInventario.actualizarProducto()
         ├─→ VistaBusqueda.actualizarProducto()
         └─→ VistaReportes.actualizarProducto()
         ↓
    [4] TODAS las vistas se actualizan automáticamente
```

### Escenario 3: Alerta de Stock Bajo

```
Stock actualizado < Stock mínimo
         ↓
GestorInventario detecta condición
         ↓
NotificadorStock.notificarStockBajo()
         ↓
TODOS los Observers reciben alerta:
    ├─→ VistaInventario muestra alerta
    ├─→ VistaBusqueda muestra alerta
    └─→ VistaReportes incrementa contador de alertas
```

---

## 🚀 Compilación y Ejecución

### Requisitos
- Java JDK 11 o superior
- Windows, Linux o macOS

### Windows (PowerShell - RECOMENDADO)

```powershell
# Configurar UTF-8 para ver emojis correctamente
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
chcp 65001

# Compilar
.\compile.bat

# Ejecutar
.\run.bat
```

### Linux/Mac

```bash
# Dar permisos
chmod +x compile.sh run.sh

# Compilar
./compile.sh

# Ejecutar
./run.sh
```

### Compilación Manual

```bash
# Crear directorio
mkdir -p bin

# Compilar
javac -d bin src/modelo/*.java src/controlador/*.java src/vista/*.java src/Main.java

# Ejecutar
java -cp bin Main
```

**📘 Para más detalles, consulta `INSTRUCCIONES.md`**

---

## 📊 Demostración del Sistema

El programa ejecuta 9 pasos que demuestran todos los patrones:

1. **Inicialización** - MVC, Facade, Singleton
2. **Registro de Observadores** - Observer
3. **Consulta de Inventario** - DTO
4. **Búsqueda de Productos** - Facade en acción
5. **Reporte de Stock Bajo** - Generación de reportes
6. **Actualización con Notificación** ⭐ - Observer en tiempo real
7. **Alerta de Stock Bajo** ⭐ - Propagación de alertas
8. **Verificación de Singleton** - Consistencia de datos
9. **Reporte Final** - Resumen completo

### Salida Esperada (Fragmento)

```
===================================================================
     SISTEMA DE GESTIÓN DE INVENTARIO EN TIEMPO REAL
===================================================================

--- PASO 6: Actualización de Stock - Demostración Observer ---
Actualizando stock del iPhone 15 Pro de 3 a 15 unidades...

🔔 Notificando actualización a 3 observadores...

[Panel Principal] 🔄 Producto actualizado:
    PROD002 - iPhone 15 Pro | Stock: 15 | $12000.00 | Zona 10

[Búsqueda Rápida] 🔄 Actualización recibida:
    PROD002 - iPhone 15 Pro | Stock: 15 | $12000.00 | Zona 10

[Centro de Reportes] 📊 Reporte actualizado con:
    PROD002 - iPhone 15 Pro | Stock: 15 | $12000.00 | Zona 10
```

---

## 📚 Documentación Adicional

- **INSTRUCCIONES.md** - Guía completa de uso paso a paso
- Comentarios JavaDoc en cada clase
- Documentación inline explicando cada patrón
- Diagramas en comentarios de código

---

## 🏆 Características Destacadas

✅ **Código funcional al 100%** - Probado y listo para ejecutar  
✅ **Documentación exhaustiva** - Cada clase con comentarios explicativos  
✅ **Demostración interactiva** - Muestra todos los patrones en acción  
✅ **Principios SOLID** - Aplicados y documentados  
✅ **Estructura profesional** - Siguiendo mejores prácticas de Java  
✅ **Integración coherente** - Todos los patrones trabajan juntos  
✅ **Notificaciones en tiempo real** - Observer en acción  
✅ **Código limpio** - Fácil de entender y mantener  


---

**¿Preguntas?** Consulta `INSTRUCCIONES.md` para guía detallada de uso.
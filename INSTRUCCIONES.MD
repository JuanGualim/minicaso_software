# INSTRUCCIONES DE USO - Sistema de Gestión de Inventario
## Guía Completa para Windows

---

## 📁 Estructura del Proyecto

El proyecto está organizado siguiendo el patrón MVC:

```
SistemaInventario/
├── src/
│   ├── modelo/              # MODELO (MVC)
│   │   ├── Producto.java
│   │   ├── Sucursal.java
│   │   ├── ProductoDTO.java          ← PATRÓN DTO
│   │   └── GestorInventario.java     ← PATRÓN SINGLETON
│   │
│   ├── controlador/         # CONTROLADOR (MVC)
│   │   ├── ObservadorInventario.java       ← PATRÓN OBSERVER (Interfaz)
│   │   ├── NotificadorStock.java           ← PATRÓN OBSERVER (Observable)
│   │   └── InventarioFacade.java           ← PATRÓN FACADE
│   │
│   ├── vista/               # VISTA (MVC)
│   │   ├── VistaInventario.java      ← PATRÓN OBSERVER (Observer)
│   │   ├── VistaBusqueda.java        ← PATRÓN OBSERVER (Observer)
│   │   └── VistaReportes.java        ← PATRÓN OBSERVER (Observer)
│   │
│   └── Main.java            # Demostración completa del sistema
│
├── bin/                     # Archivos compilados (.class) - se genera automáticamente
├── compile.bat              # Script de compilación para Windows
├── run.bat                  # Script de ejecución para Windows
├── compile.sh               # Script de compilación para Linux/Mac
├── run.sh                   # Script de ejecución para Linux/Mac
└── README.md               # Documentación del proyecto
```

---

## ⚙️ Requisitos Previos

### 1. Verificar si tienes Java instalado

1. Abre **PowerShell** o **Command Prompt (CMD)**
2. Escribe los siguientes comandos:

```cmd
java -version
javac -version
```

**Si ves un número de versión** (ejemplo: `java version "21.0.1"`), **ya tienes Java instalado** ✅

**Si NO tienes Java instalado:**

1. Descarga Java JDK desde: https://adoptium.net/
2. Selecciona "Windows" y descarga el instalador
3. Ejecuta el instalador y sigue los pasos (Next, Next, Finish)
4. Reinicia PowerShell/CMD
5. Verifica de nuevo con `java -version`

---

## 🚀 Cómo Compilar y Ejecutar en Windows

### **MÉTODO 1: Usando PowerShell (RECOMENDADO)**

Este método permite ver correctamente todos los emojis y símbolos del programa.

#### **Paso 1: Abrir PowerShell**
- Presiona `Windows + X`
- Selecciona **"Windows PowerShell"** o **"Terminal"**

#### **Paso 2: Navegar a la carpeta del proyecto**
```powershell
cd "C:\ruta\donde\guardaste\SistemaInventario"
```
Ejemplo:
```powershell
cd "C:\Users\juang\OneDrive - UVG\Documentos\Ingieria software\minicaso_software"
```

#### **Paso 3: Configurar la codificación UTF-8** ⚠️ **MUY IMPORTANTE**
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
chcp 65001
```

**Estos comandos son NECESARIOS para que los emojis se vean correctamente (✓, ⚠️, 🔔, etc.)**

**Sin estos comandos verás signos de interrogación (?) en lugar de emojis.**

#### **Paso 4: Compilar el proyecto**
```powershell
.\compile.bat
```

Deberías ver:
```
===============================================================
  Compilando Sistema de Gestion de Inventario
===============================================================
Compilando archivos Java...
Compilacion exitosa
```

#### **Paso 5: Ejecutar el programa**
```powershell
.\run.bat
```

O alternativamente:
```powershell
java -cp bin Main
```

---

### **MÉTODO 2: Usando Command Prompt (CMD)**

Si prefieres usar CMD en lugar de PowerShell:

#### **Paso 1: Abrir CMD**
- Presiona `Windows + R`
- Escribe `cmd` y presiona Enter

#### **Paso 2: Navegar a la carpeta**
```cmd
cd C:\ruta\donde\guardaste\SistemaInventario
```

#### **Paso 3: Configurar codificación UTF-8** ⚠️ **MUY IMPORTANTE**
```cmd
chcp 65001
```

#### **Paso 4: Compilar**
```cmd
compile.bat
```

#### **Paso 5: Ejecutar**
```cmd
run.bat
```

---

### **MÉTODO 3: Doble clic en archivos .bat (Más Sencillo pero sin emojis)**

⚠️ **Nota:** Este método es el más simple pero NO mostrará los emojis correctamente (verás signos de interrogación).

1. Abre la carpeta `SistemaInventario` en el Explorador de Windows
2. **Doble clic** en `compile.bat`
3. Espera a que compile (se cerrará automáticamente)
4. **Doble clic** en `run.bat`
5. Verás el programa ejecutándose

**Limitación:** Los emojis aparecerán como signos de interrogación (?).

---

## 🔧 Compilación Manual (Opcional)

Si los scripts .bat no funcionan, puedes compilar manualmente:

### En PowerShell o CMD:

```powershell
# Crear directorio para archivos compilados
mkdir bin

# Compilar todo el proyecto
javac -d bin src\modelo\*.java src\controlador\*.java src\vista\*.java src\Main.java

# Ejecutar el programa
java -cp bin Main
```

---

## 📊 Lo que el Programa Demuestra

El programa ejecuta **9 pasos** que demuestran todos los patrones:

### **PASO 1: Inicialización del Sistema**
- Demuestra **MVC**, **Facade** y **Singleton**
- Crea las 3 capas: Modelo, Vista, Controlador

### **PASO 2: Registro de Observadores**
- Demuestra el patrón **Observer**
- Registra 3 vistas que recibirán notificaciones

### **PASO 3: Consulta de Inventario**
- Demuestra el patrón **DTO**
- Muestra cómo los datos se transfieren sin exponer entidades

### **PASO 4: Búsqueda de Productos**
- Operaciones con **Facade**
- Búsqueda por nombre y visualización de detalles

### **PASO 5: Reporte de Stock Bajo**
- Generación de reportes
- Identifica productos que necesitan reabastecimiento

### **PASO 6: Actualización con Notificación** ⭐ **CLAVE**
- **Observer en acción:** Actualiza el iPhone de 3 a 15 unidades
- **TODAS las vistas reciben notificación automáticamente:**
  - Panel Principal ✓
  - Búsqueda Rápida ✓
  - Centro de Reportes ✓

### **PASO 7: Alerta de Stock Bajo** ⭐ **CLAVE**
- Actualiza el Monitor Samsung de 20 a 2 unidades
- **Se genera ALERTA crítica**
- **Las 3 vistas reciben la alerta automáticamente**

### **PASO 8: Verificación de Singleton**
- Consulta el producto actualizado
- Demuestra que todos usan la misma instancia de GestorInventario

### **PASO 9: Reporte Final**
- Resumen completo del inventario
- Estadísticas y alertas recibidas

---

## 🎯 Patrones Implementados

### 1. **MVC (Model-View-Controller)** - Patrón Arquitectónico
- **Modelo:** `GestorInventario`, `Producto`, `Sucursal`, `ProductoDTO`
- **Vista:** `VistaInventario`, `VistaBusqueda`, `VistaReportes`
- **Controlador:** `InventarioFacade`, `NotificadorStock`
- **Propósito:** Separación de responsabilidades en 3 capas

### 2. **Singleton**
- **Clase:** `GestorInventario`
- **Ubicación:** `src/modelo/GestorInventario.java`
- **Propósito:** Garantiza una única instancia del gestor de inventario
- **Beneficio:** Mantiene consistencia de datos en todo el sistema

### 3. **Facade**
- **Clase:** `InventarioFacade`
- **Ubicación:** `src/controlador/InventarioFacade.java`
- **Propósito:** Simplifica operaciones complejas entre Modelo y Vista
- **Beneficio:** Reduce acoplamiento y centraliza coordinación

### 4. **Observer**
- **Observable:** `NotificadorStock`
- **Observers:** `VistaInventario`, `VistaBusqueda`, `VistaReportes`
- **Interfaz:** `ObservadorInventario`
- **Ubicación:** `src/controlador/`
- **Propósito:** Notificaciones en tiempo real cuando cambia el inventario
- **Beneficio:** Sincronización automática entre vistas

### 5. **DTO (Data Transfer Object)**
- **Clase:** `ProductoDTO`
- **Ubicación:** `src/modelo/ProductoDTO.java`
- **Propósito:** Transfiere datos entre capas sin exponer entidades completas
- **Beneficio:** Optimiza comunicación y protege estructura interna

---

## ✅ Principios SOLID Aplicados

### **S - Single Responsibility Principle (SRP)**
- Cada clase tiene una responsabilidad única
- `GestorInventario`: solo gestiona inventario
- `InventarioFacade`: solo coordina operaciones
- `ProductoDTO`: solo transporta datos

### **O - Open/Closed Principle (OCP)**
- El sistema está abierto a extensión, cerrado a modificación
- Puedes agregar nuevas vistas (Observers) sin modificar código existente
- Puedes extender productos sin cambiar GestorInventario

### **L - Liskov Substitution Principle (LSP)**
- Cualquier implementación de `ObservadorInventario` es intercambiable
- Todas las vistas pueden sustituirse sin afectar el sistema

### **I - Interface Segregation Principle (ISP)**
- Las vistas solo implementan métodos que necesitan (`actualizarProducto`, `alertaStockBajo`)
- Interfaces mínimas y específicas

### **D - Dependency Inversion Principle (DIP)**
- Las vistas dependen de la abstracción `ObservadorInventario`, no de implementaciones concretas
- El controlador depende de interfaces del modelo, no de clases concretas

---

## ⚠️ Solución de Problemas Comunes

### **Problema: Los emojis no se ven (aparecen como ?)**

**Solución:**
1. Usa PowerShell en lugar de CMD
2. Ejecuta ANTES de correr el programa:
   ```powershell
   [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
   chcp 65001
   ```

### **Problema: "javac no se reconoce como comando"**

**Solución:**
- Java no está instalado o no está en el PATH
- Instala Java JDK desde https://adoptium.net/
- Reinicia PowerShell/CMD después de instalar

### **Problema: "No se puede encontrar o cargar la clase principal Main"**

**Solución:**
- El programa no está compilado
- Ejecuta primero `compile.bat`
- Verifica que exista la carpeta `bin/` con archivos .class

### **Problema: Error de compilación**

**Solución:**
- Verifica que todos los archivos .java estén en sus carpetas correctas
- Asegúrate de estar en el directorio raíz del proyecto
- Revisa que no haya errores de sintaxis si modificaste el código
---

## 📞 Resumen de Comandos Importantes

### **PowerShell (Recomendado para ver emojis):**
```powershell
# Configurar UTF-8 (para ver emojis correctamente)
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
chcp 65001

# Navegar al proyecto
cd "C:\ruta\a\SistemaInventario"

# Compilar
.\compile.bat

# Ejecutar
.\run.bat
```

### **Compilación Manual:**
```powershell
mkdir bin
javac -d bin src\modelo\*.java src\controlador\*.java src\vista\*.java src\Main.java
java -cp bin Main
```

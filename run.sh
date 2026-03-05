#!/bin/bash

echo "═══════════════════════════════════════════════════════════"
echo "  Ejecutando Sistema de Gestión de Inventario"
echo "═══════════════════════════════════════════════════════════"
echo ""

# Verificar que exista el directorio bin
if [ ! -d "bin" ]; then
    echo "✗ No se encontró el directorio 'bin/'"
    echo "  Ejecuta primero: ./compile.sh"
    exit 1
fi

# Ejecutar el programa
java -cp bin Main
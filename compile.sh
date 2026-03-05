#!/bin/bash

echo "═══════════════════════════════════════════════════════════"
echo "  Compilando Sistema de Gestión de Inventario"
echo "═══════════════════════════════════════════════════════════"

# Crear directorio para archivos compilados
mkdir -p bin

# Compilar todos los archivos Java
echo "Compilando archivos Java..."
javac -d bin src/model/*.java src/controller/*.java src/view/*.java src/Main.java

if [ $? -eq 0 ]; then
    echo "✓ Compilación exitosa"
    echo "  Los archivos .class están en el directorio 'bin/'"
    echo ""
    echo "Para ejecutar el programa, usa:"
    echo "  ./run.sh"
    echo "  o"
    echo "  java -cp bin Main"
else
    echo "✗ Error en la compilación"
    exit 1
fi
# FakeShop
Proyecto final de la materia de Programación III desarrollado en Java usando componentes de FlatLaf

FakeShop es una aplicación de escritorio desarrollada en Java utilizando la biblioteca gráfica Swing. El sistema simula la administración de una tienda, permitiendo gestionar productos, realizar ventas y almacenar la información de manera persistente mediante archivos.

# FUNDAMENTOS IMPLEMENTADOS

Java 
Java Swing para la interfaz gráfica
FlatLaf para la mejora visual del proyecto
POO (Programacion orientada a objetos)
Manejo de archivos para almacenar la informacion

# ESTRUCTURA

El proyecto esta organizado en distintos paquetes con enfoques especificos.
El "Main" contiene la clase principal encargada de iniciar la aplicacion, tambien condigura el proceso al cargar los datos y el estilo visual.
El package "Modelo" incluye las clases que representan la logia cel negocio, como lo serian "Productos", "Gestion de la tienda", "Gestion de ventas", y operaciones relacionadas con el almacenamiento y recuperacion de datos.
gui se encarga de contener todas las ventanas, formularios y componentes gráficos con los que interactúa el usuario.
"Tickets" almacena la informacion relacionadad con los comprobantes o registros generados durante las ventas.
Por ultimo, "lib" incluye las bibliotecas externas necesarias para el correcto funcionamiento del proyecto, incluyendo flatlaf.

La aplicacion guarda la informacion utilizando archivos locales, permitiendo conservar los datos incluso despues de cerrar el programa. para esto, se usaron archivos para almacenar la informacion de los productos, contener los datos de los usuarios registrados, registrar el historial de ventas realizadas  y generar reportes como tickets.

# EJECUTAR EL PROGRAMA

para que el programa se ejecute correctamente, se necesita contar con Java JDK instalado, un editor de codigo compatible como lo seria Eclipse o NetBeans, y las bibliotecas externas incluidas en la carpeta "lib"

# AUTORES

Proyecto desarrollado con fines academicos.
Hector Guerrero Villa
Maria Fernanda 

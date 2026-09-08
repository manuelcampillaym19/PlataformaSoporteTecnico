# 📌 Proyecto Java - Plataforma de Gestión de Soporte Técnico

## 🧾 Descripción

- Este proyecto consiste en un sistema desarrollado en Java para gestionar usuarios y solicitudes de reparación de una empresa de soporte técnico. El sistema permite registrar usuarios, crear solicitudes, modificar y eliminar registros, finalizar solicitudes, registrar valoraciones y consultar el nivel de satisfacción.

---

## ⚙️ Funcionalidades

- Registro y gestión de usuarios
- Registro y administración de solicitudes de reparación
- Búsqueda de usuarios por ID
- Búsqueda de solicitudes por ID
- Modificación y eliminación de usuarios
- Modificación y eliminación de solicitudes
- Finalización de solicitudes
- Registro de valoraciones de satisfacción
- Consulta del promedio de satisfacción
- Detección de solicitudes prioritarias según tiempo pendiente
- Persistencia de datos en archivos `.txt`
- Interfaz mediante consola
- Interfaz mediante ventanas gráficas (`JOptionPane`)

---

## 🛠️ Tecnologías utilizadas

- Java
- Oracle JDK 11
- NetBeans IDE
- Programación Orientada a Objetos (POO)
- Java Collections Framework (JCF)
- `HashMap` y `ArrayList`
- Manejo de archivos
- Excepciones personalizadas
- `JOptionPane`

---

## 📂 Estructura del proyecto

- `src/gestionSoporte/` → Código fuente del sistema
- `personaBase.java` → Clase base para personas
- `Usuario.java` → Gestión de usuarios y solicitudes asociadas
- `Solicitud.java` → Gestión de solicitudes de reparación
- `archivoDatos.java` → Carga y almacenamiento de datos
- `Sistema.java` → Menús, operaciones y coordinación del sistema
- `UsuarioNoEncontradoException.java` → Excepción para usuarios inexistentes
- `SolicitudNoEncontradaException.java` → Excepción para solicitudes inexistentes
- `usuarios.txt` → Almacenamiento de usuarios
- `solicitudes.txt` → Almacenamiento de solicitudes

---

## 🚀 Cómo ejecutar

1. Descargar o clonar el repositorio.
2. Abrir el proyecto en NetBeans o en un IDE compatible con Java.
3. Ejecutar la clase **Sistema.java**.

Al iniciar el programa, se debe seleccionar el modo de uso:

- **1 → Consola**
- **2 → Ventana gráfica (`JOptionPane`)**

---

## 💾 Persistencia de datos

- Los usuarios y solicitudes se almacenan en archivos `.txt`.
- Los datos existentes se cargan automáticamente al iniciar el sistema.
- Los cambios se guardan durante las operaciones y al finalizar la ejecución.
- Si no existen archivos de datos en la primera ejecución, el sistema carga información inicial para permitir probar las funcionalidades.

---

## 📝 Notas

- El sistema utiliza identificadores para localizar usuarios y solicitudes.
- Las solicitudes se encuentran asociadas a un usuario.
- Un usuario que posee solicitudes asociadas no puede ser eliminado.
- Las solicitudes deben estar cerradas para poder registrar una valoración.
- Las solicitudes prioritarias se determinan según el tiempo que llevan pendientes.

---

## 👨‍💻 Autores
- Gonzalo Benavente
- Mario Saavedra
- Manuel Campillay

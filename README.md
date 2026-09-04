\# Plataforma de Gestión de Soporte Técnico



\## Descripción



La Plataforma de Gestión de Soporte Técnico es un sistema desarrollado en Java que permite administrar usuarios y solicitudes de reparación de dispositivos eléctricos y electrónicos.



El sistema permite mantener organizada la información de los usuarios, asociar solicitudes de reparación y realizar un seguimiento de su estado.



\## Objetivo



El objetivo del proyecto es proporcionar una herramienta sencilla para registrar y administrar usuarios y solicitudes de soporte técnico, facilitando su consulta, modificación y seguimiento.



\## Funcionalidades principales



\### Gestión de usuarios



\- Registrar nuevos usuarios.

\- Consultar los usuarios registrados.

\- Localizar un usuario mediante su identificador.

\- Modificar los datos de un usuario.

\- Dar de baja un usuario cuando no posee solicitudes asociadas.



\### Gestión de solicitudes



\- Registrar nuevas solicitudes asociadas a un usuario.

\- Consultar las solicitudes registradas.

\- Localizar una solicitud mediante su identificador.

\- Modificar la información de una solicitud.

\- Dar de baja una solicitud.

\- Finalizar solicitudes registrando su tiempo de atención.



\### Satisfacción y seguimiento



\- Registrar una valoración de 1 a 5 para solicitudes finalizadas.

\- Consultar el promedio de satisfacción de las solicitudes valoradas.

\- Detectar solicitudes pendientes que superan un tiempo de espera determinado.



\### Formas de interacción



El sistema puede utilizarse mediante:



\- Consola.

\- Ventanas gráficas mediante `JOptionPane`.



\## Tecnologías utilizadas



\- Java

\- NetBeans

\- Java Collections Framework

\- Archivos de texto para persistencia

\- JOptionPane para la interfaz mediante ventanas

\## Estructura del proyecto



El proyecto se encuentra organizado en diferentes clases, cada una con una función específica dentro del sistema.



\### Clases principales



\- `personaBase`: clase abstracta que contiene los datos generales compartidos por las personas registradas, como nombre y correo electrónico.



\- `Usuario`: representa a un usuario del sistema. Hereda de `personaBase` y mantiene la información de sus solicitudes asociadas.



\- `Solicitud`: representa una solicitud de reparación. Contiene información como identificador, detalle, estado, tiempo de atención, valoración y usuario asociado.



\- `Sistema`: controla el funcionamiento general de la aplicación, incluyendo los menús, las operaciones y las formas de interacción con el usuario.



\- `archivoDatos`: se encarga de guardar y cargar la información de usuarios y solicitudes mediante archivos de texto.



\### Clases de excepciones



\- `UsuarioNoEncontradoException`: se utiliza cuando se intenta localizar un usuario que no se encuentra registrado.



\- `SolicitudNoEncontradaException`: se utiliza cuando se intenta localizar una solicitud que no existe en el sistema.












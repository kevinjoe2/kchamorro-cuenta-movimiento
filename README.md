# Microservicio cuenta - movimientos

Tecnologias utilizadas.
- Java 17
- Gradle 7
- Spring 3.1.5
- Postgresql

Para levantar la base de datos en un contenedor docker utilizar el siguiente comanda:
- ``docker run -p 25432:5432 --name cuenta_movimiento -e POSTGRES_PASSWORD=cuenta_movimiento_pwd -e POSTGRES_USER=cuenta_movimiento_usr -e POSTGRES_DB=cuenta_movimiento_db -d postgres``

### Para realizar pruebas se creo un archivo que contiene CURLs
- ``KCHAMORRO.postman_collection.json``

### Adicional se creo un archivo dockerfile para poder desplegar el microservicio en docker utilizando el siguiente comando:
1. Compilar el proyecto
- ``gradle clean build``
2. Crear imagen docker
- ``docker build -t cuenta-movimiento-imagen .``
3. Iniciar el contenedor con la imagen creada
- ``docker run -p 8082:8080 --name cuenta-movimiento-contenedor -d cuenta-movimiento-imagen``

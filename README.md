# **Ejercicio de Programación**

## **Índice**
---
1. [Ejercicio](#id1)
2. [Descripción de la solución](#id2)
3. [Herramientas](#id3)
4. [Requerimientos](#id4)
5. [Obtner el código fuente](#id5)
6. [Docker](#id6)
7. [Casos de prueba](#id7)

>## **Ejercicio**
<div id='id1' />
---

Para coordinar acciones de respuesta ante fraudes, es útil tener disponible información
contextual del lugar de origen detectado en el momento de comprar, buscar y pagar. Para
ello se decide crear una herramienta que dada una IP obtenga información asociada.
El ejercicio consiste en construir una API Rest que permita:

1. Dada una dirección IP, encontrar el país al que pertenece y mostrar:
   * El nombre y código ISO del país
   * Moneda local y su cotización actual en dólares o euros.
2. Ban/Blacklist de una IP: marcar la ip en una lista negra no permitiéndole consultar el
la información del punto 1.

**Observaciones**: Tener en cuenta que el punto 1 puede recibir fluctuaciones agresivas de
tráfico.

Para obtener la información, pueden utilizarse las siguientes APIs públicas:

+ Geolocalización de IPs:​ ​https://ip2country.info/
+ Información de paises:​ ​http://restcountries.eu/
+ Información sobre monedas:​ ​http//fixer.io/

**Consideraciones**
---
+ Se solicita una solución con un diseño OOP.
+ La solución debe ser en Java, Kotlin o Groovy.
+ Incluir tests que aseguren el correcto funcionamiento de la API. Idealmente de caja blanca (unitarios) y caja negra (end to end / funcionales).
+ Es deseable que la aplicación pueda correr, ser construida y ejecutada dentro de un       contenedor Docker (incluir un Dockerfile e instrucciones para ejecutarlo).
+ La aplicación deberá hacer un uso racional de las APIs, evitando hacer llamadas
    innecesarias.
+ La aplicación no deberá perder su estado ante un shutdown.
+ Además de funcionamiento, prestar atención al estilo y calidad del código fuente.

>## **Descripción de la solución**
<div id='id2' />
---
La aplicación sale por el puerto 9090 y el endpoint es http://localhost:9090/ip-validator/api
se crea el end-point /ip/information/{ip} de tipo GET, donde recibe como parámetro el número de IP del cual se quiere obtener la información.

Para prevenir el llamado a los terceros se creó una validación al parámetro IP por medio de una expresión regular, después de esto se valida que no se encuentre en la tabla de ban list y finalmente al empezar a consumir los servicios de los terceros que nos suministran la información se valida que la información se obtenga para continuar con los llamados a los otros servicios.

Si no es posible obtener la información de algún servicio o falla en una de las validaciones se responde al cliente con un status **HTTP 400 bad request**, de ser una petición exitosa se responde un JSON con status **HTTP 200 OK**. De encontrar una falla por el lado del servidor se responde el estatus **HTTP 500 internal server error**.

Se crea el end-point /ip/banned de tipo POST, donde recibe un objeto JSON con un parámetro de tipo String llamado ipNumber para agregar este a la ban list de base de datos.
Si se agrega de forma exitosa se responde con el estatus **HTTP 201 created**. este servicio también realiza las validaciones deIP valida y si existe ya en la banlist, si no pasa estas validaciones responde un **HTTP 400 bad reques**.


>## **Herramientas**
<div id='id3' />
---
Para este ejercicio se utilizaron las siguientes Herramientas:

- Java 11
- SpringBoot 2.4.5
- JUnit 5
- Mockito
- Docker
- Postman
- Gradle

>## **Requerimientos**
<div id='id4' />
---
- Java 11
- Gradle >= 5.6
- Docker


>## **Obtner el código fuente**
<div id='id5' />
---
El código fuente se encuentra en GitHub, para obtenerlo se debe ejecutar el siguiete comando en la terminal o ir al sitio directamente. [repositorio](https://github.com/bweto/ip-validator.git)

        git clone https://github.com/bweto/ip-validator.git

Al descargar el repositorio y abrirlo en un IDE podemos tener acceso en la raíz del proyecto a la capeta coverage que tiene el informe generado por jacoco al correr el comando `gradle test` y la carpeta postman que tiene un JSON que puede ayudar a las pruebas de la aplicación.

La aplicación al levantarse cuenta con un swagger en la siguiente URL `http://localhost:9090/ip-validator/api/swagger-ui/`

>## **Docker**
<div id='id6' />
---
Actualmente se encuentra en dockerHub y se puede obtener con el siguiente comando 
`docker pull rbdesign/ip_validator:latest`
y correr con este `docker run -p 9090:9090 rbdesign/ip_validator`

La aplicación cuenta con un Dockerfile en la raíz del proyecto con la cual podemos crear una imagen del proyecto ejecutando los siguientes comandos:

*Para esto ubicarnos en la raíz del proyecto*

- `gradle clean build` este comando valida el código fuente y construye el artefacto jar en la ruta del directorio raíz /build/libs/ allí se crea el artefacto ipValidator.jar

- Crear imagen con el siguiente comando `docker build -t ip_validator .`

- Correr imagen con el siguiente comando `docker run -p 9090:9090 ip_validator`

>## **Casos de prueba**
<div id='id7' />

1. Obtener información de una IP:

    Para probar el servicio de obtener información de una IP, se debe consumir con una petición GET, enviando en vez de {id} enviar la IP de la cual se desea conocer la información.
    http://localhost:9090/ip-validator/api/ip/information/{id}

    Ejemplo:

    GET Request: 

    http://localhost:9090/ip-validator/api/ip/information/186.35.6.23

    Response JSON, exitoso HTTP 200:

        {
          "countryName": "Chile",
          "countryISO": "CHL",
          "currency": "CLP",
          "base": "EUR",
          "price": 849.959912
        }

    `curl -vl 'localhost:9090/ip-validator/api/ip/information/186.35.6.23'`

2. Obtener información de una IP invalida:

    GET Request:

    http://localhost:9090/ip-validator/api/ip/information/186.35.6.2385

    Response Bad request HTTP 400:

        { 
            "message": "Invalid IP"
        }
    
    `curl -vl 'localhost:9090/ip-validator/api/ip/information/186.35.6.2385'`

3. Agregar ip a la ban list:


    http://localhost:9090/ip-validator/api/ip/banned

    POST Request JSON:

        { 
            "ipNumber": "186.35.6.23" 
        }

    Response JSON created HTTP 201:

        {
            "message": "The IP was successfully added to the ban list."
        }

    `curl --location --request POST 'localhost:9090/ip-validator/api/ip/banned' \
        --header 'Content-Type: application/json' \
        --data-raw '{
            "ipNumber": "192.168.168.168"
        }'`

4. Tratar de conseguir información de una IP en la ban list

    Después de agregar la IP vamos a probar si esta quedo en la ban lista tratando de consultar información de nuevo.

    GET Request

    http://localhost:9090/ip-validator/api/ip/information/186.35.6.23

    Response bad request HTTP 400

    {
        "message": "This IP is already on the ban list"
    }

    `curl -vl 'localhost:9090/ip-validator/api/ip/information/186.35.6.23'`

>Gracias Atte. *Roberto García Betancourt*
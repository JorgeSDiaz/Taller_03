# TALLER 3: MICROFRAMEWORKS WEB
Se construye un microframework WEB bajo basándonos en el conocido [spark](https://sparkjava.com/), este se construye implementando funciones lambda, dando así la posibilidad de definir servicios get, ruta de archivos estáticos y su entrega. Además de implementarse un patron de integración de 3 capas.

---
### Prerrequisitos
Para elaborar este proyecto requerimos de las siguientes tecnologias:

 - **[Maven](https://openwebinars.net/blog/que-es-apache-maven/)**: Apache Maven es una herramienta que estandariza la configuración de un proyecto en todo su ciclo de vida.
 - **[Git](https://learn.microsoft.com/es-es/devops/develop/git/what-is-git)**: Es un sistema de control de versiones distribuido (VCS).

---
###  Instalación
Primero clonamos el repositorio

     git clone https://github.com/jorge-stack/Taller_03.git
    
Se accede al repositorio que acabamos de clonar

	 cd Taller_03

Hacemos la construccion del proyecto

	 mvn clean package install
---
### Corriendo
Ahora corremos el servidor

**Windows**

	  mvn exec:java -"Dexec.mainClass"="org.myorg.App"

**Linux/MacOs**

	 mvn exec:java -Dexec.mainClass="org.myorg.App"

### Run
Para arrancar el servidor debemos de definir el puerto por el que deseamos que corra
![Run](https://i.imgur.com/W3y9ZjT.png)
Definir siempre antes del run, los métodos get que deseamos definir, el acceso a los archivos estáticos se genera nada más arranque el servidor, y en caso de algún error de tipeo o ruta no definida, se le mostrara un mensaje en formato Json con las rutas que estas disponibles dentro del servidor
**Windows**
![Help](https://i.imgur.com/jiD5oKg.png)
**Ubuntu**

### StaticFiles
Se puede definir una ruta local donde se tomarán archivos para poder responder de las consultas en el navegador, cabe decir que la ruta por defecto apunta a la carpeta "resources" dentro del proyecto, en el caso de desear poner alguna otra ruta, se haría de la siguiente manera
![Static](https://i.imgur.com/RflIn8Y.png)
Puede acceder a ellos poniendo en el navegador 

	 https://localhost:PortAssigned/fileName.extension

Se vería algo así
**Windows**
![Image](https://i.imgur.com/a1gcjkL.png)
**Ubuntu**

Recuerde que una vez empieza a correr el servidor estas rutas se generan de manera automática

### Get

Antes de arrancar el servidor definimos los métodos GET que necesitemos, sabiendo que podremos acceder a ellos accediendo a ellos siguiendo este patron.

	 https://localhost:PortAssigned/apps/pathDefined

Veamos un ejemplo con "/hello", por defecto el content type sera "text/plain"
![HelloWorld](https://i.imgur.com/lhdGisL.png)
**Windows**
![Hello](https://i.imgur.com/Oypzv8s.png)
**Ubuntu**

Tambien podemos decidir en qué formato nos deberá dar la respuesta, especificando de la siguiente manera
![json](https://i.imgur.com/o7IDY4b.png)
**Windows**
![HelloJson](https://i.imgur.com/GNxIucq.png)
**Ubuntu**

Si lo deseamos también podemos definir rutas que consuman servicios rest enlazados a una Api o conexión Http, lo haremos de la siguiente forma
![RService](https://i.imgur.com/p5Uz6fc.png)
Para el ejemplo definimos una conexión al servicio api https://www.omdbapi.com, para poder usar el servicio de consulta que se define en el RestService pasado por parámetro
**Windows**
![Rest](https://i.imgur.com/K6cPyCC.png)
**Ubuntu**


---
## Corriendo test

Ejecutamos el comando

	mvn Test
	
---
## Construido con

* [Maven](https://maven.apache.org/): Apache Maven es una herramienta que estandariza la configuración de un proyecto en todo su ciclo de vida.
* [Git](https://rometools.github.io/rome/):  Es un sistema de control de versiones distribuido (VCS).
* [IntelliJ](https://www.jetbrains.com/idea/): Es un entorno de desarrollo integrado para el desarrollo de programas informáticos.
* [Java 19](https://www.java.com/es/): Lenguaje de programación de propósito general, es decir, que sirve para muchas cosas, para web, servidores, aplicaciones móviles, entre otros. Java también es un lenguaje orientado a objetos, y con un fuerte tipado de variables.
* [Html](https://developer.mozilla.org/es/docs/Learn/Getting_started_with_the_web/HTML_basics): Es el código que se utiliza para estructurar y desplegar una página web y sus contenidos.
* [JavaScript](https://developer.mozilla.org/es/docs/Learn/JavaScript/First_steps/What_is_JavaScript): JavaScript es un lenguaje de programación o de secuencias de comandos que te permite implementar funciones complejas en páginas web
* [CSS](https://developer.mozilla.org/es/docs/Learn/CSS/First_steps/What_is_CSS):Las hojas de estilo en cascada permiten crear páginas web atractivas.

## Autor
* **[Jorge David Saenz Diaz](https://co.linkedin.com/in/jorgedsaenzd/en)**  - [Jorge-Stack](https://github.com/jorge-stack?tab=repositories)

## Licencia
**©** Jorge David Saenz Diaz, Estudiante de Ingeniería de Sistemas de la Escuela Colombiana de Ingeniería Julio Garavito.

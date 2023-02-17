package org.myorg;

import org.myorg.logic.Protocol;
import org.myorg.logic.service.RESTService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.myorg.logic.http.Server.*;

public class App {
    public static void main(String[] args) throws IOException {
        // La ruta queda registrada como "/apps/{path}"
        Get("/hello",
                () -> """
                        Hello World!
                        """
        );

        // Format Available: json, html, css, js, plain, jpg
        Get("/hellojson",
                () -> """
                        {
                            "Message": "Hello World!"
                        }
                        """,
                "json"
        );

        // Si desea que su RestService tenga utilidad con alguna API, por favor configure la conexion Http
        setConnection("https://www.omdbapi.com/", "&apikey=2701988f");

        // Defina el path del fetch como /{formato_respuesta}-service-/path, siendo el path el nombre
        // del servicio que añadió con GET(path, RESTService)
        Get("/api", new RESTService() {

            @Override
            public List<String> getHeader() {
                List<String> header = new ArrayList<>();
                header.add("GET HTTP/1.1 200 OK\"");
                header.add("Content-Type: text/html");
                return header;
            }

            @Override
            public Protocol getProtocol() {
                return Protocol.GET;
            }

            @Override
            public String getResponse() {
                return """
                        <!DOCTYPE html>
                        <html>
                          <head>
                            <title>Movies</title>
                            <meta charset="UTF-8" />
                            <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                            <style>
                              label {
                                color: red;
                                font-size: 25px;
                              }
                              input[type="text"]{
                               width: 150px;
                               height: 30px;
                               font-size: 15px;
                               color: darkgray;
                              }
                              input[type="text"]:focus {
                                color: black;
                              }
                              input[type="button"] {
                                width: 100px;
                                height: 25px;
                              }
                            </style>
                          </head>
                          <body>
                            <h1>GET Movie by Title</h1>
                            <form action="json/api">
                              <label for="name">Title  </label>
                              <input type="text" id="title" value="indiana" /><br /><br />
                              <input type="button" value="Submit" onclick="loadGetMsg()" />
                            </form>
                            <br>
                            <div id="getrespmsg"></div>
                            <script type="text/javascript">
                              function loadGetMsg() {
                                let title = document.getElementById("title");
                                let url = "json-service/api/?t=" + title.value;
                                console.log(url);
                                fetch(url, { method: "GET", mode:"no-cors"})
                                  .then((x) => x.text())
                                  .then((y) => (document.getElementById("getrespmsg").innerHTML = y));
                              }
                            </script>
                          </body>
                        </html>
                                """;
            }
        });

        // Si no cambia la ruta, la ruta por defecto va a ser "src/main/resources"
        // Los archivos en la ruta definida ya se pueden acceder desde que se arranca el servidor
        // Acceda a ellos con "/{nameFile.extension}"
        // setRouteFiles(); // Función para definir la ruta de los archivos estáticos

        // Se arranca el servidor en el puerto que se le indique
        run(3000);

    }
}
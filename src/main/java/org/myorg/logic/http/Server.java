package org.myorg.logic.http;

import org.myorg.visual.page.Page;
import org.myorg.logic.service.RESTService;
import org.myorg.logic.Protocol;
import org.myorg.visual.page.PageFile;
import org.myorg.visual.page.Pages;
import org.myorg.logic.Route;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server {

    public static OutputStream outputStream;
    public static PrintWriter output;
    private static Connection connection;
    private static Map<String, Pages> endPoints = new HashMap<>();
    private static String routeFiles = "src/main/resources/";

    public static void run(int port) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Listing on: http://localhost:" + port + "/");
        } catch (IOException e) {
            System.out.println("Couldn't Listen on Port: " + port);
            System.exit(1);
        }

        Socket clientSocket = null;
        while (!serverSocket.isClosed()) {
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Ready to Receive");
            } catch (IOException exception) {
                System.out.println("Accept Failed");
                System.exit(1);
            }

            outputStream = clientSocket.getOutputStream();
            output = new PrintWriter(outputStream, true);
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            generateGetFilesFromPath();

            boolean firstLine = true;
            String path = "/Simple";
            while ((inputLine = input.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (firstLine) {
                    path = inputLine.split(" ").length > 1 ? inputLine.split(" ")[1] : path;
                    firstLine = false;
                }
                if (!input.ready()) {
                    break;
                }
            }

            if (!endPoints.containsKey(path)) {
                if (path.contains("-service/")) {
                        for (String line : generateHeader(path.split("-")[0], Protocol.GET)) {
                            output.println(line);
                        }
                        output.println();
                        try {
                            String query = path.split("/" )[4];
                            output.println(connection.getData(query));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                } else {
                    for (String line : generateHeader("json", Protocol.GET)) {
                        output.println(line);
                    }
                    output.println();
                    output.println("{ \"EndPoints\":" + (endPoints.keySet().stream().sorted().
                            map(key -> "\"" + key + "\"").toList()) + "}");
                }
            } else {
                Pages page = endPoints.get(path);
                page.getConsumer().accept(page);
            }

            output.close();
            input.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    public static void setRouteFiles(String location) {
        routeFiles = location;
    }

    public static void Get(String path, Route content) {
        Pages page = new Page(content,
                (Pages pages) -> {
                    for (String line : pages.getHeader()) {
                        output.println(line);
                    }
                    output.println();
                    try {
                        output.println(pages.getContent());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, generateHeader(content.getContent(), Protocol.GET)
        );

        Get("/apps" + (path.startsWith("/") ? path : "/" + path), page);
    }

    public static void Get(String path, Route content, String responseType) {
        Pages page = new Page(content,
                (Pages pages) -> {
                    for (String line : pages.getHeader()) {
                        output.println(line);
                    }
                    output.println();
                    try {
                        output.println(pages.getContent());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, generateHeader(responseType, Protocol.GET)
        );

        Get("/apps" + (path.startsWith("/") ? path : "/" + path), page);
    }

    private static void Get(Route content) {
        String route = routeFiles + content.getContent();
        Route contentRoot = () -> route;
        Pages page = new PageFile(contentRoot,
                (Pages pages) -> {
                    for (String line : pages.getHeader()) {
                        output.println(line);
                    }
                    output.println();
                    if (pages.getHeader().get(1).equals("Content-Type: image/jpeg")) {
                        try {
                            outputStream.write(((PageFile) pages).getContentBytes());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            output.println(pages.getContent());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, generateHeader(content.getContent().split("\\.")[1], Protocol.GET)
        );

        Get("/" + content.getContent(), page);
    }

    public static void Get(String path, RESTService service) {
        Pages page = new Page(service::getResponse,
                (Pages pages) -> {
                    for (String line : pages.getHeader()) {
                        output.println(line);
                    }
                    output.println();
                    try {
                        output.println(pages.getContent());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }, service.getHeader()
        );

        Get("/apps" + path, page);
    }

    public static void setConnection(String url, String apiKey) {
        connection = new Connection(url, apiKey);
    }

    public static void setConnection(String url) {
        connection = new Connection(url);
    }

    private static List<String> generateHeader(String content, Protocol protocol) {
        List<String> header = new ArrayList<>();
        String response = "";
        if (protocol.equals(Protocol.GET)) {
            response += "GET ";
        } else if (protocol.equals(Protocol.POST)) {
            response += "POST ";
        }
        header.add(response + "HTTP/1.1 200 OK");
        switch (content) {
            case "html" -> header.add("Content-Type: text/html");
            case "css" -> header.add("Content-Type: text/css");
            case "js" -> header.add("Content-Type: text/javascript");
            case "jpg" -> header.add("Content-Type: image/jpeg");
            case "json" -> header.add("Content-Type: application/json");
            default -> header.add("Content-Type: text/plain");
        }
        return header;
    }

    private static void generateGetFilesFromPath() throws IOException {
        List<String> fileNames = Files.list(Paths.get(routeFiles)).
                filter(Files::isRegularFile).
                map(Path::getFileName).
                map(Path::toString).
                toList();

        for (String fileName : fileNames) {
            Get(() -> fileName);
        }
    }

    private static void Get(String path, Pages page) {
        endPoints.put(path, page);
    }

}

package org.myorg.httpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Server {

    public static OutputStream outputStream;
    public static PrintWriter output;
    private static Map<String, Pages> getEndPoints = new HashMap<>();
    String routeFiles = "src/main/resources";

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

            if (getEndPoints.containsKey(path)) {
                Pages page = getEndPoints.get(path);
                page.getConsumer().accept(page.getContent());
            }

            output.close();
            input.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    public static void Get(String path, Route content) {
        String cont = content.content();
        Consumer<String> block = (String apply) -> {
            for (String line: generateHeader(apply)) {
                output.println(line);
            }
            output.println();
            output.println(apply);
        };

        Pages page = new Page(cont, block);
        getEndPoints.put(path, page);
    }

    private static List<String> generateHeader(String content) {
        List<String> header = new ArrayList<>();
        header.add("HTTP/1.1 200 OK");
        if (content.startsWith("<!DOCTYPE html>")) {
            header.add("Content-type: text/html");
        } else {
            header.add("Content-Type: text/plain");
        }
        return header;
    }
}

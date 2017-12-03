package io.github.bael.dictionary.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Server {

    // Порт словаря по умолчанию
    private static final int SERVER_DEFAULT_PORT = 9000;

    private static void log(String msg) {
        System.out.println(msg);
    }


    private final WordDictionary dictionary;

    void start() {

        //Socket clientSocket = null;
        try (
                ServerSocket serverSocket = new ServerSocket(port);
        ) {

            log("Сервер запущен на порту " + port);

            boolean isStopped = false;
            while (!isStopped) try (Socket clientSocket = serverSocket.accept();
                                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());) {

                System.out.println("Получили соединение " + clientSocket);

                List<String> command = (List<String>) in.readObject();
                System.out.println("Прочитали команду " + command);



                String commandStr = command.get(0);
                String term = command.get(1);
                HashSet<String> values = new HashSet<>();
                values.addAll(command.subList(2, command.size()));

                log(commandStr);
                log(term);
                log(values.toString());

                List<Object> result = new ArrayList<>();

                switch (commandStr) {
                    case "add":
                        result.add(dictionary.addDefinitions(term, values));
                        break;
                    case "get":
                        result.addAll(dictionary.getDefinitions(term));
                        break;
                    case "remove":
                        result.add(dictionary.removeTerm(term));

                        break;
                    default:
                        result.add("Неизвестная команда");
                }


                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                out.writeObject(result);
                out.flush();
                log("ответили ");

                out.close();
                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    // Порт сервера
    private final int port;

    public Server(int portToSet) {

            if (portToSet> 1000 && portToSet< 65535) {
                port = portToSet;
            } else {
                port = SERVER_DEFAULT_PORT;
            }

            dictionary = WordDictionary.createDictionary();
    }

    public static void main(String[] args) {

        try {

            int portToSet = Integer.parseInt(args[0]);
            Server server = new Server(portToSet);
            server.start();

        } catch (NumberFormatException e) {
            log("Подан некорректный порт для старта сервера:"+args[0]);
        }

    }



}

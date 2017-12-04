package io.github.bael.dictionary.server;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    // Порт словаря по умолчанию
    private static final int SERVER_DEFAULT_PORT = 9000;

    private static void log(String msg) {
        System.out.println(msg);
    }

    private final ExecutorService threadPool;

    private final WordDictionary dictionary;

    private void start() {


        try (
                ServerSocket serverSocket = new ServerSocket(port)
        ) {

            log("Сервер запущен на порту " + port);

            boolean isStopped = false;
            while (!isStopped) {

                try {
                    this.threadPool.execute(
                            new DictionaryCommandRunnable(serverSocket.accept(), dictionary));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    // Порт сервера
    private final int port;

    private Server(int portToSet) {

            if (portToSet> 1000 && portToSet< 65535) {
                port = portToSet;
            } else {
                port = SERVER_DEFAULT_PORT;
            }
            this.threadPool = Executors.newFixedThreadPool(20);

            dictionary = WordDictionary.createMultiThreadDictionary();
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

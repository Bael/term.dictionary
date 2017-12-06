package io.github.bael.dictionary.server;

import io.github.bael.dictionary.termdictionary.ConcurrentDictionary;
import io.github.bael.dictionary.termdictionary.DictionaryFactory;
import io.github.bael.dictionary.termdictionary.TermDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {



    private final ExecutorService threadPool;

    private final TermDictionary dictionary;

    final Logger logger = LoggerFactory.getLogger(ConcurrentDictionary.class);

    private void start() {

        logger.info("Server trying to start on port {}", port);

        try (
                ServerSocket serverSocket = new ServerSocket(port)
        ) {

            logger.info("Server started on port {}", port);

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
            logger.error("Server IOException occurred {}", e);
            e.printStackTrace();

        } catch (Exception e) {
            logger.error("Server Exception occurred {}", e);
            e.printStackTrace();

        } finally {
            logger.info("Shutting thread pool");
            threadPool.shutdownNow();
        }
    }

    // server port
    private final int port;
    private final static int MIN_PORT = 1000;
    private final static int MAX_PORT = 65535;
    // default port
    private static final int SERVER_DEFAULT_PORT = 9000;

    // thread count
    private final static int THREADS_COUNT = 20;

    private Server(int portToSet) {

        logger.info("Creating server instance with port {}", portToSet);

        if (portToSet > MIN_PORT && portToSet <= MAX_PORT) {
            port = portToSet;
        } else {
            port = SERVER_DEFAULT_PORT;
        }
        this.threadPool = Executors.newFixedThreadPool(THREADS_COUNT);

        dictionary = DictionaryFactory.createMultiThreadDictionary(THREADS_COUNT);
    }

    public static void main(String[] args) {

        final Logger logger = LoggerFactory.getLogger(ConcurrentDictionary.class);
        try {
            logger.info("Dictionary server is called with params {}", (Object[]) args);

            int portToSet = Integer.parseInt(args[0]);
            Server server = new Server(portToSet);
            server.start();

        } catch (NumberFormatException e) {
            logger.error("Error initiating Dictionary server. Check the port number in given args {}", (Object[]) args);
        }
        catch (Exception e) {
            logger.error("Server crushed, exception {}", e);
            e.printStackTrace();
        }

    }



}

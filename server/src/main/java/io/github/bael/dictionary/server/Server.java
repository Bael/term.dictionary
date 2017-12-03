package io.github.bael.dictionary.server;

import io.github.bael.dictionary.DictionaryCommand;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    private static void log(String msg) {
        System.out.println(msg);
    }
    public static void main(String[] args) {

        int port = 9000;
        if (args != null && args.length > 0) {
              int arg0 = (Integer.parseInt(args[0]));
              if (arg0 > 1000 && arg0 < 65535) {
                  port = arg0;

              }


        }

        log("Сервер запущен на порту " + port);


        //Socket clientSocket = null;
        try (
                ServerSocket serverSocket = new ServerSocket(port);

            ) {


            boolean isStopped = false;
            while (!isStopped) {

                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());) {

                    System.out.println("Получили соединение " + clientSocket);

                    List<String> command = (List<String>) in.readObject();
                    System.out.println("Прочитали команду " + command);



                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    out.writeObject(new String("Command recieved:"+command));
                    out.flush();
                    System.out.println("ответили ");

                    out.close();
                    in.close();

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
}

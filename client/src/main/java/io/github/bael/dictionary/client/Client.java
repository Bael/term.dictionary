package io.github.bael.dictionary.client;


import io.github.bael.dictionary.DictionaryCommand;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * This is a dictionary client. It can add, get, or delete term definitions of Dictionary.
 */
class Client {

    // waiting no more 20s
    private static int SOCKET_TIMEOUT = 2000;
    private final String serverIP;
    private final int serverPort;
    private final DictionaryCommand command;

    public Client(String[] args) throws IllegalArgumentException {

        if (args.length < 4) {
            throw new IllegalArgumentException("Не указаны нужные аргументы!");
        }
        serverIP = args[0];
        serverPort = Integer.parseInt(args[1]);
        command = new DictionaryCommand(Arrays.asList(args).subList(2, args.length));
    }

    /**
     * Main entry method
     *
     * @param args arguments of program from command line<p>
     *             Preconditions: args[0] - valid ip, args[1] port for successful connection <br>
     *             args[2] - command to complete after connection <br>
     *             args[3] - term to process <br>
     *             args[4] - definitions of term <br>
     *             </p>
     */
    public static void main(String[] args) {

        try {

            Client client = new Client(args);
            client.executeCommandOnServer();

        }
        catch (IllegalArgumentException ex) {

            System.out.println(ex.getLocalizedMessage());

            System.out.format("Укажите корректные параметры запуска:%n 1) %s %n 2) %s %n 3) %s %n 4) %s %n 5) %s %n %s %n",
                    "IP адрес сервера",
                    "Порт сервера",
                    "Название команды, допустимые значения add get remove",
                    "Термин, который вы хотите обработать командой",
                    "Значения термина (в случае команды add)",
                    "Пример корректного запуска: java -jar client.jar 192.168.0.1 9000 add hello алло привет здравствуйте");


        }

    }

    /**
     * execute command method
     *
     * is connects to server and tries to execute given command
     * and prints given response to console
     */
    private void executeCommandOnServer() {


            try(
                    Socket clientSocket     = new Socket(serverIP, serverPort);
                    ObjectOutputStream out  = new ObjectOutputStream(clientSocket.getOutputStream());

                ) {

                clientSocket.setSoTimeout(SOCKET_TIMEOUT);

                out.writeObject(command.getCommandArray());
                out.flush();

                System.out.println("Команда послана");

                ObjectInputStream in  = new ObjectInputStream(clientSocket.getInputStream());
                List<Object> response = (List<Object>) in.readObject();
                for (Object s : response) {
                    System.out.println(String.format("<%s>%n", s.toString()));
                }



            } catch (SocketException e) {
                System.out.println("Ошибка соединения!");
                e.printStackTrace();
            }
            catch (SocketTimeoutException e) {
                System.out.println("Превышено время ожидания!");
                e.printStackTrace();
            }
            catch (UnknownHostException e) {
                System.out.println("Указан некорректный хост!");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Не распознан ответ от сервера");
                e.printStackTrace();
            }

    }








}

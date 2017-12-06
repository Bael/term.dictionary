package io.github.bael.dictionary.client;


import io.github.bael.dictionary.DictionaryCommand;
import io.github.bael.dictionary.DictionaryCommandResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * This is a dictionary client. It can add, get, or delete term definitions of Dictionary.
 */
class Client {

    // waiting no more 20s
    private static int SOCKET_TIMEOUT = 20000;
    private final String serverIP;
    private final int serverPort;
    private final DictionaryCommand command;

    public Client(String[] args) throws IllegalArgumentException {

        if (args.length < 4) {
            throw new IllegalArgumentException("Client arguments are not specified!");
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

            System.out.format("Please set valid args:%n 1) %s %n 2) %s %n 3) %s %n 4) %s %n 5) %s %n %s %n",
                    "Server IP",
                    "Server port",
                    "Command name, valid values are: add, get, remove",
                    "Term you want to process by command",
                    "Term definitions (in case of add command)",
                    "Example of valid args: java -jar client.jar 192.168.0.1 9000 add hello aloha hi");

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

                out.writeObject(command);
                out.flush();




                try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {
                    DictionaryCommandResponse response = (DictionaryCommandResponse) in.readObject();
                    response.getResponse().forEach(System.out::println);

                }




            }
            catch (ClassCastException e) {
                System.out.println("Transport Class Error");
                e.printStackTrace();
            }
            catch (SocketException e) {
                System.out.println("IO error!");
                e.printStackTrace();
            }
            catch (SocketTimeoutException e) {
                System.out.println("Socket timeout occurred!");
                e.printStackTrace();
            }
            catch (UnknownHostException e) {
                System.out.println("Wrong host specified!");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println("Sever response not recognized:");
                e.printStackTrace();
            }

    }








}

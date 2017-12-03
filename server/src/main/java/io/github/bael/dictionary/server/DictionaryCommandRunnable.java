package io.github.bael.dictionary.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DictionaryCommandRunnable implements Runnable {

    private final WordDictionary dictionary;
    private final Socket clientSocket;

    public DictionaryCommandRunnable(final Socket clientSocket, final WordDictionary dictionary) {

        this.dictionary = dictionary;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        log("Получили соединение " + clientSocket);

        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            List<String> dictionaryCommand;
            dictionaryCommand = (List<String>) in.readObject();
            System.out.println("Прочитали команду " + dictionaryCommand);

            String commandType = dictionaryCommand.get(0);
            String term = dictionaryCommand.get(1);
            HashSet<String> values = new HashSet<>();
            values.addAll(dictionaryCommand.subList(2, dictionaryCommand.size()));

            log(commandType);
            log(term);
            log(values.toString());

            List<Object> result = new ArrayList<>();

            switch (commandType) {
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


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if(clientSocket != null && !clientSocket.isClosed())
            {
                try {
                    clientSocket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private void log(String commandStr) {
        System.out.println(commandStr);
    }
}

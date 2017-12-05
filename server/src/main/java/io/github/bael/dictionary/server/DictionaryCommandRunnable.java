package io.github.bael.dictionary.server;

import io.github.bael.dictionary.DictionaryCommand;
import io.github.bael.dictionary.DictionaryCommandResponse;
import io.github.bael.dictionary.termdictionary.TermDictionary;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DictionaryCommandRunnable implements Runnable {

    private final TermDictionary dictionary;
    private final Socket clientSocket;

    DictionaryCommandRunnable(final Socket clientSocket, final TermDictionary dictionary) {

        this.dictionary = dictionary;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        log("Получили соединение " + clientSocket);

        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {


            DictionaryCommand dictionaryCommand = (DictionaryCommand) in.readObject();

            System.out.println("Прочитали команду " + dictionaryCommand);

            DictionaryCommandResponse result = applyCommand(dictionaryCommand);

            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(result);
            out.flush();
            log("ответили ");

            out.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

    /**
     * Applies termdictionary command to termdictionary
     * @param dictionaryCommand
     * @return
     */
    private DictionaryCommandResponse applyCommand(DictionaryCommand dictionaryCommand) {

        List<String> result = new ArrayList<>();

        String term = dictionaryCommand.getTerm();


        switch (dictionaryCommand.getCommand()) {
            case "add":
                dictionary.addDefinitions(term, dictionaryCommand.getDefinitions());
                result.add(DictionaryCommandResponse.DEFINITIONS_ARE_ADDED);
                break;

            case "get":
                Set<String> founded = dictionary.getDefinitions(term);
                if (founded != null) {
                    result.addAll(founded);
                } else result.add(DictionaryCommandResponse.DEFINITIONS_ARE_NOT_FOUNDED);
                break;

            case "remove":
                if (dictionary.removeTerm(term)) {
                    result.add(DictionaryCommandResponse.TERM_IS_DELETED);
                } else {
                    result.add(DictionaryCommandResponse.TERM_IS_NOT_FOUNDED);
                }

                break;
            default:
                result.add(DictionaryCommandResponse.UNKNOWN_COMMAND);
        }

        return new DictionaryCommandResponse(result);

    }

    private void log(String commandStr) {
        System.out.println(commandStr);
    }
}

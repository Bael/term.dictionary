package io.github.bael.dictionary.server;

import io.github.bael.dictionary.DictionaryCommand;
import io.github.bael.dictionary.DictionaryCommandResponse;
import io.github.bael.dictionary.termdictionary.ConcurrentDictionary;
import io.github.bael.dictionary.termdictionary.TermDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DictionaryCommandRunnable implements Runnable {

    private final TermDictionary dictionary;
    private final Socket clientSocket;
    final Logger logger = LoggerFactory.getLogger(ConcurrentDictionary.class);

    DictionaryCommandRunnable(final Socket clientSocket, final TermDictionary dictionary) {

        this.dictionary = dictionary;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        logger.info("Got a connection to client socket {}", clientSocket);

        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

            logger.info("Start Reading a dictionary command ");
            DictionaryCommand dictionaryCommand = (DictionaryCommand) in.readObject();
            logger.info("Command:'{}' term:'{}'", dictionaryCommand.getCommand(), dictionaryCommand.getTerm());

            logger.info("Starting apply command");
            DictionaryCommandResponse result = applyCommand(dictionaryCommand);
            logger.info("Result:{}", Arrays.toString(result.getResponse().toArray()));

            logger.info("Starting reply to client");
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject(result);
            out.flush();
            logger.info("Done");

        } catch (ClassNotFoundException e) {
            logger.error("Transport class error {}", e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IO Error {}", e);
            e.printStackTrace();
        } finally {
            if (clientSocket != null && !clientSocket.isClosed()) {
                try {
                    logger.debug("Closing client socket");
                    clientSocket.close();
                    logger.debug("Socket closed");

                } catch (IOException e) {
                    logger.error("Error occurred while closing client socket {}", e);
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     * Recognize command type and apply it to the dictionary.
     * This method can recognize add, get and remove action. For such commands we return the result from dictionary.
     * If command is not recognised we complain on unknown command type
     * and return result
     *
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
                logger.error("Unknown command from client {}", dictionaryCommand.getCommand());
                result.add(DictionaryCommandResponse.UNKNOWN_COMMAND);
        }

        return new DictionaryCommandResponse(result);

    }


}

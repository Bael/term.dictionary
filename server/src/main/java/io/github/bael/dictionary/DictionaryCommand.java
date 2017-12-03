package io.github.bael.dictionary;

import io.github.bael.dictionary.server.WordDictionary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Command for server
 */
public class DictionaryCommand implements Serializable {
    public String getCommand() {
        return command;
    }

    public String getTerm() {
        return term;
    }

    public List<String> getDefinitions() {
        return definitions;
    }

    private final String command;
    private final String term;
    private final List<String> definitions;


    /*
    public List<String> apply(WordDictionary dictionary) {


    }*/

    public DictionaryCommand(List<String> params) throws IllegalArgumentException {

        if (params == null || params.size() < 2) {
            throw new IllegalArgumentException("Не поданы  параметры команды! " + params);
        }

        command = params.get(0).toLowerCase();
        String[] validCommands = {"add", "remove", "get"};
        if (!Arrays.asList(validCommands).contains(command)) {
            throw new IllegalArgumentException("Поданная команда некорректна " + command);
        }

        term = params.get(1);
        if (term == null || term.isEmpty()) {
            throw new IllegalArgumentException("Термин не определен:" + term);
        }


        definitions = new ArrayList<>();

        for (String s : params.subList(2, params.size())) {
            if (s != null && !s.isEmpty()) {
                definitions.add(s);
            }
        }




    }
}

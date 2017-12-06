package io.github.bael.dictionary;

import java.io.Serializable;
import java.util.*;

/**
 * Command for server. Transport class
 */
public class DictionaryCommand implements Serializable {
    public String getCommand() {
        return command;
    }

    public String getTerm() {
        return term;
    }

    public Set<String> getDefinitions() {
        return definitions;
    }

    private final String command;
    private final String term;
    private final Set<String> definitions;


    public DictionaryCommand(List<String> params) throws IllegalArgumentException  {

        if (params == null || params.size() < 2) {
            throw new IllegalArgumentException("Command args are not given! " + params);
        }

        command = params.get(0).toLowerCase();
        String[] validCommands = {"add", "remove", "get"};
        if (!Arrays.asList(validCommands).contains(command)) {
            throw new IllegalArgumentException("Wrong command type:" + command);
        }

        term = params.get(1);
        if (term == null || term.isEmpty()) {
            throw new IllegalArgumentException("Term is not set or null:" + term);
        }


        definitions = new HashSet<>();

        for (String s : params.subList(2, params.size())) {
            if (s != null && !s.isEmpty()) {
                definitions.add(s);
            }
        }


    }
}

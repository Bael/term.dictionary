package io.github.bael.dictionary;

import java.io.Serializable;
import java.util.List;

public class DictionaryCommandResponse implements Serializable {
    public DictionaryCommandResponse(List<String> response) {
        this.response = response;

    }

    public static final String DEFINITIONS_ARE_ADDED = "<Definitions are added>";
    public static final String DEFINITIONS_ARE_NOT_FOUNDED = "<Definitions are not founded>";
    public static final String TERM_IS_DELETED = "<Term is deleted>";
    public static final String TERM_IS_NOT_FOUNDED = "<Term is not founded>";
    public static final String UNKNOWN_COMMAND = "<Unknown command>";

    private final List<String> response;

    public List<String> getResponse() {
        return response;
    }



}

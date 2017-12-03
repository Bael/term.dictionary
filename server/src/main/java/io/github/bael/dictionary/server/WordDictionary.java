package io.github.bael.dictionary.server;

import java.util.List;
import java.util.Set;

public interface WordDictionary {

    static WordDictionary createDictionary() {
        return new SimpleDictionary();
    }

    String addDefinitions(String term, Set<String> definitions);

    Set<String> getDefinitions(String term);

    String removeTerm(String term);


}

package io.github.bael.dictionary.server;

import java.util.Set;

public interface WordDictionary {

    static WordDictionary createSingleThreadDictionary() {
        return new SimpleDictionary();
    }

    static WordDictionary createMultiThreadDictionary() {
        return new ConcurrentDictionary();
    }

    String addDefinitions(String term, Set<String> definitions);

    Set<String> getDefinitions(String term);

    String removeTerm(String term);


}

package io.github.bael.dictionary.termdictionary;

import io.github.bael.dictionary.server.Server;

public class DictionaryFactory {
    public static TermDictionary createSingleThreadDictionary() {
        return new SimpleDictionary();
    }

    public static TermDictionary createMultiThreadDictionary(int threadsCount) {
        return new ConcurrentDictionary(threadsCount);
    }



}

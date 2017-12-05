package io.github.bael.dictionary.termdictionary;

public class DictionaryFactory {
    public static TermDictionary createSingleThreadDictionary() {
        return new SimpleDictionary();
    }

    public static TermDictionary createMultiThreadDictionary() {
        return new ConcurrentDictionary();
    }

}

package io.github.bael.dictionary.server;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentDictionary implements WordDictionary {

    private final ConcurrentMap<String, ConcurrentSkipListSet<String>> dictionary;
    public ConcurrentDictionary() {
        dictionary = new ConcurrentHashMap<>();

    }

    @Override
    public String addDefinitions(String term, Set<String> definitions) {

        ConcurrentSkipListSet<String> set = dictionary.getOrDefault(term, new ConcurrentSkipListSet<>());
        set.addAll(definitions);
        dictionary.put(term, set);

        return "Данные добавлены";
    }

    @Override
    public Set<String> getDefinitions(String term) {
        return dictionary.getOrDefault(term, new ConcurrentSkipListSet<>());
    }

    @Override
    public String removeTerm(String term) {

        if (dictionary.remove(term) == null) {
            return "Такого слова нет в словаре";
        } else {
            return "Удалено слово";
        }
    }
}

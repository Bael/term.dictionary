package io.github.bael.dictionary.server;

import java.util.*;

public class SimpleDictionary implements WordDictionary {

    private HashMap<String, Set<String>> dictionary;


    @Override
    public String addDefinitions(String term, Set<String> definitions) {
        //Set<String> termInDictionary = dictionary.getOrDefault(term, new HashSet<>());
        //termInDictionary.addAll(definitions);
        dictionary.putIfAbsent(term, definitions);
        return "Успешно добавлено";
    }



    @Override
    public Set<String> getDefinitions(String term) {
        return dictionary.getOrDefault(term, new HashSet<>());
    }

    @Override
    public String removeTerm(String term) {
        if (getDefinitions(term) == null) {
            return "Слова нет в словаре";
        } else {
            dictionary.remove(term);
            return "Слово удалено";
        }


    }


    public SimpleDictionary() {
        this.dictionary = new HashMap<>();
    }
}

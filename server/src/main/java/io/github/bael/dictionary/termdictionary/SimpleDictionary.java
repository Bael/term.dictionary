package io.github.bael.dictionary.termdictionary;

import java.util.*;

public class SimpleDictionary implements TermDictionary {

    private HashMap<String, Set<String>> dictionary;


    @Override
    public void addDefinitions(String term, Set<String> definitions) {
        //Set<String> termInDictionary = termdictionary.getOrDefault(term, new HashSet<>());
        //termInDictionary.addAll(definitions);
        dictionary.putIfAbsent(term, definitions);
    }



    @Override
    public Set<String> getDefinitions(String term) {
        return dictionary.get(term);
    }

    @Override
    public boolean removeTerm(String term) {
        if (getDefinitions(term) == null) {
            return false;
        } else {
            dictionary.remove(term);
            return true;
        }


    }


    public SimpleDictionary() {
        this.dictionary = new HashMap<>();
    }
}

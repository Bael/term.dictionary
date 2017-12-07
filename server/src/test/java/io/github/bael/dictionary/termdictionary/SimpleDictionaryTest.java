package io.github.bael.dictionary.termdictionary;

import io.github.bael.dictionary.termdictionary.SimpleDictionary;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class SimpleDictionaryTest {


    @Test
    public void addDefinitions() {
        SimpleDictionary dict = fillDictionary();
        assertEquals(4, dict.getDefinitions("good").size());
    }


    @Test
    public void removeTerm() {

        SimpleDictionary dict = fillDictionary();
        dict.removeTerm("good");
        assertEquals(null, dict.getDefinitions("good"));
    }

    private SimpleDictionary fillDictionary() {
        SimpleDictionary dict = new SimpleDictionary();

        String term = "good";

        HashSet<String> defs = new HashSet<>();
        defs.add("годный");
        defs.add("восхитительный");
        defs.add("чудесный");
        dict.addDefinitions(term, defs);

        defs.add("прекрасный");

        dict.addDefinitions(term, defs);


        return dict;




    }
}
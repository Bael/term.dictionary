package io.github.bael.dictionary.server;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ConcurrentDictionaryTest {

    @Test
    public void addDefinitions() throws InterruptedException {
        final WordDictionary dictionary = WordDictionary.createMultiThreadDictionary();

        Set<String> words = new HashSet<>();
        words.add("marvelous");


        final String term = "good";
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                words.add(String.valueOf(finalI));
                dictionary.addDefinitions(term, words);
                            });
            thread.start();
            thread.join();
        }


        String[] expected = {};
        expected = words.toArray(expected);

        Arrays.sort(expected);

        String[] actual = {};
        actual = dictionary.getDefinitions(term).toArray(actual);

        Arrays.sort(actual);


        assertArrayEquals(expected, actual);
        
    }

    @Test
    public void getDefinitions() {
    }

    @Test
    public void removeTerm() {
    }
}
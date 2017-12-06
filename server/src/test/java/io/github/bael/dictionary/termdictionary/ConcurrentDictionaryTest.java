package io.github.bael.dictionary.termdictionary;

import org.junit.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ConcurrentDictionaryTest {

    @Test
    public void addAndGetDefinitions() {

        final TermDictionary dictionary = DictionaryFactory.createMultiThreadDictionary();

        Set<String> words = new ConcurrentSkipListSet<>();
        words.add("marvelous");

        final String term = "good";
        fillDictionaryForTerm(term, words, dictionary);

        String[] expected = {};
        // type casting
        expected = words.toArray(expected);

        Arrays.sort(expected);

        String[] actual = {};
        actual = dictionary.getDefinitions(term).toArray(actual);

        Arrays.sort(actual);

        assertArrayEquals(expected, actual);
        
    }

    @Test
    public void removeTerm() {

        final TermDictionary dictionary = DictionaryFactory.createMultiThreadDictionary();

        Set<String> words = new ConcurrentSkipListSet<>();
        words.add("marvelous");

        final String term = "pretty";
        fillDictionaryForTerm(term, words, dictionary);

        dictionary.removeTerm(term);

        assertEquals(null, dictionary.getDefinitions(term));
    }


    /**
     * fills dictionary for given term
     * @param term
     * @param words
     * @param dictionary
     */
    private void fillDictionaryForTerm(String term, Set<String> words, final TermDictionary dictionary) {

        for (int i = 1; i <= 100; i++) {

            words.add(String.valueOf(i));
            int finalI = i;
            Thread thread = new Thread(() -> {

                final String def = "marvelous"
                        + String.valueOf(finalI)
                        + String.valueOf(Thread.currentThread().getId());
                dictionary.addDefinitions(
                        term,
                        words);

                words.add(def);
                dictionary.addDefinitions(term, words);
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
package io.github.bael.dictionary.server;

import io.github.bael.dictionary.termdictionary.DictionaryFactory;
import io.github.bael.dictionary.termdictionary.TermDictionary;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.*;

public class ConcurrentDictionaryTest {

    @Test
    public void addDefinitions() throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        final TermDictionary dictionary = DictionaryFactory.createMultiThreadDictionary();
        lock.unlock();
        System.out.println(dictionary);

        Set<String> words = new ConcurrentSkipListSet<>();
        words.add("marvelous");


        final String term = "good";
        for (int i = 1; i <= 100; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {

                /*final String def = "marvelous"
                                        + String.valueOf(finalI)
                                        + String.valueOf(Thread.currentThread().getId());

                */
                words.add(String.valueOf(finalI));
                //System.out.println(term);
//                System.out.println(words);
                dictionary.addDefinitions(
                        term,
                        words);

                //words.add(def);
                //dictionary.addDefinitions(term, words);
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
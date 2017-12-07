package io.github.bael.dictionary.termdictionary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentDictionary implements TermDictionary {

    final Logger logger = LoggerFactory.getLogger(ConcurrentDictionary.class);

    private final ConcurrentMap<String, ConcurrentSkipListSet<String>> dictionary;
    ConcurrentDictionary(int threadCount) {
        int initialCapacity = 1000;
        float loadFactor = 0.75f;
        logger.info("Creating dictionary with initial capacity {} load factor {} and concurrency level {}", initialCapacity, loadFactor, threadCount);

        dictionary = new ConcurrentHashMap<>(initialCapacity, loadFactor, threadCount);
    }

    @Override
    public void addDefinitions(String term, Set<String> definitions) {
        logger.debug("Added definitions called with {} term and definitions {}", term, definitions);

        dictionary.putIfAbsent(term, new ConcurrentSkipListSet<>());
        ConcurrentSkipListSet<String> set = dictionary.get(term);
        set.addAll(definitions);

        logger.debug("Current definitions are {} ", set);

    }

    @Override
    public Set<String> getDefinitions(String term) {
        logger.debug("get Definitions called with {}", term);
        return dictionary.get(term);
    }

    @Override
    public boolean removeTerm(String term) {

        logger.debug("remove term called for {}", term);

        if (dictionary.remove(term) == null) {
            logger.debug("such term does not exists in Dictionary");

            return false;
        } else {
            logger.debug("term is successfully removed");

            return true;
        }
    }
}

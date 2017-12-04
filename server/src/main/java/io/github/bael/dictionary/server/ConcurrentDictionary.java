package io.github.bael.dictionary.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentDictionary implements WordDictionary {

//    final Logger logger = LoggerFactory.getLogger(ConcurrentDictionary.class);

    private final ConcurrentMap<String, ConcurrentSkipListSet<String>> dictionary;
    ConcurrentDictionary() {
        dictionary = new ConcurrentHashMap<>();

    }

    @Override
    public String addDefinitions(String term, Set<String> definitions) {

//        logger.debug("Added definitions called with {} term and defintions {}", term, definitions);

        ConcurrentSkipListSet<String> set = dictionary.getOrDefault(term, new ConcurrentSkipListSet<>());

        synchronized (set) {
            set.addAll(definitions);
            dictionary.put(term, set);

//        logger.debug("Current definitions are {} ", set);
        }

        return "Данные добавлены";
    }

    @Override
    public Set<String> getDefinitions(String term) {

//        logger.debug("get Definitions called with {}", term);

        return dictionary.getOrDefault(term, new ConcurrentSkipListSet<>());
    }

    @Override
    public String removeTerm(String term) {

//        logger.debug("remove term called for {}", term);

        if (dictionary.remove(term) == null) {

//            logger.debug("such term does'nt exists in dictionary");

            return "Такого слова нет в словаре";
        } else {

//            logger.debug("term is removed");

            return "Удалено слово";
        }
    }
}

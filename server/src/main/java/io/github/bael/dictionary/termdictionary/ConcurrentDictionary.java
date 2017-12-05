package io.github.bael.dictionary.termdictionary;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class ConcurrentDictionary implements TermDictionary {

//    final Logger logger = LoggerFactory.getLogger(ConcurrentDictionary.class);

    private final ConcurrentMap<String, ConcurrentSkipListSet<String>> dictionary;
    ConcurrentDictionary() {
        dictionary = new ConcurrentHashMap<>();

    }

    @Override
    public void addDefinitions(String term, Set<String> definitions) {

//        logger.debug("Added definitions called with {} term and defintions {}", term, definitions);
        ConcurrentSkipListSet<String> set = dictionary.putIfAbsent(term, new ConcurrentSkipListSet<>());
        set.addAll(definitions);


    }

    @Override
    public Set<String> getDefinitions(String term) {

//        logger.debug("get Definitions called with {}", term);

        return dictionary.get(term);
    }

    @Override
    public boolean removeTerm(String term) {

//        logger.debug("remove term called for {}", term);

        if (dictionary.remove(term) == null) {

//            logger.debug("such term does'nt exists in termdictionary");

            return false;
        } else {

//            logger.debug("term is removed");

            return true;
        }
    }
}

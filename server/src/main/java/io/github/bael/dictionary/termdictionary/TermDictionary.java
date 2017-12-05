package io.github.bael.dictionary.termdictionary;

import java.util.Set;

/**
 * Dictionary of terms interface. Supports addition, getting and removing definitions of term.
 *
 */
public interface TermDictionary {



    /**
     * Add definitions to given term.
     * @param term - must not be null
     * @param definitions - set of definitions to add, duplicates are not saved.
     */
    void addDefinitions(String term, Set<String> definitions);


    /**
     * Get definitions of given term
     * @param term - key to search, must not be null
     * @return founded definitions, if term not added in dictionary - then null, else set of definitions.
     */
    Set<String> getDefinitions(String term);

    /**
     * Removes term from dictionary
     * @param term to remove
     * @return if term was in dictionary then return true, false otherwise
     */
    boolean removeTerm(String term);


}

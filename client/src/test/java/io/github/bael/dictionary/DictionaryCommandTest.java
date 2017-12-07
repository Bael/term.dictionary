package io.github.bael.dictionary;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DictionaryCommandTest {


    @Test(expected = IllegalArgumentException.class)
    public void testCommandWithEmptyParams() {
        List<String> list = new ArrayList<>();
        DictionaryCommand command = new DictionaryCommand(list);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCommandWithWrongCommandType() {

        List<String> list = new ArrayList<>();
        list.add("adddd");
        list.add("hello");
        list.add("howareyou");


        DictionaryCommand command = new DictionaryCommand(list);
    }

    @Test
    public void testValidCommand() {

        List<String> list = new ArrayList<>();
        list.add("add");
        list.add("hello");
        list.add("привет");
        list.add("дратути");

        DictionaryCommand command = new DictionaryCommand(list);

        assertEquals("add", command.getCommand());
        assertEquals("hello", command.getTerm());
        assertArrayEquals(list.subList(2, list.size()).toArray(), command.getDefinitions().toArray());

    }



}
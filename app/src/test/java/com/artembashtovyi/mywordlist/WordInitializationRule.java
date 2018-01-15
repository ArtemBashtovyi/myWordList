package com.artembashtovyi.mywordlist;

import com.artembashtovyi.mywordlist.data.model.Word;

import org.junit.Before;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felix on 1/6/18
 */

public class WordInitializationRule implements TestRule{

    @Override
    public Statement apply(Statement base, Description description) {
       return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                base.evaluate();
            }
        };

    }

    public List<Word> getTestWords() {

        List<Word> words = new ArrayList<>();
        words.add(new Word(1,"thus","таким чином"));
        words.add(new Word(2,"availability","наявність"));
        words.add(new Word(3,"arrangement","організація"));
        words.add(new Word(4,"afterwards","пізніше"));
        words.add(new Word(5,"additionally","додатково"));

        return words;
    }

    public List<Word> getFavoriteTestWords() {

        List<Word> words = new ArrayList<>();
        words.add(new Word(2,"availability","наявність"));
        words.add(new Word(3,"arrangement","організація"));
        words.add(new Word(5,"additionally","додатково"));

        return words;
    }

}

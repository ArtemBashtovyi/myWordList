package com.artembashtovyi.mywordlist;

import com.artembashtovyi.mywordlist.data.model.Word;

import org.junit.Assume;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void word_isEquals() {
        Word word = new Word(1,"s","a");
        Word word2 = word;


      //  assertSame("equal words ",word,word2);
    }

    @Test
    public void testOnething() {
        System.out.println("This method is ignored because its not ready yet");
        Assume.assumeFalse(System.getProperty("os.name").contains("Linux"));
    }
}
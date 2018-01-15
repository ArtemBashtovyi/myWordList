package com.artembashtovyi.mywordlist;

import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.DbHelper;
import com.artembashtovyi.mywordlist.data.sqlite.query.AllWordsQuery;
import com.artembashtovyi.mywordlist.data.sqlite.query.RecentWordsQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by felix on 1/5/18
 *
 * */

@RunWith(AndroidJUnit4.class)
public class DbHelperTest {

    private final static String WORDS_TABLE_NAME = DbHelper.Words.TABLE_WORDS;
    private final static String FAVORITE_TABLE_NAME = DbHelper.FavoriteWords.TABLE_FAVORITE_WORDS;

    private DbHelper dbHelper;

    @Before
    public void initDbHelper() {
        getTargetContext().deleteDatabase(DbHelper.getInstance(getTargetContext()).getDatabaseName());

        dbHelper = DbHelper.getInstance(getTargetContext());

        dbHelper.truncate(WORDS_TABLE_NAME);
        dbHelper.truncate(FAVORITE_TABLE_NAME);

    }

    @After
    public void cleanResources() {
        dbHelper.close();
    }

    @Test
    public void insertSingleWordTest() {
        dbHelper.addWord(new Word(1,"thus","таким чином"),WORDS_TABLE_NAME);

        List<Word> result = dbHelper.getWords(new AllWordsQuery(),WORDS_TABLE_NAME);

        assertThat("the size result is 1",result.size(),is(1));
        assertEquals(result.get(0).getEngVersion(),"thus");
        assertTrue("word id is 1",result.get(0).getId() == 1);
    }


    @Test
    public void editWordTest() {

        Word newWord = new Word(4,"availability","здатність");
        Word oldWord = new Word(5,"arrangement","організація");

        dbHelper.addWord(oldWord,WORDS_TABLE_NAME);
        dbHelper.editWord(oldWord,newWord);

        List<Word> editWords = dbHelper.getWords(new AllWordsQuery(),WORDS_TABLE_NAME);

        assertThat(editWords.get(0),is(newWord));
        assertTrue(editWords.get(0).getEngVersion().equals("availability"));

    }

    @Test
    public void getFiveRecentlyWordsTest() {
        dbHelper.addWord(new Word(1,"therefore","отже"),WORDS_TABLE_NAME);
        dbHelper.addWord(new Word(2,"through","через"),WORDS_TABLE_NAME);
        dbHelper.addWord(new Word(3,"additionally","додатково"),WORDS_TABLE_NAME);
        dbHelper.addWord(new Word(4,"demand","вимагає"),WORDS_TABLE_NAME);
        dbHelper.addWord(new Word(10,"lead","призвести"),WORDS_TABLE_NAME);


        List<Word> words = dbHelper.getWords(new RecentWordsQuery(5),WORDS_TABLE_NAME);


        assertThat(words.size(),is(5));
        Log.i("TestRecent","words size is 5");

        List<String> engVersions = new ArrayList<>();
        for (Word word : words) {
            engVersions.add(word.getEngVersion());
        }

        // check words in desc order
        assertThat(engVersions,contains("lead","demand","additionally","through","therefore"));
        assertTrue(words.get(words.size()-1).getId() == 1);

    }
}

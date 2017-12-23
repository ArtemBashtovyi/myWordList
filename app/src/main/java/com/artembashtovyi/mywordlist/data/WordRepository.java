package com.artembashtovyi.mywordlist.data;


import android.content.Context;

import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.DbHelper;
import com.artembashtovyi.mywordlist.data.sqlite.query.Query;

import java.util.List;

public class WordRepository  {

    private static WordRepository INSTANCE;
    private DbHelper dbHelper;

    private WordRepository(Context context,DbHelper dbHelper) {
        this.dbHelper = dbHelper;

    }

    public static WordRepository getInstanse(Context context,DbHelper dbHelper) {
        if (INSTANCE == null) {
            INSTANCE = new WordRepository(context.getApplicationContext(),dbHelper);
        }
        return INSTANCE;
    }

    public void addWord(Word word) {
        dbHelper.addWord(word);
    }

    public void deleteWords(List<Word> words) {
        dbHelper.deleteWords(words);
    }

    public List<Word> getWords(Query query) {
        return dbHelper.getWords(query);
    }

    public void editWord(Word oldWord,Word newWord) {
        dbHelper.editWord(oldWord,newWord);
    }

}

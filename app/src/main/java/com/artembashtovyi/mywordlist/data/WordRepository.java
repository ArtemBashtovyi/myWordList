package com.artembashtovyi.mywordlist.data;


import android.os.AsyncTask;

import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.DbHelper;
import com.artembashtovyi.mywordlist.data.sqlite.query.AllWordsQuery;
import com.artembashtovyi.mywordlist.data.sqlite.query.Query;

import java.util.List;

public class WordRepository  {

    private static WordRepository INSTANCE;
    private DbHelper dbHelper;


    private WordRepository(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public static WordRepository getInstance(DbHelper dbHelper) {
        if (INSTANCE == null) {
            INSTANCE = new WordRepository(dbHelper);
        }
        return INSTANCE;
    }

    public void addWord(Word word) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dbHelper.addWord(word,DbHelper.Words.TABLE_WORDS);
                return null;
            }
        }.execute();
    }

    public void deleteWords(List<Word> words) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dbHelper.deleteWords(words);
                return null;
            }
        }.execute();
    }

    public List<Word> getWords(Query query) {
        return dbHelper.getWords(query,DbHelper.Words.TABLE_WORDS);
    }

    public void editWord(Word oldWord,Word newWord) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dbHelper.editWord(oldWord,newWord);
                return null;
            }
        }.execute();
    }

    public void addToFavorites(Word word) {
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dbHelper.addWord(word,DbHelper.FavoriteWords.TABLE_FAVORITE_WORDS);
                return null;
            }
        }.execute();
    }

    public void deleteFromFavorites(Word word){
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dbHelper.deleteWord(word,DbHelper.FavoriteWords.TABLE_FAVORITE_WORDS);
                return null;
            }
        }.execute();
    }


    public List<Word> getFavorites() {
        return dbHelper.getWords(new AllWordsQuery(),DbHelper.FavoriteWords.TABLE_FAVORITE_WORDS);
    }

    public boolean isFavorite(Word word) {
        return dbHelper.isFavorite(word);
    }
}

package com.artembashtovyi.mywordlist.data;


import com.artembashtovyi.mywordlist.data.async.WordCallbacks;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.DbHelper;
import com.artembashtovyi.mywordlist.data.sqlite.query.AllWordsQuery;
import com.artembashtovyi.mywordlist.data.sqlite.query.Query;

import java.util.List;
import java.util.concurrent.Executor;

public class WordRepositoryImpl implements WordRepository {

    private static WordRepositoryImpl INSTANCE;
    private DbHelper dbHelper;
    private Executor diskIOExecutor;
    private Executor mainThreadExecutor;

    private WordRepositoryImpl(Executor mainThreadExecutor,Executor diskIOExecutor,DbHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.diskIOExecutor = diskIOExecutor;
        this.mainThreadExecutor = mainThreadExecutor;
    }

    public static WordRepositoryImpl getInstance(Executor mainThreadExecutor,Executor pool,DbHelper dbHelper) {
        if (INSTANCE == null) {
            INSTANCE = new WordRepositoryImpl(mainThreadExecutor,pool,dbHelper);
        }
        return INSTANCE;
    }

    @Override
    public void addWord(Word word) {
        dbHelper.addWord(word, DbHelper.Words.TABLE_WORDS);
    }

    @Override
    public void deleteWords(List<Word> words) {
        diskIOExecutor.execute(() -> {
            dbHelper.deleteWords(words, DbHelper.Words.TABLE_WORDS);
            dbHelper.deleteWords(words, DbHelper.FavoriteWords.TABLE_FAVORITE_WORDS);
        });
    }

    @Override
    public void getWords(WordCallbacks.AllWordsCallback callback, Query query) {
        diskIOExecutor.execute(() -> {
            List<Word> result = dbHelper.getWords(query, DbHelper.Words.TABLE_WORDS);
            // callback in MainThread
            mainThreadExecutor.execute(() -> callback.allWordsReceive(result));
        });
    }

    @Override
    public void editWord(Word oldWord, Word newWord) {
        diskIOExecutor.execute(() -> dbHelper.editWord(oldWord, newWord));
    }

    @Override
    public void addToFavorites(Word word) {
        diskIOExecutor.execute( () -> dbHelper.addWord(word, DbHelper.FavoriteWords.TABLE_FAVORITE_WORDS));
    }

    @Override
    public void deleteFromFavorites(Word word) {
        diskIOExecutor.execute( () -> dbHelper.deleteWord(word, DbHelper.FavoriteWords.TABLE_FAVORITE_WORDS));
    }

    @Override
    public void getFavorites(WordCallbacks.FavoriteWordsCallback callback) {
        diskIOExecutor.execute(() -> {
            List<Word> result = dbHelper.getWords(new AllWordsQuery(), DbHelper.FavoriteWords.TABLE_FAVORITE_WORDS);
            mainThreadExecutor.execute(() -> callback.favoriteWordsReceive(result));
        });
    }

    @Override
    public void isFavorite(WordCallbacks.CheckWordCallback callback,Word word) {
        diskIOExecutor.execute( () -> {
            final boolean isFavorite = dbHelper.isFavorite(word);
            mainThreadExecutor.execute(() -> callback.checkFavorite(isFavorite));
        });
    }


}

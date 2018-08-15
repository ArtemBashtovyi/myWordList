package com.artembashtovyi.mywordlist.data;


import com.artembashtovyi.mywordlist.data.async.WordCallbacks;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.DbHelper;
import com.artembashtovyi.mywordlist.data.sqlite.query.AllWordsQuery;
import com.artembashtovyi.mywordlist.data.sqlite.query.Query;
import com.artembashtovyi.mywordlist.ui.dialog.description.WordState;

import java.util.ArrayList;
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
        diskIOExecutor.execute( () -> dbHelper.addWord(word));
    }

    @Override
    public void deleteWord(Word word) {
        diskIOExecutor.execute( () -> dbHelper.deleteWord(word));
    }

    @Override
    public void deleteWords(List<Word> words) {
        diskIOExecutor.execute(() -> dbHelper.deleteWords(words, DbHelper.Tables.MAIN_TABLE_NAME));
    }

    @Override
    public void getWords(WordCallbacks.AllWordsCallback callback, Query query) {
        diskIOExecutor.execute(() -> {
            List<Word> result = dbHelper.getWords(query);
            // callback in MainThread
            mainThreadExecutor.execute(() -> callback.allWordsReceive(result));
        });
    }


    @Override
    public void getRedWords(WordCallbacks.AllWordsCallback callback) {
        diskIOExecutor.execute(() -> {
            List<Word> result = dbHelper.getWords(WordState.RED);
            mainThreadExecutor.execute(() -> callback.allWordsReceive(result));
        });
    }

    @Override
    public void getYellowWords(WordCallbacks.AllWordsCallback callback) {
        diskIOExecutor.execute(() -> {
            List<Word> result = dbHelper.getWords(WordState.YELLOW);
            mainThreadExecutor.execute(() -> callback.allWordsReceive(result));
        });
    }

    @Override
    public void getGreenWords(WordCallbacks.AllWordsCallback callback) {
        diskIOExecutor.execute(() -> {
            List<Word> result = dbHelper.getWords(WordState.GREEN);
            mainThreadExecutor.execute(() -> callback.allWordsReceive(result));
        });
    }

    @Override
    public void getFavoriteWords(WordCallbacks.AllWordsCallback callback) {
        diskIOExecutor.execute(() -> {

            // FIXME : stupid logic,need streams or Kotlin
            List<Word> result = dbHelper.getWords(new AllWordsQuery());
            List<Word> favoriteWords = new ArrayList<>();

            for (Word word : result) {
                if (word.isFavorite()) {
                    favoriteWords.add(word);
                }
            }

            mainThreadExecutor.execute(() -> callback.allWordsReceive(favoriteWords));
        });
    }


    @Override
    public void editWord(Word oldWord, Word newWord) {
        diskIOExecutor.execute(() -> dbHelper.editWord(oldWord, newWord));
    }

    @Override
    public void isFavorite(WordCallbacks.CheckWordCallback callback, Word word) {
        diskIOExecutor.execute(() -> {
            boolean isExist = dbHelper.isFavorite(word);
            mainThreadExecutor.execute(() -> callback.checkExistence(isExist));
        });
    }

    @Override
    public void setFavorite(Word word, boolean isFavorite) {
        diskIOExecutor.execute(() -> dbHelper.setFavorite(word,isFavorite));
    }

    @Override
    public void getWordState(WordCallbacks.CheckWordStateCallback callback, Word word) {
        diskIOExecutor.execute(() -> {
            WordState wordState = dbHelper.getWordState(word);
            mainThreadExecutor.execute(() -> callback.checkWordState(wordState));
        });
    }

    @Override
    public void setWordState(Word word, WordState wordState) {
        diskIOExecutor.execute(() -> dbHelper.setWordState(word,wordState));
    }
}

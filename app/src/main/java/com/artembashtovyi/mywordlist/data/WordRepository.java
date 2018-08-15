package com.artembashtovyi.mywordlist.data;

import com.artembashtovyi.mywordlist.data.async.WordCallbacks;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.query.Query;
import com.artembashtovyi.mywordlist.ui.dialog.description.WordState;

import java.util.List;

/**
 * Created by felix on 5/12/18
 */

public interface WordRepository {

    void addWord(Word word);

    void deleteWord(Word word);

    void deleteWords(List<Word> words);

    void getWords(WordCallbacks.AllWordsCallback callback,Query query);

    void getRedWords(WordCallbacks.AllWordsCallback callback);

    void getYellowWords(WordCallbacks.AllWordsCallback callback);

    void getGreenWords(WordCallbacks.AllWordsCallback callback);

    void getFavoriteWords(WordCallbacks.AllWordsCallback callback);

    void editWord(Word oldWord, Word newWord);

    void isFavorite(WordCallbacks.CheckWordCallback callback,Word word);

    void setFavorite(Word word,boolean isFavorite);

    void getWordState(WordCallbacks.CheckWordStateCallback callback,Word word);

    void setWordState(Word word, WordState wordState);
}

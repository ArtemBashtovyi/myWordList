package com.artembashtovyi.mywordlist.data;

import com.artembashtovyi.mywordlist.data.async.WordCallbacks;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.query.Query;

import java.util.List;

/**
 * Created by felix on 5/12/18
 */

interface WordRepository {
    void addWord(Word word);

    void deleteWords(List<Word> words);

    void getWords(WordCallbacks.AllWordsCallback callback,Query query);

    void editWord(Word oldWord, Word newWord);

    void addToFavorites(Word word);

    void deleteFromFavorites(Word word);

    void getFavorites(WordCallbacks.FavoriteWordsCallback callback);

    void isFavorite(WordCallbacks.CheckWordCallback callback,Word word);
}

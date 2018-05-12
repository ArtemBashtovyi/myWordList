package com.artembashtovyi.mywordlist.data.async;

import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.List;

/**
 * Created by felix on 5/10/18
 */

public interface WordCallbacks {

    interface AllWordsCallback {
        void allWordsReceive(List<Word> words);
    }

    interface FavoriteWordsCallback {
        void favoriteWordsReceive(List<Word> words);
    }

    interface CheckWordCallback {
        void checkFavorite(boolean isFavorite);
    }
}

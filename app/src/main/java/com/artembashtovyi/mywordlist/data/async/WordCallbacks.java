package com.artembashtovyi.mywordlist.data.async;

import android.support.annotation.Nullable;

import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.dialog.description.WordState;

import java.util.List;

/**
 * Created by felix on 5/10/18
 */

public interface WordCallbacks {

    interface AllWordsCallback {
        void allWordsReceive(List<Word> words);
    }

    interface CheckWordCallback {
        void checkExistence(boolean isFavorite);
    }

    interface CheckWordStateCallback {
        void checkWordState(@Nullable WordState wordState);
    }
}

package com.artembashtovyi.mywordlist.ui.dialog.description;

import com.artembashtovyi.mywordlist.data.model.Word;

/**
 * Created by felix on 5/21/18
 */

public interface DescriptionDialogContract {

    void showDescriptionDialog(Word word, boolean isFavorite, WordState wordState);

    void updateDialogFavoriteImage(boolean isFavorite);

    void updateDialogColorImage(WordState state);
}

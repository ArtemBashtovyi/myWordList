package com.artembashtovyi.mywordlist.ui.favorites;

import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.List;

/**
 * Created by felix on 12/26/17
 */

public interface FavoritesView  {

    void showLoading();
    void hideLoading();

    void showFavorites(List<Word> favorites);
}

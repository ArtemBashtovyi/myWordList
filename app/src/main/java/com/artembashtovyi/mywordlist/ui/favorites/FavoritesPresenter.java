package com.artembashtovyi.mywordlist.ui.favorites;


import android.util.Log;

import com.artembashtovyi.mywordlist.Presenter;
import com.artembashtovyi.mywordlist.data.WordRepositoryImpl;
import com.artembashtovyi.mywordlist.data.async.WordCallbacks;
import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.List;

public class FavoritesPresenter implements Presenter<FavoritesView> {

    private FavoritesView view;
    private WordRepositoryImpl wordRepository;
    private List<Word> words;


    public FavoritesPresenter(FavoritesView view, WordRepositoryImpl wordRepository) {
        this.view = view;
        this.wordRepository = wordRepository;
    }

    public void loadFavorites() {
        view.showLoading();

        WordCallbacks.FavoriteWordsCallback callback = (words) -> {
            FavoritesPresenter.this.words = words;
            view.showFavorites(words);
            view.hideLoading();
            Log.i("FavoritePresenter","callback Favorite words");
        };
        wordRepository.getFavorites(callback);
    }


    public void removeFavorite(Word word) {
        wordRepository.deleteFromFavorites(word);
        words.remove(word);
    }

    @Override
    public void onViewAttached(FavoritesView view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        view = null;
    }

    @Override
    public void onDestroy() {
        wordRepository = null;
    }


}

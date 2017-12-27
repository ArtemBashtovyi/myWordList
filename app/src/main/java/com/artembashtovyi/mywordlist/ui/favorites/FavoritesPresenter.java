package com.artembashtovyi.mywordlist.ui.favorites;


import android.os.AsyncTask;

import com.artembashtovyi.mywordlist.Presenter;
import com.artembashtovyi.mywordlist.data.WordRepository;
import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.List;

public class FavoritesPresenter implements Presenter<FavoritesView> {

    private FavoritesView view;
    private WordRepository wordRepository;
    private List<Word> words;


    public FavoritesPresenter(FavoritesView view, WordRepository wordRepository) {
        this.view = view;
        this.wordRepository = wordRepository;
    }

    void loadFavorites() {
        new AsyncTask<Void,Void,List<Word>>() {

            @Override
            protected List<Word> doInBackground(Void... voids) {
                return wordRepository.getFavorites();
            }

            @Override
            protected void onPostExecute(List<Word> words) {
                super.onPostExecute(words);
                FavoritesPresenter.this.words = words;
                view.showFavorites(words);

            }
        }.execute();
    }


    void removeFavorite(Word word) {
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

    }
}

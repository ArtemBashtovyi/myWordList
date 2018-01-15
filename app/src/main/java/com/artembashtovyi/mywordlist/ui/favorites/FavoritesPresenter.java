package com.artembashtovyi.mywordlist.ui.favorites;


import android.os.AsyncTask;
import android.util.Log;

import com.artembashtovyi.mywordlist.Presenter;
import com.artembashtovyi.mywordlist.data.WordRepository;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.dialog.ViewChoiceDialog;

import java.util.List;

public class FavoritesPresenter implements Presenter<FavoritesView> {

    private FavoritesView view;
    private WordRepository wordRepository;
    private List<Word> words;

    public FavoritesPresenter(FavoritesView view, WordRepository wordRepository) {
        this.view = view;
        this.wordRepository = wordRepository;
    }

    public void loadFavorites() {
        new AsyncTask<Void, Void, List<Word>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                view.showLoading();
            }

            @Override
            protected List<Word> doInBackground(Void... voids) {
                return wordRepository.getFavorites();
            }

            @Override
            protected void onPostExecute(List<Word> words) {
                super.onPostExecute(words);
                FavoritesPresenter.this.words = words;
                view.hideLoading();
                view.showFavorites(words);
                Log.i("Presenter","onPostEx");
            }
        }.execute();

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

    }


}

package com.artembashtovyi.mywordlist.ui.list;


import android.os.AsyncTask;

import com.artembashtovyi.mywordlist.data.WordRepository;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.query.AllWordsQuery;
import com.artembashtovyi.mywordlist.Presenter;
import com.artembashtovyi.mywordlist.ui.adapter.EngVersionView;
import com.artembashtovyi.mywordlist.ui.adapter.FullVersionView;
import com.artembashtovyi.mywordlist.ui.adapter.UaVersionView;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;

import java.util.Collections;
import java.util.List;

public class WordListPresenter implements Presenter<WordListView> {

    private WordListView view;
    private WordRepository wordRepository;
    private List<Word> words;
    private ViewBindContract contract;

    WordListPresenter(WordListView view, WordRepository wordRepository) {
        this.view = view;
        this.wordRepository = wordRepository;
    }


    void loadWords() {
        if (words == null) {
            new AsyncTask<Void, Void, List<Word>>() {

                @Override
                protected List<Word> doInBackground(Void... voids) {
                    return words = wordRepository.getWords(new AllWordsQuery());
                }

                @Override
                protected void onPostExecute(List<Word> wordsDTO) {
                    words = wordsDTO;
                    view.showWords(wordsDTO);
                }

            }.execute();
        } else
            view.showWords(words);
    }

    // start dialog which describe word, and check favorite flag
    void startDescriptionDialog(Word word) {
        boolean isFavorite = wordRepository.isFavorite(word);
        view.showDescriptionDialog(word,isFavorite);
    }

    // check DB, if word in favorites then remove him,and comeback flag word doesn't exists in table
    boolean isFavoriteState(Word word) {
        boolean isFavorite = wordRepository.isFavorite(word);
        if (isFavorite) {
            wordRepository.deleteFromFavorites(word);
            return false;
        } else {
            wordRepository.addToFavorites(word);
            return true;
        }
    }

    @Override
    public void onViewAttached(WordListView view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        view = null;
    }

    @Override
    public void onDestroy() {

    }

    void shuffleWords() {
        Collections.shuffle(words);
        view.showWords(words);
    }

    void changeAdapterContract(String selectedItem) {
        switch (selectedItem) {
            case "Full":
                contract = new FullVersionView();
                break;
            case "Ua":
                contract = new UaVersionView();
                break;
            case "Eng":
                contract = new EngVersionView();
                break;
        }
        view.showViewContract(contract);
    }
}

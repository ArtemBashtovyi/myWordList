package com.artembashtovyi.mywordlist.ui.list;


import android.util.Log;

import com.artembashtovyi.mywordlist.Presenter;
import com.artembashtovyi.mywordlist.data.WordRepositoryImpl;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.query.AllWordsQuery;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;
import com.artembashtovyi.mywordlist.ui.dialog.description.WordState;

import java.util.Collections;
import java.util.List;

public class WordListPresenter implements Presenter<WordListView> {

    private WordListView view;
    private WordRepositoryImpl wordRepository;
    private List<Word> words;
    private ViewBindContract contract = null;

    WordListPresenter(WordListView view, WordRepositoryImpl wordRepository) {
        this.view = view;
        this.wordRepository = wordRepository;
    }

    void loadWords() {
        if (words == null) {
            wordRepository.getWords((selectedWords) -> {
                words = selectedWords;
                view.showWords(selectedWords);
                if (contract != null) {
                    view.showViewContract(contract);
                }
            },new AllWordsQuery());
        } else view.showWords(words);
    }

    // start dialog which describe word
    void startDescriptionDialog(Word word) {
        //  check favorite flag
        wordRepository.isFavorite((isFavorite) ->
                // check word state and return it
                wordRepository.getWordState(wordState -> {
                    view.showDescriptionDialog(word, isFavorite, wordState);
                    Log.i("WordListPresenter","show description of the word : " + word.getEngVersion()
                            + " " + isFavorite + " word state: " + wordState);
                    },word)
        ,word);

    }

    // check DB, if word in favorites then remove him,and return flag that word doesn't exists in table
    void changeFavoriteState(Word word) {
        wordRepository.isFavorite(isWordFavorite -> {
            // set isFavorite but vise versa
            boolean currentState = !isWordFavorite;
            wordRepository.setFavorite(word,currentState);
            view.updateDialogFavoriteImage(isWordFavorite);
        },word);
    }


    void setWordState(Word word, WordState currentWordState) {
        // update image of dialog instantly
        view.updateDialogColorImage(currentWordState);
        wordRepository.setWordState(word,currentWordState);
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
    public void onDestroy() {}

    void shuffleWords() {
        Collections.shuffle(words);
        view.showWords(words);
    }

    void changeAdapterContract(ViewBindContract contract) {
        this.contract = contract;
        view.updateViewContract(contract);
    }
}

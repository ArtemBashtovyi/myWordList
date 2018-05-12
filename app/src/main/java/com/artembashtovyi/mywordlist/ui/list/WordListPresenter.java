package com.artembashtovyi.mywordlist.ui.list;


import com.artembashtovyi.mywordlist.Presenter;
import com.artembashtovyi.mywordlist.data.WordRepositoryImpl;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.query.AllWordsQuery;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;

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
                if (contract !=null) {
                    view.showViewContract(contract);
                }
            },new AllWordsQuery());
        } else
            view.showWords(words);
    }

    // start dialog which describe word, and check favorite flag
    void startDescriptionDialog(Word word) {
        wordRepository.isFavorite((isFavorite) -> view.showDescriptionDialog(word,isFavorite),word);

    }

    // check DB, if word in favorites then remove him,and comeback flag word doesn't exists in table
    boolean isFavoriteState(Word word) {
        final boolean[] isFavorite = {};
        wordRepository.isFavorite(isWordFavorite -> {
            if (isWordFavorite) {
                wordRepository.deleteFromFavorites(word);
            } else {
                wordRepository.addToFavorites(word);
            }
            isFavorite[0] = isWordFavorite;
        },word);

        return !isFavorite[0];
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

    void changeAdapterContract(ViewBindContract contract) {
        this.contract = contract;
    }
}

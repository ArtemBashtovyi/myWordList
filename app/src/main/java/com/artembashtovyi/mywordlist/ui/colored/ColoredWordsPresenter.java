package com.artembashtovyi.mywordlist.ui.colored;

import android.util.Log;

import com.artembashtovyi.mywordlist.Presenter;
import com.artembashtovyi.mywordlist.data.WordRepository;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.adapter.EngVersionView;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;
import com.artembashtovyi.mywordlist.ui.dialog.description.WordState;

import java.util.Collections;
import java.util.List;

/**
 * Created by felix on 5/12/18
 */

public class ColoredWordsPresenter implements Presenter<ColoredWordsView> {

    private ColoredWordsView view;
    private WordRepository wordRepository;
    private ViewBindContract contract;
    private List<Word> words;
    private Integer currentPosition;

    ColoredWordsPresenter(ColoredWordsView view, WordRepository wordRepository) {
        this.view = view;
        this.wordRepository = wordRepository;
        contract = new EngVersionView();
    }

    void loadRedWords() {
        wordRepository.getRedWords(wordsResult -> {
            ColoredWordsPresenter.this.words = wordsResult;
            view.showWords(wordsResult,contract);
        });
    }

    void loadYellowWords() {
        wordRepository.getYellowWords(wordsResult -> {
            ColoredWordsPresenter.this.words = wordsResult;
            view.showWords(wordsResult,contract);
        });
    }

    void loadGreenWords() {
        wordRepository.getGreenWords(wordsResult -> {
            ColoredWordsPresenter.this.words = wordsResult;
            view.showWords(wordsResult,contract);
        });
    }

    void changeViewContract(ViewBindContract contract) {
        this.contract = contract;
        view.updateViewContract(contract);
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

    void saveCurrentPosition(int position) {
        currentPosition = position;
    }

    void restoreCurrentPosition() {
        if (currentPosition != null) {
            view.restoreRecyclerViewPosition(currentPosition);
        }
    }

    void setWordState(Word word, WordState currentWordState) {
        // update image of dialog instantly
        view.updateDialogColorImage(currentWordState);
        wordRepository.setWordState(word,currentWordState);
    }

    void shuffleWords() {
        Collections.shuffle(words);
        view.showWords(words,contract);
    }

    @Override
    public void onViewAttached(ColoredWordsView view) {
        this.view = view;
        if (words == null) {
            loadRedWords();
            if (currentPosition != null) {
                // restore position
                view.restoreRecyclerViewPosition(currentPosition);
            }
        } else view.showWords(words,contract);
    }

    @Override
    public void onViewDetached() {
        // save current position
        view.saveRecyclerViewPosition();
        view = null;

    }

    @Override
    public void onDestroy() {
        wordRepository = null;
    }
}

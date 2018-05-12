package com.artembashtovyi.mywordlist.ui.edit;


import android.util.Log;

import com.artembashtovyi.mywordlist.Presenter;
import com.artembashtovyi.mywordlist.data.WordRepositoryImpl;
import com.artembashtovyi.mywordlist.data.async.WordCallbacks;
import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.query.AllWordsQuery;

import java.util.Collections;
import java.util.List;

public class EditListPresenter implements Presenter<EditWordsView> {

    private final static String TAG = "EditListPresenter";

    private EditWordsView view;
    private WordRepositoryImpl wordRepository;
    private List<Word> words;

    public EditListPresenter(EditWordsView view, WordRepositoryImpl wordRepository) {
        this.view = view;
        this.wordRepository = wordRepository;
    }

    // Load All Words
    void getAllWords() {
        if (words == null) {
            WordCallbacks.AllWordsCallback callback = words -> {
               view.showWords(words);
                EditListPresenter.this.words = words;
            };
            wordRepository.getWords(callback,new AllWordsQuery());
        } else
            view.showWords(words);
    }


    void deleteWords(List<Word> selectedWords) {
        if (selectedWords != null) {
            wordRepository.deleteWords(selectedWords);
            words.removeAll(selectedWords);
            view.showEditedWordList(selectedWords);
        }
    }

    void addWord(Word word) {
        if (!words.contains(word)) {
            wordRepository.addWord(word);
            words.add(word);
            view.showAddedWord(word);
            Log.i(TAG, "New Word has appended");
        } else {
            view.showError();
        }
    }

    // Logic for presenter
    void editWord(Word oldWord, Word word) {
        Log.i(TAG, "EditWord " + word.toString());

        wordRepository.editWord(oldWord, word);
        view.showEditedWord(oldWord, word);

        int s = words.indexOf(oldWord);
        words.get(s).setEngVersion(word.getEngVersion());
        words.get(s).setUaVersion(word.getUaVersion());
    }

    void sortWords(int id) {
        switch (id) {
            case 0:
                Collections.sort(words, (word1, word2) -> word1.getEngVersion().compareTo(word2.getEngVersion()));
                break;
            case 1:
                Collections.sort(words, ((word1, word2) -> word1.getUaVersion().compareTo(word2.getUaVersion())));
                break;
            case 2:
                Collections.sort(words, (word1, word2) -> {
                    if (word1.getId() < word2.getId()) {
                        return 1;
                    } else if (word1.getId() > word2.getId())
                        return -1;
                    return 0;
                });
                break;
        }
        view.showWords(words);
    }

    @Override
    public void onViewAttached(EditWordsView view) {
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

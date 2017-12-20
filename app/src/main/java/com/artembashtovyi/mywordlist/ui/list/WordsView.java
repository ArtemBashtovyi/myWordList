package com.artembashtovyi.mywordlist.ui.list;


import com.artembashtovyi.mywordlist.data.model.Word;
import java.util.List;

public interface WordsView {

    void showWords(List<Word> words);

    void showError();

    void showAddedWord(Word word);

    void scrollListDown();
}

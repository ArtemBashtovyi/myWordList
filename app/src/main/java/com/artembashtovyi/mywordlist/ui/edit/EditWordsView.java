package com.artembashtovyi.mywordlist.ui.edit;


import com.artembashtovyi.mywordlist.data.model.Word;
import java.util.List;

public interface EditWordsView {

    void showWords(List<Word> words);

    void showError();

    void showAddedWord(Word word);

    void showEditedWord(Word oldWord,Word newWord);

    void scrollListDown();

    void showAddDialog();

}

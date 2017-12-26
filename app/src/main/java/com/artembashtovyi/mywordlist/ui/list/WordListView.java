package com.artembashtovyi.mywordlist.ui.list;


import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;

import java.util.List;

interface WordListView {

    void showWords(List<Word> words);

    void showViewContract(ViewBindContract contract);

    void showDescriptionDialog(Word word,boolean isFavorite);

}

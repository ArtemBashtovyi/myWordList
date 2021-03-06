package com.artembashtovyi.mywordlist.ui.list;


import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;
import com.artembashtovyi.mywordlist.ui.dialog.description.DescriptionDialogContract;

import java.util.List;

// view extend logic of description dialog
interface WordListView extends DescriptionDialogContract {

    void showWords(List<Word> words);

    void showViewContract(ViewBindContract contract);

    void updateViewContract(ViewBindContract contract);
}

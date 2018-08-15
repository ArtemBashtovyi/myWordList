package com.artembashtovyi.mywordlist.ui.colored;

import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;
import com.artembashtovyi.mywordlist.ui.dialog.description.DescriptionDialogContract;

import java.util.List;

/**
 * Created by felix on 5/12/18
 */

public interface ColoredWordsView extends DescriptionDialogContract{

    void showWords(List<Word> words, ViewBindContract contract);

    void showViewContract(ViewBindContract contract);

    void saveRecyclerViewPosition();

    void restoreRecyclerViewPosition(int position);

    void updateViewContract(ViewBindContract contract);
}

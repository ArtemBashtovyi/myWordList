package com.artembashtovyi.mywordlist.ui.adapter;
import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.List;

public interface ViewBindContract {

    void initializeView(List<Word> words, WordHolder holder, int position);
}

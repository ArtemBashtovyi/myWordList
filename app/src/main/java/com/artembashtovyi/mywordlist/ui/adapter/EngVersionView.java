package com.artembashtovyi.mywordlist.ui.adapter;


import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.List;

public class EngVersionView implements ViewBindContract {

    @Override
    public void initializeView(List<Word> words, WordHolder holder, int position) {
        holder.mainTv.setText(words.get(position).getEngVersion());
        holder.secondTv.setText("");
    }
}

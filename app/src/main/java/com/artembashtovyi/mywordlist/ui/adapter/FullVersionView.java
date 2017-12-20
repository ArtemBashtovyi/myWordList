package com.artembashtovyi.mywordlist.ui.adapter;

import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.ui.adapter.ViewBindContract;
import com.artembashtovyi.mywordlist.ui.adapter.editHolder.EditHolder;

import java.util.List;

public class FullVersionView implements ViewBindContract {


    @Override
    public void initializeView(List<Word> words, WordHolder holder, int position) {
        holder.mainTv.setText(words.get(position).getEngVersion());
        holder.secondTv.setText(words.get(position).getUaVersion());
    }
}

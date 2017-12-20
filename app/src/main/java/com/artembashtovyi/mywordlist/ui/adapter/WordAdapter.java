package com.artembashtovyi.mywordlist.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.model.Word;


import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordHolder>{

    private ViewBindContract viewBindContract;
    private List<Word> words;
    private Context context;
    private OnWordClickListener onWordListener;


    public WordAdapter(ViewBindContract viewBindContract, List<Word> words,
                       Context context,OnWordClickListener onWordListener) {
        this.viewBindContract = viewBindContract;
        this.context = context;
        this.words = words;
        this.onWordListener = onWordListener;
    }

    // click on View then call custom listener
    private View.OnClickListener viewOnClickListener = view -> {
        Word word = (Word) view.getTag();
        onWordListener.clickCallBack(word);
    };

    @Override
    public WordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.word_item,null);
        return new WordHolder(view);
    }


    @Override
    public void onBindViewHolder(WordHolder holder, int position) {
        viewBindContract.initializeView(words,holder,position);

        holder.rootView.setOnClickListener(viewOnClickListener);
        holder.rootView.setTag(words.get(position));
    }

    @Override
    public int getItemCount() {
        return words == null ? 0 : words.size();
    }



    // change Contract when languageMode changed
    public void changeContract(ViewBindContract viewBindContract) {
        this.viewBindContract = viewBindContract;
        notifyDataSetChanged();
    }


    public interface OnWordClickListener {
        public void clickCallBack(Word word);
    }
}

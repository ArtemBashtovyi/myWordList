package com.artembashtovyi.mywordlist.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordHolder> implements Filterable {


    private ViewBindContract viewBindContract;
    private List<Word> words;
    private List<Word> filteredWords;
    private Context context;
    private OnWordClickListener onWordListener;

    public WordAdapter(ViewBindContract viewBindContract, List<Word> words,
                       Context context,OnWordClickListener onWordListener) {
        this.viewBindContract = viewBindContract;
        this.context = context;
        this.words = words;
        this.filteredWords = words;
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
        viewBindContract.initializeView(filteredWords,holder,position);

        Word word = words.get(position);
        int color = R.color.colorRedState;

        holder.rootView.setOnClickListener(viewOnClickListener);
        holder.rootView.setTag(filteredWords.get(position));
      //  Log.i("Adapter","Color : " +color );
        holder.rootView.setBackgroundResource(color);
    }

    @Override
    public int getItemCount() {
        return filteredWords == null ? 0 : filteredWords.size();
    }


    // change Contract when languageMode has changed
    public void changeContract(ViewBindContract viewBindContract) {
        this.viewBindContract = viewBindContract;
        notifyDataSetChanged();
    }

    public void updateWord(Word word) {
        if (word != null) {
            int position = words.indexOf(word);
            if (position != -1) {
                notifyItemChanged(position);
            }
        }
        notifyDataSetChanged();
    }

    public interface OnWordClickListener {
        void clickCallBack(Word word);
    }

    // Filter for search
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {

                    // if editField isEmpty - set all words
                    filteredWords = words;
                } else {
                    List<Word> filteredList = new ArrayList<>();
                    for (Word row : words) {

                        // engVersion match condition.
                        // here we are looking for engVersion or uaVersion
                        if (row.getEngVersion().toLowerCase().contains(charString.toLowerCase())
                                || row.getEngVersion().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filteredWords = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredWords;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredWords = (ArrayList<Word>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}

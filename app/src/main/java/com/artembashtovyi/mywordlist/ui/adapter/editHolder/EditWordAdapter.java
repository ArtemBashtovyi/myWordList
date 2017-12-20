package com.artembashtovyi.mywordlist.ui.adapter.editHolder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.model.SelectedWord;
import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.ArrayList;
import java.util.List;


public class EditWordAdapter extends RecyclerView.Adapter<EditHolder>
        implements EditHolder.OnItemSelectedListener{

    private Context context;
    private List<SelectedWord> selectedWords;
    private EditHolder.OnItemSelectedListener onItemSelectedListener;

    public EditWordAdapter(Context context, List<Word> words,
                           EditHolder.OnItemSelectedListener onItemSelectedListener) {
        this.context = context;
        this.onItemSelectedListener = onItemSelectedListener;

        setSelectedWords(words);
    }

    @Override
    public EditHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.word_edit_item,null);
        return new EditHolder(view,this);
    }

    @Override
    public void onBindViewHolder(EditHolder holder, int position) {
        holder.mainTv.setText(selectedWords.get(position).getEngVersion());
        holder.secondTv.setText(selectedWords.get(position).getUaVersion());

        holder.selectedWord = selectedWords.get(position);
        holder.setChecked(holder.selectedWord.isSelected());
    }

    @Override
    public int getItemCount() {
        return selectedWords == null ? 0 : selectedWords.size();
    }


    public List<Word> getSelectedWords() {
        List<Word> words = new ArrayList<>();

        for (SelectedWord selectedWord : selectedWords) {
            if (selectedWord.isSelected()) {
                words.add(new Word(selectedWord.getId(),selectedWord.getEngVersion(),
                        selectedWord.getUaVersion()));

                Log.i("EditAdapter","getSelectedWord" +selectedWord.toString());
            }
        }
        return words;
    }


    public void addWord(Word word) {
        selectedWords.add(new SelectedWord(word,false));
        notifyItemChanged(selectedWords.size());
    }


    public void setSelectedWords(List<Word> words) {
        selectedWords = new ArrayList<>();

        for (Word word : words) {
            selectedWords.add(new SelectedWord(word,false));
        }
        notifyDataSetChanged();
    }

    @Override
    public void callBack(SelectedWord selectedWord) {
        onItemSelectedListener.callBack(selectedWord);
    }


}

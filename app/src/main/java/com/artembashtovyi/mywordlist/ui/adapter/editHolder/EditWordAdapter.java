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
        /*implements EditHolder.OnItemSelectedListener*/{

    private Context context;
    private List<SelectedWord> selectedWords;
    /*private EditHolder.OnItemSelectedListener onItemSelectedListener;*/
    private OnViewClickListener onViewClickListener;

    public EditWordAdapter(Context context, List<Word> words,
                           /*EditHolder.OnItemSelectedListener onItemSelectedListener,*/
                           OnViewClickListener onViewClickListener) {
        this.context = context;
        /*this.onItemSelectedListener = onItemSelectedListener;*/
        this.onViewClickListener = onViewClickListener;
        setSelectedWords(words);
    }


    private View.OnClickListener onClickListener = view -> {
        SelectedWord selectedWord = (SelectedWord) view.getTag();
        // set Word
        Word word = new Word(selectedWord.getId(),selectedWord.getEngVersion(),selectedWord.getUaVersion());
        onViewClickListener.onEditClick(word);
    };

    @Override
    public EditHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.word_edit_item,null);
        return new EditHolder(view);
    }

    @Override
    public void onBindViewHolder(EditHolder holder, int position) {

        SelectedWord selectedWord = selectedWords.get(position);

        holder.mainTv.setText(selectedWord.getEngVersion());
        holder.secondTv.setText(selectedWord.getUaVersion());

        holder.selectedWord = selectedWord;
        holder.setChecked(holder.selectedWord.isSelected());
        holder.editIv.setTag(selectedWord);
        holder.editIv.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        return selectedWords == null ? 0 : selectedWords.size();
    }


    public void editWord(Word oldWord,Word word) {

        int position = selectedWords.indexOf(new SelectedWord(oldWord,false));
        Log.i("EditAdapter",position + " position");

        selectedWords.get(position).setEngVersion(word.getEngVersion());
        selectedWords.get(position).setUaVersion(word.getUaVersion());

        notifyItemChanged(position);
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

    /*@Override
    public void callBack(SelectedWord selectedWord) {
        onItemSelectedListener.callBack(selectedWord);
    }*/


    public interface OnViewClickListener {
        void onEditClick(Word selectedWord);
    }
}

package com.artembashtovyi.mywordlist.ui.favorites.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>  {

    private List<Word> words;
    private Context context;
    private OnWordClickListener onWordListener;


    public FavoriteAdapter( List<Word> words, Context context,OnWordClickListener onWordListener) {

        this.context = context;
        this.words = words;
        this.onWordListener = onWordListener;
    }

    // click on View then call custom listener
    private View.OnClickListener viewOnClickListener = view -> {
        Word word = (Word) view.getTag();
        removeFavorite(word);
        onWordListener.clickCallBack(word);
    };

    @Override
    public FavoriteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.word_favorite_item,null);
        return new FavoriteHolder(view);
    }


    @Override
    public void onBindViewHolder(FavoriteHolder holder, int position) {

        Word selectedWord = words.get(position);

        holder.mainTv.setText(selectedWord.getEngVersion());
        holder.secondTv.setText(selectedWord.getUaVersion());

        holder.imageView.setOnClickListener(viewOnClickListener);
        holder.imageView.setTag(selectedWord);
    }

    @Override
    public int getItemCount() {
        return words == null ? 0 : words.size();
    }

    void removeFavorite(Word word) {
        int position = words.indexOf(word);
        notifyItemRemoved(position);
    }


    public interface OnWordClickListener {
        void clickCallBack(Word word);
    }




    class FavoriteHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.main_field_text_view)
        TextView mainTv;

        @BindView(R.id.second_field_text_view)
        TextView secondTv;

        @BindView(R.id.image_clear_favorite)
        ImageView imageView;

        public FavoriteHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


    }

}

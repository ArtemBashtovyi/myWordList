package com.artembashtovyi.mywordlist.ui.adapter.editHolder;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artembashtovyi.mywordlist.R;
import com.artembashtovyi.mywordlist.data.model.SelectedWord;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EditHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.main_field_text_view)
    TextView mainTv;

    @BindView(R.id.second_field_text_view)
    TextView secondTv;

    @BindView(R.id.checked_text_item)
    CheckedTextView textView;

    @BindView(R.id.root_view)
    LinearLayout rootView;

    SelectedWord selectedWord;
    private OnItemSelectedListener callbackSelectedListener;

    EditHolder(View itemView, OnItemSelectedListener callback) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.callbackSelectedListener = callback;

        textView.setOnClickListener(view -> {

            // set graphic state for item
            if (selectedWord.isSelected()) {
                setChecked(false);
            } else {
                setChecked(true);
            }
            callbackSelectedListener.callBack(selectedWord);
        });


        // set icon checked
        TypedValue value = new TypedValue();
        textView.getContext().getTheme().resolveAttribute(android.R.attr.listChoiceIndicatorMultiple, value, true);
        int checkMarkDrawableResId = value.resourceId;
        textView.setCheckMarkDrawable(checkMarkDrawableResId);
    }

   public void setChecked(boolean value) {
        if (value) {
            textView.setBackgroundColor(Color.LTGRAY);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                textView.setBackground(null);
            } else textView.setBackgroundColor(Color.WHITE);
        }

        selectedWord.setSelected(value);
        textView.setChecked(value);
    }


    public interface OnItemSelectedListener {
        void callBack(SelectedWord selectedWord);
    }
}
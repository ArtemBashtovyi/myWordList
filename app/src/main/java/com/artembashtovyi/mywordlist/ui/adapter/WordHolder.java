package com.artembashtovyi.mywordlist.ui.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.artembashtovyi.mywordlist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.main_field_text_view)
    TextView mainTv;

    @BindView(R.id.second_field_text_view)
    TextView secondTv;

    @BindView(R.id.root_view)
    LinearLayout rootView;

    public WordHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}

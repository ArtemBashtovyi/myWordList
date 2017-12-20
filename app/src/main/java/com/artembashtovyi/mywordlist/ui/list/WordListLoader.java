package com.artembashtovyi.mywordlist.ui.list;



import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.DbHelper;
import com.artembashtovyi.mywordlist.data.sqlite.query.AllWordsQuery;

import java.util.List;

public class WordListLoader extends AsyncTaskLoader<List<Word>> {
    private Context context;

    public WordListLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected boolean onCancelLoad() {
        return super.onCancelLoad();
    }


    @Override
    public List<Word> loadInBackground() {
        DbHelper dbHelper = new DbHelper(context);
        return dbHelper.getWords(new AllWordsQuery());
    }
}

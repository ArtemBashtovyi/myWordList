package com.artembashtovyi.mywordlist.data.sqlite;


import android.content.Context;
import android.os.AsyncTask;

import com.artembashtovyi.mywordlist.data.model.Word;


public class WordInsertBackground extends AsyncTask<Void,Void,Void>{

    private Context context;
    private Word word;

    public WordInsertBackground(Context context,Word word) {
        this.context = context;
        this.word = word;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... aVoid) {
        DbHelper dbHelper = new DbHelper(context);
        dbHelper.addWord(word);
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}

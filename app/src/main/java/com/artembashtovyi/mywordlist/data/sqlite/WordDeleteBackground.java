package com.artembashtovyi.mywordlist.data.sqlite;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.artembashtovyi.mywordlist.data.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordDeleteBackground extends AsyncTask<Void,Void,Void>{

    private Context context;
    private List<Word> words;

    public WordDeleteBackground(Context context,List<Word> words) {
        this.context = context;
        this.words = words;
    }

    @Override
    protected Void doInBackground(Void... aVoid) {
        try {
           /* DbHelper dbHelper = new DbHelper(context);
            dbHelper.deleteWords(words);*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }




    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}


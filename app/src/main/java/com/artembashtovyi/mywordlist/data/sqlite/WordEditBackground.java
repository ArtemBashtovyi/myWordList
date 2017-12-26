package com.artembashtovyi.mywordlist.data.sqlite;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.artembashtovyi.mywordlist.data.model.Word;

public class WordEditBackground extends AsyncTask<Void,Void,Void>{

    private Context context;
    private Word word;
    private Word oldWord;

    public WordEditBackground(Context context,Word oldWord,Word word) {
        this.context = context;
        this.word = word;
        this.oldWord = oldWord;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        /*DbHelper dbHelper = new DbHelper(context);
        dbHelper.editWord(oldWord,word);*/
        Log.i("AsyncEdit","Edit" + word.toString());
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}

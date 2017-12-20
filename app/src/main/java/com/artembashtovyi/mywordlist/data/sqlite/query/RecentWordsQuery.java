package com.artembashtovyi.mywordlist.data.sqlite.query;

import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.DbHelper;

import java.util.List;

import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.Words.TABLE_WORDS;



public class RecentWordsQuery implements Query {

    private int countWords;

    public RecentWordsQuery(int countWords) {
        this.countWords = countWords;
    }

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM " + DbHelper.Words.TABLE_WORDS
                + " ORDER BY " + DbHelper.Words._ID + " DESC LIMIT " + countWords;
    }
}

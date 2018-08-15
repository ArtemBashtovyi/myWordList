package com.artembashtovyi.mywordlist.data.sqlite.query;

import android.provider.BaseColumns;



public class RecentWordsQuery implements Query {

    private int countWords;

    public RecentWordsQuery(int countWords) {
        this.countWords = countWords;
    }

    @Override
    public String getSqlQuery(String tableName) {

        return "SELECT * FROM " + tableName
                + " ORDER BY " + BaseColumns._ID + " DESC LIMIT " + countWords;
    }


}

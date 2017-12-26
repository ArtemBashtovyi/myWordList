package com.artembashtovyi.mywordlist.data.sqlite.query;

import com.artembashtovyi.mywordlist.data.sqlite.DbHelper;



public class RecentWordsQuery implements Query {

    private int countWords;

    public RecentWordsQuery(int countWords) {
        this.countWords = countWords;
    }

    @Override
    public String getSqlQuery(String tableName) {

        return "SELECT * FROM " + tableName
                + " ORDER BY " + DbHelper.Words._ID + " DESC LIMIT " + countWords;
    }


}

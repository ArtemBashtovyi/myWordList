package com.artembashtovyi.mywordlist.data.sqlite.query;

import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.Words.TABLE_WORDS;



public class AllWordsQuery implements Query {

    @Override
    public String getSqlQuery() {
        return "SELECT * FROM " + TABLE_WORDS;
    }
}

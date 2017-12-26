package com.artembashtovyi.mywordlist.data.sqlite.query;



public class AllWordsQuery implements Query {

    @Override
    public String getSqlQuery(String tableName) {
        return "SELECT * FROM " + tableName;
    }
}

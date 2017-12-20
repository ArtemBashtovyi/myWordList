package com.artembashtovyi.mywordlist.data.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.artembashtovyi.mywordlist.data.model.Word;
import com.artembashtovyi.mywordlist.data.sqlite.query.Query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.Words.COLUMN_NAME_ENG_VERSION;
import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.Words.COLUMN_NAME_UA_VERSION;
import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.Words.SQL_CREATE_WORDS_TABLE;
import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.Words.TABLE_WORDS;


public class DbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String AUTO_INCREMENT = " AUTOINCREMENT";


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wordList.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_WORDS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
            onCreate(db);
        }
    }


    public void deleteWords(List<Integer> ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            StringBuilder stringBuilder = new StringBuilder();
            Iterator<Integer> iterator = ids.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(iterator.next());
                if (iterator.hasNext()) {
                    stringBuilder.append(",");
                }
            }

            Log.i("DbHelper", "DeleteWords SQL " + stringBuilder.toString());
            db.execSQL(String.format("DELETE FROM " + Words.TABLE_WORDS +
                    " WHERE " + Words._ID + " IN (%s);", stringBuilder.toString()));
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public List<Word> getWords(Query query) {
        Log.i("DbHelper","getAllWords");
        List<Word> words = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query.getSqlQuery(),null);
        // Управляем курсором

        try {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(Words._ID));
                    String engVersion = cursor.getString(cursor.getColumnIndex(Words.COLUMN_NAME_ENG_VERSION));
                    String uaVersion  = cursor.getString(cursor.getColumnIndex(Words.COLUMN_NAME_UA_VERSION));

                    Word word = new Word(id,engVersion,uaVersion);
                    words.add(word);
                } while(cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.d("SQLite","Error get All Words");
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return words;
    }

    // TODO : implement check ' character
    void addWord(Word word){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ENG_VERSION,word.getEngVersion());
        contentValues.put(COLUMN_NAME_UA_VERSION,word.getUaVersion());

            long rowId =
                    db.insert(TABLE_WORDS, null, contentValues);
            Log.i("DbHelper addWord","id" + rowId);
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.close();
        }

    }


    /* Entity */
    public static class Words implements BaseColumns {

        public static final String TABLE_WORDS = "words";
        static final String COLUMN_NAME_ENG_VERSION = "engVersion";
        static final String COLUMN_NAME_UA_VERSION = "uaVersion";

        static final String SQL_CREATE_WORDS_TABLE =
                "CREATE TABLE " + TABLE_WORDS + "(" +
                        Words._ID + INTEGER_TYPE +  PRIMARY_KEY + AUTO_INCREMENT  + //"(6)" +
                        "," + COLUMN_NAME_ENG_VERSION + TEXT_TYPE + //"(45)" +
                        "," + COLUMN_NAME_UA_VERSION + TEXT_TYPE /*+ "(45)"*/ + ")";

        public static final String SQL_DROP_WORDS_TABLE = "DROP TABLE " + TABLE_WORDS;

    }


}

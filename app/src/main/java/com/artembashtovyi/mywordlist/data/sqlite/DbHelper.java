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
import java.util.List;

import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.FavoriteWords.SQL_CREATE_FAVORITE_WORDS_TABLE;
import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.FavoriteWords.TABLE_FAVORITE_WORDS;
import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.Words.COLUMN_NAME_ENG_VERSION;
import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.Words.COLUMN_NAME_UA_VERSION;
import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.Words.SQL_CREATE_WORDS_TABLE;
import static com.artembashtovyi.mywordlist.data.sqlite.DbHelper.Words.TABLE_WORDS;


public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "DbHelper";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String AUTO_INCREMENT = " AUTOINCREMENT";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wordList.db";

    private static DbHelper INSTANCE = null;

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DbHelper getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DbHelper(context.getApplicationContext());
        }
        return INSTANCE;
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_WORDS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_WORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORDS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_WORDS);
            onCreate(db);
        }
    }

    public boolean isFavorite(Word word) {
        Log.i(TAG,"isFavorite");
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * from " + TABLE_FAVORITE_WORDS + " where " +
                COLUMN_NAME_ENG_VERSION + " = '" + word.getEngVersion() + "' AND "+
                COLUMN_NAME_UA_VERSION + " = '" + word.getUaVersion() + "'";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void deleteWord(Word word,String tableName) {
        Log.i(TAG,"deleteWord");
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            db.delete(tableName,
                    Words.COLUMN_NAME_ENG_VERSION + " = ? AND " + Words.COLUMN_NAME_UA_VERSION + " = ?",
                    new String[] {word.getEngVersion(), word.getUaVersion()+""});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void deleteWords(List<Word> delete,String tableName) {
        Log.i(TAG,"deleteWords");
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            for (Word aDelete : delete) {
                db.delete(tableName,
                        Words.COLUMN_NAME_ENG_VERSION + " = ? AND " + Words.COLUMN_NAME_UA_VERSION + " = ?",
                        new String[] {aDelete.getEngVersion(), aDelete.getUaVersion()+""});
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }


    public void editWord(Word oldWord,Word newWord) {
        Log.i(TAG,"OldWord" + oldWord.toString());
        Log.i(TAG,"NewWord" + newWord.toString());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ENG_VERSION,newWord.getEngVersion());
        contentValues.put(COLUMN_NAME_UA_VERSION,newWord.getUaVersion());

        try {
            db.update(TABLE_WORDS,contentValues, Words.COLUMN_NAME_ENG_VERSION + " = ? AND "
                            + Words.COLUMN_NAME_UA_VERSION + " = ?",
                    new String[] {oldWord.getEngVersion(), oldWord.getUaVersion()+""});

        } catch (Exception e ) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }


    public List<Word> getWords(Query query,String tableName) {
        Log.i(TAG,"getAllWords");
        List<Word> words = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query.getSqlQuery(tableName),null);
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
            Log.d(TAG,"Error getAllWords");
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }

        return words;
    }

    // TODO : implement check ' character
    public void addWord(Word word,String tableName){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME_ENG_VERSION,word.getEngVersion());
            contentValues.put(COLUMN_NAME_UA_VERSION,word.getUaVersion());

            long rowId =
                    db.insert(tableName, null, contentValues);
            Log.i(TAG," id -- row " + rowId);
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
                        Words._ID + INTEGER_TYPE +  PRIMARY_KEY + AUTO_INCREMENT  +
                        "," + COLUMN_NAME_ENG_VERSION + TEXT_TYPE +
                        "," + COLUMN_NAME_UA_VERSION + TEXT_TYPE + ")";


    }


    public static class FavoriteWords implements BaseColumns{

        public static final String TABLE_FAVORITE_WORDS = "favoriteWords";
        static final String COLUMN_NAME_ENG_VERSION = "engVersion";
        static final String COLUMN_NAME_UA_VERSION = "uaVersion";

        static final String SQL_CREATE_FAVORITE_WORDS_TABLE =
                "CREATE TABLE " + TABLE_FAVORITE_WORDS + "(" +
                        FavoriteWords._ID + INTEGER_TYPE +  PRIMARY_KEY + AUTO_INCREMENT  +
                        "," + COLUMN_NAME_ENG_VERSION + TEXT_TYPE +
                        "," + COLUMN_NAME_UA_VERSION + TEXT_TYPE + ")";
    }

}

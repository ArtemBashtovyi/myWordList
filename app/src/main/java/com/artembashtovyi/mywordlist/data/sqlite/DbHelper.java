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
import com.artembashtovyi.mywordlist.ui.dialog.description.WordState;

import java.util.ArrayList;
import java.util.List;


public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "DbHelper";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String AUTO_INCREMENT = " AUTOINCREMENT";

    private static final String COLUMN_NAME_ENG_VERSION = "engVersion";
    private static final String COLUMN_NAME_UA_VERSION = "uaVersion";
    private static final String COLUMN_NAME_COLOR_STATE = "color_state";
    private static final String COLUMN_NAME_FAVORITE = "favorite";

    private static final int RED_STATE = 1;
    private static final int YELLOW_STATE = 2;
    private static final int GREEN_STATE = 3;


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
        sqLiteDatabase.execSQL(createWordTable(Tables.MAIN_TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Tables.MAIN_TABLE_NAME);
            onCreate(db);
        }
    }

    public void deleteWord(Word word) {
        Log.i(TAG,"deleteWord");
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.delete(Tables.MAIN_TABLE_NAME,
                    COLUMN_NAME_ENG_VERSION + " = ? AND " + COLUMN_NAME_UA_VERSION + " = ?",
                    new String[]{word.getEngVersion(), word.getUaVersion() + ""});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteWords(List<Word> delete,String tableName) {
        Log.i(TAG,"deleteWords");

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            for (Word aDelete : delete) {
                db.delete(tableName,
                        COLUMN_NAME_ENG_VERSION + " = ? AND " + COLUMN_NAME_UA_VERSION + " = ?",
                        new String[]{aDelete.getEngVersion(), aDelete.getUaVersion() + ""});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void editWord(Word oldWord,Word newWord) {
        Log.i(TAG,"OldWord" + oldWord.toString());
        Log.i(TAG,"NewWord" + newWord.toString());

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ENG_VERSION,newWord.getEngVersion());
        contentValues.put(COLUMN_NAME_UA_VERSION,newWord.getUaVersion());

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.update(Tables.MAIN_TABLE_NAME, contentValues, COLUMN_NAME_ENG_VERSION + " = ? AND "
                            + COLUMN_NAME_UA_VERSION + " = ?",
                    new String[]{oldWord.getEngVersion(), oldWord.getUaVersion() + ""});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setWordState(Word word,WordState state) {
        Log.i(TAG,"setColorState :" + state  + " for word = " + word);

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_COLOR_STATE,getIntByState(state));

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.update(Tables.MAIN_TABLE_NAME, contentValues, COLUMN_NAME_ENG_VERSION + " = ? AND "
                            + COLUMN_NAME_UA_VERSION + " = ?",
                    new String[]{word.getEngVersion(), word.getUaVersion() + ""});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFavorite(Word word,boolean isFavorite) {
        Log.i(TAG,"setFavorite :" + word + "status :" + isFavorite);

        ContentValues contentValues = new ContentValues();
        int favoriteState = isFavorite  ? 1 : 0;

        contentValues.put(COLUMN_NAME_FAVORITE,favoriteState);

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            db.update(Tables.MAIN_TABLE_NAME, contentValues, COLUMN_NAME_ENG_VERSION + " = ? AND "
                            + COLUMN_NAME_UA_VERSION + " = ?",
                    new String[]{word.getEngVersion(), word.getUaVersion() + ""});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // get current word state
    public WordState getWordState(Word word) {
        Log.i(TAG,"getWordState ");
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * from " + Tables.MAIN_TABLE_NAME + " where " +
                COLUMN_NAME_ENG_VERSION + " = '" + word.getEngVersion() + "' AND "+
                COLUMN_NAME_UA_VERSION + " = '" + word.getUaVersion() + "'";

        Cursor cursor = db.rawQuery(query,null);
        try {
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                int state = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_COLOR_STATE));
                Log.i(TAG,"checkState = " + state);
                cursor.close();
                return getStateByInt(state);
            }
        } catch (Exception e) {
            //  Log.d(TAG,"Error getAllWords");
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return getStateByInt(0);
    }

    public boolean isFavorite(Word word) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * from " + Tables.MAIN_TABLE_NAME + " where " +
                COLUMN_NAME_ENG_VERSION + "=? AND "+
                COLUMN_NAME_UA_VERSION + "=?";

        Cursor cursor = db.rawQuery(query,new String[]{word.getEngVersion(), word.getUaVersion() + ""});
        try {
            if(cursor.getCount() > 0){
                // if true -word is favorite
                cursor.moveToFirst();
                boolean isFavorite = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_FAVORITE)) == 1;
                Log.i(TAG,"isFavorite " + isFavorite);
                cursor.close();
                return isFavorite;
            }
        } catch (Exception e) {
            //  Log.d(TAG,"Error getAllWords");
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return false;
    }

    // get All word by Query
    public List<Word> getWords(Query query) {
       // Log.i(TAG,"getAllWords");
        List<Word> words = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query.getSqlQuery(Tables.MAIN_TABLE_NAME),null);
        // cursor manage

        try {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
                    String engVersion = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ENG_VERSION));
                    String uaVersion  = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_UA_VERSION));
                    int state  = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_COLOR_STATE));
                    boolean isFavorite  = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_FAVORITE)) == 1;

                    Word word = new Word(id,engVersion,uaVersion,getStateByInt(state),isFavorite);

                    words.add(word);
                } while(cursor.moveToNext());
            }

        } catch (Exception e) {
          //  Log.d(TAG,"Error getAllWords");
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }

        return words;
    }

    // TODO : implement check ' character
    public void addWord(Word word){

        String engVersion = word.getEngVersion();
        String uaVersion = word.getUaVersion();

        // hack for ukrainian symbol '
        engVersion = engVersion.replaceAll("'","\'");
        uaVersion = uaVersion.replaceAll("'","\'");

        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_NAME_ENG_VERSION, engVersion);
            contentValues.put(COLUMN_NAME_UA_VERSION, uaVersion);

            long rowId =
                    db.insert(Tables.MAIN_TABLE_NAME, null, contentValues);
            Log.i(TAG, " id -- row " + rowId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Word> getWords(WordState state) {
        int rowState = getIntByState(state);
        List<Word> words = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Tables.MAIN_TABLE_NAME + " WHERE " +
                COLUMN_NAME_COLOR_STATE + " = " + rowState, null);
        // cursor manage

        try {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
                    String engVersion = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_ENG_VERSION));
                    String uaVersion  = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_UA_VERSION));
                    int wordState  = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_COLOR_STATE));
                    boolean isFavorite  = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_FAVORITE)) == 1;

                    Word word = new Word(id,engVersion,uaVersion,getStateByInt(wordState),isFavorite);
                    words.add(word);
                } while(cursor.moveToNext());
            }

        } catch (Exception e) {
            //  Log.d(TAG,"Error getAllWords");
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return words;
    }

    private int getIntByState(WordState state) {
        switch (state) {
            case RED:
                return RED_STATE;
            case GREEN:
                return GREEN_STATE;
            case YELLOW:
                return YELLOW_STATE;
        }
        return 0;
    }

    private WordState getStateByInt(int state) {
        switch (state) {
            case RED_STATE:
                return WordState.RED;
            case YELLOW_STATE:
                return WordState.YELLOW;
            case GREEN_STATE:
                return WordState.GREEN;
        }
        return null;
    }

    public void truncate(String tableName) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("delete from " + tableName);
    }

    /* Entities */
    public interface Tables {
        String MAIN_TABLE_NAME = "words";

    }

    private static String createWordTable(final String tableName) {
        return "CREATE TABLE " + tableName + "(" +
                BaseColumns._ID + INTEGER_TYPE +  PRIMARY_KEY + AUTO_INCREMENT  +
                "," + COLUMN_NAME_ENG_VERSION + TEXT_TYPE +
                "," + COLUMN_NAME_UA_VERSION + TEXT_TYPE +
                "," + COLUMN_NAME_COLOR_STATE + INTEGER_TYPE + " (1)" + " DEFAULT" + " 0" +
                "," + COLUMN_NAME_FAVORITE + INTEGER_TYPE + " (1)" + " DEFAULT" + " 0" +
                ")";
    }


}

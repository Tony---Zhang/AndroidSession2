package com.thoughtworks.wechat.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.thoughtworks.wechat.database.DataBaseContract.TweetEntry;
import com.thoughtworks.wechat.database.DataBaseContract.UserEntry;

public class DataBaseHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "WeChat.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String NOT_NULL = " NOT NULL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TWEETLIST_ENTRIES =
            "CREATE TABLE  IF NOT EXISTS " + TweetEntry.TABLE_NAME + " (" +
                    TweetEntry._ID + " INTEGER PRIMARY KEY," +
                    TweetEntry.COLUMN_NAME_CONTENT + TEXT_TYPE + COMMA_SEP +
                    TweetEntry.COLUMN_NAME_IMAGES + TEXT_TYPE + COMMA_SEP +
                    TweetEntry.COLUMN_NAME_SENDER + TEXT_TYPE + COMMA_SEP +
                    TweetEntry.COLUMN_NAME_COMMENTS + TEXT_TYPE + COMMA_SEP +
                    TweetEntry.COLUMN_NAME_ERROR + TEXT_TYPE + COMMA_SEP +
                    TweetEntry.COLUMN_NAME_UNKNOW_ERROR + TEXT_TYPE +
            " )";
    private static final String SQL_CREATE_USER_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + UserEntry.TABLE_NAME + " (" +
                    UserEntry._ID + " INTEGER PRIMARY KEY," +
                    UserEntry.COLUMN_NAME_USERNAME + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    UserEntry.COLUMN_NAME_NICK + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    UserEntry.COLUMN_AVATAR + TEXT_TYPE + COMMA_SEP +
                    UserEntry.COLUMN_PROFILE_IMAGE + TEXT_TYPE +
            " )";


    private static final String SQL_DELETE_TWEETLIST_ENTRIES =
            "DROP TABLE IF EXISTS " + TweetEntry.TABLE_NAME;

    private static final String SQL_DELETE_USER_ENTRIES =
            "DROP TABLE IF EXISTS " + UserEntry.TABLE_NAME;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TWEETLIST_ENTRIES);
        db.execSQL(SQL_CREATE_USER_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL(SQL_CREATE_USER_ENTRIES);
        } else {
            db.execSQL(SQL_DELETE_TWEETLIST_ENTRIES);
            db.execSQL(SQL_DELETE_USER_ENTRIES);
        }
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}

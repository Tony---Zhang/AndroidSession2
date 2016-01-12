package com.thoughtworks.wechat.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thoughtworks.wechat.model.Tweet;
import com.thoughtworks.wechat.model.User;

import java.util.List;

public class DataBaseAdapter {

    public static final String TAG = "WeChat database";

    private DataBaseHelper dataBaseHelper;

    public DataBaseAdapter(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public SQLiteDatabase openWrite() {
        return dataBaseHelper.getWritableDatabase();
    }

    public SQLiteDatabase openRead() {
        return dataBaseHelper.getReadableDatabase();
    }

    /**
     * If the argument is non-null, close the cursor.
     */
    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            cursor.close();
        }
    }

    public void insertTweetList(List<Tweet> tweetList) {
        if (tweetList == null || tweetList.isEmpty()) {
            return;
        }
        SQLiteDatabase sqLiteDatabase = openWrite();
        try {
            sqLiteDatabase.beginTransaction();
            boolean result = true;
            for (Tweet tweet: tweetList) {
                ContentValues contentValues = DataBaseUtils.tweet2ContentValues(tweet);
                final long tweetID = sqLiteDatabase.insertOrThrow(DataBaseContract.TweetEntry.TABLE_NAME, null, contentValues);
                result = result && isInsertSuccess(tweetID);
            }
            if (result) {
                sqLiteDatabase.setTransactionSuccessful();
                Log.i(TAG, "insert tweet list successful, count: " + tweetList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "insert tweet list occur error: " + e.getMessage());
        } finally {
            sqLiteDatabase.endTransaction();
        }
        sqLiteDatabase.close();
    }

    public boolean insertTweet(Tweet tweet) {
        ContentValues contentValues = DataBaseUtils.tweet2ContentValues(tweet);
        SQLiteDatabase sqLiteDatabase = openWrite();
        final long tweetID = sqLiteDatabase.insert(DataBaseContract.TweetEntry.TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
        Log.i(TAG, "insert tweet with id: " + tweetID);
        return isInsertSuccess(tweetID);
    }

    public boolean insertOrUpdateUser(User user) {
        ContentValues contentValues = DataBaseUtils.user2ContentValues(user);
        SQLiteDatabase sqLiteDatabase = openWrite();
        final Cursor cursor = queryUser();
        long userId;
        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getLong(cursor.getColumnIndex(DataBaseContract.UserEntry._ID));
            sqLiteDatabase.update(DataBaseContract.UserEntry.TABLE_NAME, contentValues, DataBaseContract.UserEntry._ID + "=?", new String[]{String.valueOf(userId)});
            Log.i(TAG, "update user with id: " + userId);
        } else {
            userId = sqLiteDatabase.insert(DataBaseContract.UserEntry.TABLE_NAME, null, contentValues);
            Log.i(TAG, "insert user with id: " + userId);
        }
        sqLiteDatabase.close();

        return isInsertSuccess(userId);
    }

    private boolean isInsertSuccess(long tweetID) {
        return tweetID != -1l;
    }

    public int deleteAllTweetList() {
        final int delete = openWrite().delete(DataBaseContract.TweetEntry.TABLE_NAME, null, null);
        Log.i(TAG, "delete all tweet list, size: " + delete);
        return delete;
    }

    public Cursor queryTweetList() {
        return openRead().query(DataBaseContract.TweetEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor queryUser() {
        return openRead().query(DataBaseContract.UserEntry.TABLE_NAME, null, null, null, null, null, null);
    }
}
package com.thoughtworks.wechat.database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thoughtworks.wechat.model.Tweet;
import com.thoughtworks.wechat.model.User;

import java.util.ArrayList;
import java.util.List;

public class DataBaseAdapter {

    public static final String TAG = "WeChat database";
    private final ContentResolver contentResolver;

    private DataBaseHelper dataBaseHelper;

    public DataBaseAdapter(Context context) {
        contentResolver = context.getContentResolver();
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

        contentResolver.delete(DataBaseContract.Tweets.URI, null, null);

        List<ContentValues> contentValuesList = new ArrayList<>(tweetList.size());

        for(Tweet tweet : tweetList) {
            contentValuesList.add(DataBaseUtils.tweet2ContentValues(tweet));
        }

        contentResolver.bulkInsert(DataBaseContract.Tweets.URI, contentValuesList.toArray(new ContentValues[tweetList.size()]));
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
        return contentResolver.query(DataBaseContract.Tweets.URI, null, null, null, null);
    }

    public Cursor queryUser() {
        return openRead().query(DataBaseContract.UserEntry.TABLE_NAME, null, null, null, null, null, null);
    }
}

package com.thoughtworks.wechat;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.thoughtworks.wechat.database.DataBaseContract;
import com.thoughtworks.wechat.database.DataBaseHelper;

public class WxContentProvider extends ContentProvider {

    public static final int TWEET = 1;
    public static final int TWEET_ID = 2;

    private DataBaseHelper mDatabaseHelper;
    private static UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, "/tweets", TWEET);
        uriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, "/tweets/#", TWEET_ID);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        switch (code){
            case TWEET_ID:
                String tweetId = DataBaseContract.Tweets.getTweetId(uri);
                selection = DataBaseContract.Tweets._ID + "=" + tweetId;
                selectionArgs = null;
            case TWEET:
                return mDatabaseHelper.getWritableDatabase().delete(DataBaseContract.Tweets.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }
    }

    @Override
    public String getType(Uri uri) {
        int code = uriMatcher.match(uri);
        switch (code) {
            case TWEET:
                return DataBaseContract.Tweets.CONTENT_TYPE;
            case TWEET_ID:
                return DataBaseContract.Tweets.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int code = uriMatcher.match(uri);
        switch (code){
            case TWEET:
                long id = mDatabaseHelper.getWritableDatabase().insertOrThrow(DataBaseContract.Tweets.TABLE_NAME, null, values);
                Uri tweetUri = DataBaseContract.Tweets.buildTweetUri(id);
                return tweetUri;
            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int code = uriMatcher.match(uri);
        switch (code){
            case TWEET_ID:
                String tweetId = DataBaseContract.Tweets.getTweetId(uri);
                selection = DataBaseContract.Tweets._ID + "=" + tweetId;
                selectionArgs = null;
            case TWEET:
                return mDatabaseHelper.getReadableDatabase().query(DataBaseContract.Tweets.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        switch (code){
            case TWEET_ID:
                String tweetId = DataBaseContract.Tweets.getTweetId(uri);
                selection = DataBaseContract.Tweets._ID + "=" + tweetId;
                selectionArgs = null;
            case TWEET:
                return mDatabaseHelper.getWritableDatabase().update(DataBaseContract.Tweets.TABLE_NAME, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("The uri '" + uri + "' is not supported by this ContentProvider");
        }
    }
}

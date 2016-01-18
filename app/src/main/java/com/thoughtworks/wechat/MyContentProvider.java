package com.thoughtworks.wechat;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.thoughtworks.wechat.database.DataBaseContract;
import com.thoughtworks.wechat.database.DataBaseHelper;

public class MyContentProvider extends ContentProvider {


    private static UriMatcher uriMatcher;

    private static SQLiteDatabase sqLiteDatabase;
    private static final int CODE_TWEET = 1;

    private static final int CODE_TWEET_ITEM = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, "/tweets", CODE_TWEET);
        uriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, "/tweets/#", CODE_TWEET_ITEM);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        switch (code) {
            case CODE_TWEET:
                return sqLiteDatabase.delete(DataBaseContract.TweetEntry.TABLE_NAME, selection, selectionArgs);
            case CODE_TWEET_ITEM:
                String tweetId = DataBaseContract.Tweets.getId(uri);
                selection = DataBaseContract.TweetEntry._ID + "=?";
                selectionArgs = new String[]{tweetId};
                return sqLiteDatabase.delete(DataBaseContract.TweetEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        int code = uriMatcher.match(uri);
        String type = null;
        switch (code) {
            case CODE_TWEET:
                type = ContentResolver.CURSOR_DIR_BASE_TYPE + "/tweet";
                break;
            case CODE_TWEET_ITEM:
                type = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/tweet";
                break;
            default:
                break;
        }
        if (TextUtils.isEmpty(type)) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        return type;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        int code = uriMatcher.match(uri);
        switch (code) {
            case CODE_TWEET:
                sqLiteDatabase.insert(DataBaseContract.TweetEntry.TABLE_NAME, null, values);
                return DataBaseContract.Tweets.createUri();
            case CODE_TWEET_ITEM:
                long id = sqLiteDatabase.insert(DataBaseContract.TweetEntry.TABLE_NAME, null, values);
                return DataBaseContract.Tweets.createUri(String.valueOf(id));
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public boolean onCreate() {
        DataBaseHelper helper = new DataBaseHelper(this.getContext());
        sqLiteDatabase = helper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        int code = uriMatcher.match(uri);
        Cursor query = null;
        switch (code) {
            case CODE_TWEET:
                query = sqLiteDatabase.query(DataBaseContract.TweetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CODE_TWEET_ITEM:
                String tweetId = DataBaseContract.Tweets.getId(uri);
                selection = DataBaseContract.TweetEntry._ID + "=?";
                selectionArgs = new String[]{tweetId};
                query = sqLiteDatabase.query(DataBaseContract.TweetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
        }
        if (query == null) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
        return query;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

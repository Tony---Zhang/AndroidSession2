package com.thoughtworks.wechat;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.thoughtworks.wechat.database.DataBaseContract;
import com.thoughtworks.wechat.database.DataBaseHelper;

public class MyContentProvider extends ContentProvider {
    private static final int TWEETS = 1;
    private static final int TWEETS_ID = 2;

    static UriMatcher uriMatcher = createUrlMatcher();
    private SQLiteDatabase database;


    private static UriMatcher createUrlMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(DataBaseContract.CONTENT_AUTH, "/tweets", TWEETS);
        uriMatcher.addURI(DataBaseContract.CONTENT_AUTH, "/tweets/#", TWEETS_ID);
        return uriMatcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case TWEETS_ID:
                selection = DataBaseContract.TweetEntry._ID + "=?";
                selectionArgs = new String[]{DataBaseContract.Tweets.getId(uri)};
            case TWEETS:
                return database.delete(DataBaseContract.TweetEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TWEETS:
                return "vnd.android.cursor.dir/tweet";
            case TWEETS_ID:
                return "vnd.android.cursor.item/tweet";
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case TWEETS:
                long id = database.insert(DataBaseContract.TweetEntry.TABLE_NAME, null, values);
                return DataBaseContract.Tweets.createUri(id);
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public boolean onCreate() {
        database = new DataBaseHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case TWEETS_ID:
                selection = DataBaseContract.TweetEntry._ID + "=?";
                selectionArgs = new String[]{DataBaseContract.Tweets.getId(uri)};
            case TWEETS:
                return database.query(DataBaseContract.TweetEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case TWEETS_ID:
                selection = DataBaseContract.TweetEntry._ID + "=?";
                selectionArgs = new String[]{DataBaseContract.Tweets.getId(uri)};
            case TWEETS:
                return database.update(DataBaseContract.TweetEntry.TABLE_NAME, values, selection, selectionArgs);
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}

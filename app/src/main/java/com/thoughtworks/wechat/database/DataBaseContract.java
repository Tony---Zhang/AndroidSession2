package com.thoughtworks.wechat.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import com.thoughtworks.wechat.BuildConfig;

public final class DataBaseContract {

    public final static Uri baseUri;
    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;

    static {
        baseUri = new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(CONTENT_AUTHORITY).build();
    }

    public static class Tweets {
        public static Uri createUri() {
            return baseUri.buildUpon().appendPath("tweets").build();
        }

        public static Uri createUri(String id) {
            return baseUri.buildUpon().appendPath("tweets").appendPath(id).build();
        }

        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    private DataBaseContract() {
    }

    public interface TweetEntry extends BaseColumns {
        String TABLE_NAME = "tweets";
        String COLUMN_NAME_CONTENT = "content";
        String COLUMN_NAME_IMAGES = "images";
        String COLUMN_NAME_SENDER = "sender";
        String COLUMN_NAME_COMMENTS = "comments";
        String COLUMN_NAME_ERROR = "error";
        String COLUMN_NAME_UNKNOW_ERROR = "unknow_error";
    }

    public interface UserEntry extends BaseColumns {
        String TABLE_NAME = "user";
        String COLUMN_NAME_USERNAME = "username";
        String COLUMN_NAME_NICK = "nick";
        String COLUMN_AVATAR = "avatar";
        String COLUMN_PROFILE_IMAGE = "profileImage";
    }
}

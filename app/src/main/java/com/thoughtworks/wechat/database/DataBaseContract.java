package com.thoughtworks.wechat.database;

import android.net.Uri;
import android.provider.BaseColumns;

public final class DataBaseContract {

    private DataBaseContract() {
    }

    public static final String CONTENT_AUTH = "com.thoughtworks.wechat";
    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTH);

    public static class Tweets implements TweetEntry {
        public static final Uri URI = DataBaseContract.CONTENT_URI.buildUpon().appendPath("tweets").build();


        public static Uri createUri(long tweetId) {
            return URI.buildUpon().appendPath(String.valueOf(tweetId)).build();
        }

        public static String getId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }


    public static interface TweetEntry extends BaseColumns {
        public static final String TABLE_NAME = "tweets";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_IMAGES = "images";
        public static final String COLUMN_NAME_SENDER = "sender";
        public static final String COLUMN_NAME_COMMENTS = "comments";
        public static final String COLUMN_NAME_ERROR = "error";
        public static final String COLUMN_NAME_UNKNOW_ERROR = "unknow_error";
    }

    public static interface UserEntry extends BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_NICK = "nick";
        public static final String COLUMN_AVATAR = "avatar";
        public static final String COLUMN_PROFILE_IMAGE = "profileImage";
    }
}

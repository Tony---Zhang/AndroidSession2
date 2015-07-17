package com.thoughtworks.wechat.database;

import android.provider.BaseColumns;

/**
 * Created by zywang on 7/17/15.
 */
public final class DataBaseContract {
    private DataBaseContract() {
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
}

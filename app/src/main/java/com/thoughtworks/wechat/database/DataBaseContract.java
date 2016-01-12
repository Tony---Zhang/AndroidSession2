package com.thoughtworks.wechat.database;

import android.provider.BaseColumns;

public final class DataBaseContract {

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

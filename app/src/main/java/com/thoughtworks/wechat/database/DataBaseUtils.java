package com.thoughtworks.wechat.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.wechat.model.Image;
import com.thoughtworks.wechat.model.Sender;
import com.thoughtworks.wechat.model.Tweet;
import com.thoughtworks.wechat.model.User;

import java.util.List;

/**
 * Created by zywang on 7/17/15.
 */
public class DataBaseUtils {

    private static Gson gson = new Gson();

    public static Tweet cursor2Tweet(Cursor cursor) {
        Tweet tweet = new Tweet();
        tweet.setContent(cursor.getString(cursor.getColumnIndex(DataBaseContract.TweetEntry.COLUMN_NAME_CONTENT)));
        List<Image> images = gson.fromJson(cursor.getString(cursor.getColumnIndex(DataBaseContract.TweetEntry.COLUMN_NAME_IMAGES)), new TypeToken<List<Image>>() {
        }.getType());
        tweet.setImages(images);
        List<Object> comments = gson.fromJson(cursor.getString(cursor.getColumnIndex(DataBaseContract.TweetEntry.COLUMN_NAME_COMMENTS)), new TypeToken<List<Image>>() {
        }.getType());
        tweet.setComments(comments);
        tweet.setSender(gson.fromJson(cursor.getString(cursor.getColumnIndex(DataBaseContract.TweetEntry.COLUMN_NAME_SENDER)), Sender.class));
        tweet.setError(cursor.getString(cursor.getColumnIndex(DataBaseContract.TweetEntry.COLUMN_NAME_ERROR)));
        tweet.setUnknownError(cursor.getString(cursor.getColumnIndex(DataBaseContract.TweetEntry.COLUMN_NAME_UNKNOW_ERROR)));
        return tweet;
    }

    public static ContentValues tweet2ContentValues(Tweet tweet) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseContract.TweetEntry.COLUMN_NAME_CONTENT, tweet.getContent());
        contentValues.put(DataBaseContract.TweetEntry.COLUMN_NAME_IMAGES, gson.toJson(tweet.getImages()));
        contentValues.put(DataBaseContract.TweetEntry.COLUMN_NAME_COMMENTS, gson.toJson(tweet.getComments()));
        contentValues.put(DataBaseContract.TweetEntry.COLUMN_NAME_SENDER, gson.toJson(tweet.getSender()));
        contentValues.put(DataBaseContract.TweetEntry.COLUMN_NAME_ERROR, tweet.getError());
        contentValues.put(DataBaseContract.TweetEntry.COLUMN_NAME_UNKNOW_ERROR, tweet.getUnknownError());
        return contentValues;
    }

    public static User cursor2User(Cursor cursor) {
        User user = new User();
        user.setUsername(cursor.getString(cursor.getColumnIndex(DataBaseContract.UserEntry.COLUMN_NAME_USERNAME)));
        user.setNick(cursor.getString(cursor.getColumnIndex(DataBaseContract.UserEntry.COLUMN_NAME_NICK)));
        user.setAvatar(cursor.getString(cursor.getColumnIndex(DataBaseContract.UserEntry.COLUMN_AVATAR)));
        user.setProfileImage(cursor.getString(cursor.getColumnIndex(DataBaseContract.UserEntry.COLUMN_PROFILE_IMAGE)));
        return user;
    }

    public static ContentValues user2ContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseContract.UserEntry.COLUMN_NAME_USERNAME, user.getUsername());
        contentValues.put(DataBaseContract.UserEntry.COLUMN_NAME_NICK, user.getNick());
        contentValues.put(DataBaseContract.UserEntry.COLUMN_AVATAR, user.getAvatar());
        contentValues.put(DataBaseContract.UserEntry.COLUMN_PROFILE_IMAGE, user.getProfileImage());
        return contentValues;
    }
}

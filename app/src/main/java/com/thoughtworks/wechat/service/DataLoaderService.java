package com.thoughtworks.wechat.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.common.base.Predicate;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.wechat.database.DataBaseAdapter;
import com.thoughtworks.wechat.model.Tweet;
import com.thoughtworks.wechat.model.User;
import com.thoughtworks.wechat.utils.FileUtils;

import java.util.List;

import static com.google.common.collect.FluentIterable.from;

public class DataLoaderService extends IntentService {

    public static final String TAG = DataLoaderService.class.getSimpleName();

    public static final String ACTION_FETCH = "com.thoughtworks.wechat.service.ACTION_FETCH_DATA";

    public static final String STATUS_START = "start";
    public static final String STATUS_COMPLETE = "complete";
    public static final String STATUS_FAILED = "failed";

    public static final String EXTRA_ERROR = "error";
    public static final String EXTRA_STATUS = "status";

    private String action;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public DataLoaderService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        action = intent.getAction();
        if (action.equals(ACTION_FETCH)) {
            sendStatusBroadcast(STATUS_START);
            fetchUserAndTweetList();
            sendStatusBroadcast(STATUS_COMPLETE);
        }
    }

    private void fetchUserAndTweetList() {
        try {
            DataBaseAdapter dataBaseAdapter = new DataBaseAdapter(getApplicationContext());
            String headerSource = FileUtils.readAssetTextFile(getApplicationContext(), "user.json");
            String tweetSource = FileUtils.readAssetTextFile(getApplicationContext(), "tweets.json");
            final Gson gson = new Gson();
            User user = gson.fromJson(headerSource, User.class);
            List<Tweet> tweetList = gson.fromJson(tweetSource, new TypeToken<List<Tweet>>() {
            }.getType());
            tweetList = from(tweetList).filter(new Predicate<Tweet>() {
                @Override
                public boolean apply(Tweet input) {
                    return (input.getError() == null && input.getUnknownError() == null);
                }
            }).toList();
            dataBaseAdapter.insertOrUpdateUser(user);
            dataBaseAdapter.deleteAllTweetList();
            // use transaction to optimizing performance
            dataBaseAdapter.insertTweetList(tweetList);
        } catch (Exception e) {
            sendErrorBroadcast(e);
        }
    }

    private void sendStatusBroadcast(String status) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_STATUS, status);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }

    private void sendErrorBroadcast(Exception e) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_ERROR, e.getMessage());
        intent.putExtra(EXTRA_STATUS, STATUS_FAILED);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }
}

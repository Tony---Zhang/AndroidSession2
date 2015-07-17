package com.thoughtworks.wechat;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.wechat.adapter.TweetAdapter;
import com.thoughtworks.wechat.database.DataBaseContract.TweetEntry;
import com.thoughtworks.wechat.database.DataBaseHelper;
import com.thoughtworks.wechat.database.DataBaseUtils;
import com.thoughtworks.wechat.model.Tweet;
import com.thoughtworks.wechat.model.User;
import com.thoughtworks.wechat.utils.FileUtils;
import com.thoughtworks.wechat.viewholder.TweetHeaderHolder;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SecondActivity extends AppCompatActivity {

    @InjectView(R.id.listview)
    ListView mTweetListView;
    private TweetAdapter mTweetAdapter;
    private View mHeaderView;
    private DataBaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.inject(this);

        mDataBaseHelper = new DataBaseHelper(this);
        initViews();
        initData();
    }

    private void initViews() {
        mTweetAdapter = new TweetAdapter(this, query());
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.tweet_header, mTweetListView, false);
        mTweetListView.addHeaderView(mHeaderView);
        mTweetListView.setAdapter(mTweetAdapter);
    }

    private Cursor query() {
        return mDataBaseHelper.getReadableDatabase().query(TweetEntry.TABLE_NAME, null, null, null, null, null, null);
    }

    private void initData() {
        new AsyncTask<Void, Void, User>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected User doInBackground(Void... params) {
                User user = null;
                try {
                    String headerSource = FileUtils.readAssetTextFile(SecondActivity.this, "user.json");
                    String tweetSource = FileUtils.readAssetTextFile(SecondActivity.this, "tweets.json");
                    final Gson gson = new Gson();
                    user = gson.fromJson(headerSource, User.class);
                    List<Tweet> tweetList = gson.fromJson(tweetSource, new TypeToken<List<Tweet>>() {
                    }.getType());
                    mDataBaseHelper.getWritableDatabase().delete(TweetEntry.TABLE_NAME, null, null);
                    for(Tweet tweet: tweetList) {
                        boolean noError = tweet.getError() == null && tweet.getUnknownError() == null;
                        boolean shouldDisplay = tweet.getContent() != null && tweet.getImages() != null;
                        if(noError && shouldDisplay) {
                            ContentValues contentValues = DataBaseUtils.tweet2ContentValues(tweet);
                            mDataBaseHelper.getWritableDatabase().insert(TweetEntry.TABLE_NAME, null, contentValues);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    TweetHeaderHolder holder = new TweetHeaderHolder(SecondActivity.this, mHeaderView);
                    holder.populate(user);
                }
                Cursor query = query();
                mTweetAdapter.changeCursor(query);
            }

        }.execute();
    }

}

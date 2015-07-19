package com.thoughtworks.wechat;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.common.base.Predicate;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.wechat.adapter.TweetAdapter;
import com.thoughtworks.wechat.database.DataBaseAdapter;
import com.thoughtworks.wechat.model.Tweet;
import com.thoughtworks.wechat.model.User;
import com.thoughtworks.wechat.utils.FileUtils;
import com.thoughtworks.wechat.viewholder.TweetHeaderHolder;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.google.common.collect.FluentIterable.from;

public class TweetListActivity extends AppCompatActivity {

    @InjectView(R.id.listview)
    ListView mTweetListView;
    private TweetAdapter mTweetAdapter;
    private View mHeaderView;
    private DataBaseAdapter mDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.inject(this);

        initViews();
        initData();
    }

    private void initViews() {
        mDataBaseAdapter = new DataBaseAdapter(this);
        mTweetAdapter = new TweetAdapter(this, mDataBaseAdapter.queryTweetList());
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.tweet_header, mTweetListView, false);
        mTweetListView.addHeaderView(mHeaderView);
        mTweetListView.setAdapter(mTweetAdapter);
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
                    String headerSource = FileUtils.readAssetTextFile(TweetListActivity.this, "user.json");
                    String tweetSource = FileUtils.readAssetTextFile(TweetListActivity.this, "tweets.json");
                    final Gson gson = new Gson();
                    user = gson.fromJson(headerSource, User.class);
                    List<Tweet> tweetList = gson.fromJson(tweetSource, new TypeToken<List<Tweet>>() {
                    }.getType());
                    tweetList = from(tweetList).filter(new Predicate<Tweet>() {
                        @Override
                        public boolean apply(Tweet input) {
                            return (input.getError() == null && input.getUnknownError() == null);
                        }
                    }).toList();
                    mDataBaseAdapter.deleteAllTweetList();
                    // use transaction to optimizing performance
                    mDataBaseAdapter.insertTweetList(tweetList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                if (user != null) {
                    TweetHeaderHolder holder = new TweetHeaderHolder(TweetListActivity.this, mHeaderView);
                    holder.populate(user);
                }
                mTweetAdapter.changeCursor(mDataBaseAdapter.queryTweetList());
            }

        }.execute();
    }

}

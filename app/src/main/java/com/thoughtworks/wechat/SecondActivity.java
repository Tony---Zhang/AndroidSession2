package com.thoughtworks.wechat;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.google.common.base.Predicate;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.wechat.adapter.TweetAdapter;
import com.thoughtworks.wechat.model.Tweet;
import com.thoughtworks.wechat.model.User;
import com.thoughtworks.wechat.utils.FileUtils;
import com.thoughtworks.wechat.viewholder.TweetHeaderHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.google.common.collect.FluentIterable.from;

public class SecondActivity extends AppCompatActivity {

    @InjectView(R.id.listview)
    ListView mTweetListView;
    private TweetAdapter mTweetAdapter;
    private View mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.inject(this);

        initViews();
        initData();
    }

    private void initViews() {
        mTweetAdapter = new TweetAdapter(this);
        mTweetListView.setAdapter(mTweetAdapter);
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.tweet_header, mTweetListView, false);
        mTweetListView.addHeaderView(mHeaderView);
    }

    private void initData() {
        new AsyncTask<Void, Void, Pair<User, List<Tweet>>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Pair<User, List<Tweet>> doInBackground(Void... params) {
                List<Tweet> tweetList = new ArrayList<>();
                User user = null;
                try {
                    String headerSource = FileUtils.readAssetTextFile(SecondActivity.this, "user.json");
                    String tweetSource = FileUtils.readAssetTextFile(SecondActivity.this, "tweets.json");
                    user = new Gson().fromJson(headerSource, User.class);
                    tweetList = new Gson().fromJson(tweetSource, new TypeToken<List<Tweet>>() {
                    }.getType());
                    tweetList = from(tweetList).filter(new Predicate<Tweet>() {
                        @Override
                        public boolean apply(Tweet input) {
                            return (input.getError() == null && input.getUnknownError() == null);
                        }
                    }).toList();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Pair<User, List<Tweet>> pair = new Pair<>(user, tweetList);
                return pair;
            }

            @Override
            protected void onPostExecute(Pair<User, List<Tweet>> result) {
                User user = result.first;
                List<Tweet> tweetList = result.second;
                if (user != null) {
                    TweetHeaderHolder holder = new TweetHeaderHolder(SecondActivity.this, mHeaderView);
                    holder.populate(user);
                }
                mTweetAdapter.setTweetList(tweetList);
                mTweetAdapter.notifyDataSetChanged();
            }

        }.execute();
    }
}

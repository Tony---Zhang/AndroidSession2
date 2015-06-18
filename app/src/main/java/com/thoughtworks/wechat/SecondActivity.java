package com.thoughtworks.wechat;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.common.base.Predicate;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.wechat.adapter.TweetAdapter;
import com.thoughtworks.wechat.model.Tweet;
import com.thoughtworks.wechat.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.FluentIterable.from;

public class SecondActivity extends AppCompatActivity {

    @InjectView(R.id.listview)
    ListView mTweetListView;
    private TweetAdapter mTweetAdapter;

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
    }

    private void initData() {
        new AsyncTask<Void, Void, List<Tweet>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<Tweet> doInBackground(Void... params) {
                List<Tweet> tweetList = new ArrayList<>();
                try {
                    String source = FileUtils.readAssetTextFile(SecondActivity.this, "tweets.json");
                    tweetList = new Gson().fromJson(source, new TypeToken<List<Tweet>>() {
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
                return tweetList;
            }

            @Override
            protected void onPostExecute(List<Tweet> tweetList) {
                mTweetAdapter.setTweetList(tweetList);
                mTweetAdapter.notifyDataSetChanged();
            }

        }.execute();
    }
}

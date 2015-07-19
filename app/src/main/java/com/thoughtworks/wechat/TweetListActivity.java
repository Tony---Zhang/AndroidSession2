package com.thoughtworks.wechat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.thoughtworks.wechat.adapter.TweetAdapter;
import com.thoughtworks.wechat.database.DataBaseAdapter;
import com.thoughtworks.wechat.database.DataBaseUtils;
import com.thoughtworks.wechat.model.User;
import com.thoughtworks.wechat.service.DataLoaderService;
import com.thoughtworks.wechat.viewholder.TweetHeaderHolder;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.thoughtworks.wechat.database.DataBaseAdapter.closeQuietly;

public class TweetListActivity extends AppCompatActivity {

    class DataLoaderBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(DataLoaderService.ACTION_FETCH)) {
                final String status = intent.getStringExtra(DataLoaderService.EXTRA_STATUS);
                if (status.equals(DataLoaderService.STATUS_COMPLETE)) {
                    Cursor userCursor = mDataBaseAdapter.queryUser();
                    if (userCursor != null && userCursor.moveToFirst()) {
                        User user = DataBaseUtils.cursor2User(userCursor);
                        TweetHeaderHolder holder = new TweetHeaderHolder(TweetListActivity.this, mHeaderView);
                        holder.populate(user);
                        closeQuietly(userCursor);
                    }
                    mTweetAdapter.changeCursor(mDataBaseAdapter.queryTweetList());
                } else if (status.equals(DataLoaderService.STATUS_START)) {
                    Toast.makeText(context, "Start fetch!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, intent.getStringExtra(DataLoaderService.EXTRA_ERROR), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @InjectView(R.id.listview)
    ListView mTweetListView;

    private TweetAdapter mTweetAdapter;
    private View mHeaderView;
    private DataBaseAdapter mDataBaseAdapter;
    private DataLoaderBroadcastReceiver mDataLoaderReceiver = new DataLoaderBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweetlist_activity);
        ButterKnife.inject(this);

        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(DataLoaderService.ACTION_FETCH);
        LocalBroadcastManager.getInstance(this).registerReceiver(mDataLoaderReceiver, filter);

        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDataLoaderReceiver);
    }

    private void initViews() {
        mDataBaseAdapter = new DataBaseAdapter(this);
        mTweetAdapter = new TweetAdapter(this, mDataBaseAdapter.queryTweetList());
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.tweet_header, mTweetListView, false);
        mTweetListView.addHeaderView(mHeaderView);
        mTweetListView.setAdapter(mTweetAdapter);
    }

    private void initData() {
        Intent service = new Intent(DataLoaderService.ACTION_FETCH);
        service.setClass(this, DataLoaderService.class);
        startService(service);
    }

}

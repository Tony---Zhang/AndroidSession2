package com.thoughtworks.wechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.model.Tweet;
import com.thoughtworks.wechat.viewholder.TweetItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TweetAdapter extends BaseAdapter {

    private Context mContext;
    private List<Tweet> mTweetList;

    public TweetAdapter(Context context) {
        this.mContext = context;
        this.mTweetList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mTweetList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTweetList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TweetItemViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tweet_item_view, parent, false);
            holder = new TweetItemViewHolder(mContext, convertView);
            convertView.setTag(holder);
        }
        holder = (TweetItemViewHolder) convertView.getTag();
        holder.populate((Tweet) getItem(position));
        return convertView;
    }

    public void setTweetList(List<Tweet> tweetList) {
        this.mTweetList = tweetList;
    }
}

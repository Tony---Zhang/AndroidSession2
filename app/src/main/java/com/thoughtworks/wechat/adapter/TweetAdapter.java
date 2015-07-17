package com.thoughtworks.wechat.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.google.gson.Gson;
import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.database.DataBaseUtils;
import com.thoughtworks.wechat.model.Tweet;
import com.thoughtworks.wechat.viewholder.TweetItemViewHolder;

public class TweetAdapter extends CursorAdapter {

    private Gson gson;

    public TweetAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
        gson = new Gson();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.tweet_item_view, parent, false);
        TweetItemViewHolder holder = new TweetItemViewHolder(context, convertView);
        convertView.setTag(holder);
        return convertView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TweetItemViewHolder holder = (TweetItemViewHolder) view.getTag();

        Tweet tweet = DataBaseUtils.cursor2Tweet(cursor);

        holder.populate(tweet);
    }

}

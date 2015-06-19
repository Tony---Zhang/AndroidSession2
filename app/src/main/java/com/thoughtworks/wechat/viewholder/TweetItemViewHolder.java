package com.thoughtworks.wechat.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.model.Sender;
import com.thoughtworks.wechat.model.Tweet;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TweetItemViewHolder {

    @InjectView(R.id.tweet_sender_icon)
    ImageView mIconImage;
    @InjectView(R.id.tweet_sender_name)
    TextView mSenderNameText;
    @InjectView(R.id.tweet_content)
    TextView mContentText;
    private Context mContext;

    public TweetItemViewHolder(Context context, View view) {
        mContext = context;
        ButterKnife.inject(this, view);
    }

    public void populate(Tweet tweet) {
        Sender sender = tweet.getSender();
        Glide.with(mContext).load(sender.getAvatar())
                .placeholder(R.mipmap.ic_launcher).crossFade().into(mIconImage);
        mSenderNameText.setText(sender.getNick());
        mContentText.setText(tweet.getContent());
    }
}

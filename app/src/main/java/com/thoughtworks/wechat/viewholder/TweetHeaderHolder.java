package com.thoughtworks.wechat.viewholder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.thoughtworks.wechat.R;
import com.thoughtworks.wechat.model.User;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TweetHeaderHolder {

    @InjectView(R.id.header_bg)
    ImageView mProfileImage;
    @InjectView(R.id.user_avatar)
    ImageView mAvatarImage;
    @InjectView(R.id.user_nick)
    TextView mUserNickText;
    private Context context;

    public TweetHeaderHolder(Context context, View view) {
        this.context = context;
        ButterKnife.inject(this, view);
    }

    public void populate(User user) {
        Glide.with(context).load(user.getProfileImage()).into(mProfileImage);
        Glide.with(context).load(user.getAvatar()).placeholder(R.mipmap.ic_launcher).crossFade().into(mAvatarImage);
        mUserNickText.setText(user.getNick());
    }
}

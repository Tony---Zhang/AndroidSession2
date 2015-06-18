package com.thoughtworks.wechat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("profile-image")
    @Expose
    private String profileImage;
    @Expose
    private String avatar;
    @Expose
    private String nick;
    @Expose
    private String username;

    /**
     *
     * @return
     * The profileImage
     */
    public String getProfileImage() {
        return profileImage;
    }

    /**
     *
     * @param profileImage
     * The profile-image
     */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    /**
     *
     * @return
     * The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     *
     * @param avatar
     * The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     *
     * @return
     * The nick
     */
    public String getNick() {
        return nick;
    }

    /**
     *
     * @param nick
     * The nick
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

}

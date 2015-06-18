
package com.thoughtworks.wechat.model;

import com.google.gson.annotations.Expose;

public class Sender {

    @Expose
    private String username;
    @Expose
    private String nick;
    @Expose
    private String avatar;

    /**
     * 
     * @return
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 
     * @return
     *     The nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * 
     * @param nick
     *     The nick
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * 
     * @return
     *     The avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 
     * @param avatar
     *     The avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}

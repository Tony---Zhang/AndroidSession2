
package com.thoughtworks.wechat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Tweet {

    @Expose
    private String content;
    @Expose
    private List<Image> images = new ArrayList<Image>();
    @Expose
    private Sender sender;
    @Expose
    private List<Object> comments = new ArrayList<Object>();
    @Expose
    private String error;
    @SerializedName("unknown error")
    @Expose
    private String unknownError;

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * 
     * @param images
     *     The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     * 
     * @return
     *     The sender
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * 
     * @param sender
     *     The sender
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    /**
     * 
     * @return
     *     The comments
     */
    public List<Object> getComments() {
        return comments;
    }

    /**
     * 
     * @param comments
     *     The comments
     */
    public void setComments(List<Object> comments) {
        this.comments = comments;
    }

    /**
     * 
     * @return
     *     The error
     */
    public String getError() {
        return error;
    }

    /**
     * 
     * @param error
     *     The error
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * 
     * @return
     *     The unknownError
     */
    public String getUnknownError() {
        return unknownError;
    }

    /**
     * 
     * @param unknownError
     *     The unknown error
     */
    public void setUnknownError(String unknownError) {
        this.unknownError = unknownError;
    }

}

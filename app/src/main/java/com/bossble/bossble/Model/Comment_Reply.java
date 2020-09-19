package com.bossble.bossble.Model;

/**
 * Created by Fawad on 6/9/2020.
 */

public class Comment_Reply {

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String user_id;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_country() {
        return user_country;
    }

    public void setUser_country(String user_country) {
        this.user_country = user_country;
    }

    public String getUser_comment_reply() {
        return user_comment_reply;
    }

    public void setUser_comment_reply(String user_comment_reply) {
        this.user_comment_reply = user_comment_reply;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String user_name;
    public String user_country;
    public String user_comment_reply;
    public String user_image;

    public String getCommentt_id() {
        return commentt_id;
    }

    public void setCommentt_id(String commentt_id) {
        this.commentt_id = commentt_id;
    }

    public String getChallenge_id() {
        return challenge_id;
    }

    public void setChallenge_id(String challenge_id) {
        this.challenge_id = challenge_id;
    }

    public String getReply_id() {
        return reply_id;
    }

    public void setReply_id(String reply_id) {
        this.reply_id = reply_id;
    }

    public String commentt_id;
    public String challenge_id;
    public String reply_id;




}

package com.bossble.bossble.Model;

import java.util.ArrayList;

/**
 * Created by Fawad on 6/6/2020.
 */

public class Comments {

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUserreply(String userreply) {
        this.userreply = userreply;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsercomment() {
        return usercomment;
    }

    public void setUsercomment(String usercomment) {
        this.usercomment = usercomment;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    public String userid;
    public String username;
    public String usercomment;
    public String userimage;
    public String userreply;
    public ArrayList<String> list;

    public String getUserreply() {
        return userreply;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
    }

    public String getChallenge_id() {
        return challenge_id;
    }

    public void setChallenge_id(String challenge_id) {
        this.challenge_id = challenge_id;
    }

    public String challenge_id;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String comment_id;


}

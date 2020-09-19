package com.bossble.bossble.Model;

import java.util.ArrayList;

public class Campaigns {
    public String id;

    public ArrayList<String> campaign_images;

    public ArrayList<String> getCampaign_images() {
        return campaign_images;
    }

    public void setCampaign_images(ArrayList<String> campaign_images) {
        this.campaign_images = campaign_images;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getDescription_background() {
        return description_background;
    }

    public void setDescription_background(String description_background) {
        this.description_background = description_background;
    }

    public String getDescription_fonts() {
        return description_fonts;
    }

    public void setDescription_fonts(String description_fonts) {
        this.description_fonts = description_fonts;
    }

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public String getLike_count() {
        return like_count;
    }

    public void setLike_count(String like_count) {
        this.like_count = like_count;
    }

    public String getChallenge_attempted_count() {
        return challenge_attempted_count;
    }

    public void setChallenge_attempted_count(String challenge_attempted_count) {
        this.challenge_attempted_count = challenge_attempted_count;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String user_id;
    public String title;
    public String descritpion;
    public String tags;
    public String created_at;
    public String username;
    public String profile_image;
    public String description_background;
    public String description_fonts;
    public String comments_count;
    public String like_count;
    public String challenge_attempted_count;
    public String video;
    public String image;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String country;

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String view_count;

}

package com.example.fitnessapp;

public class NewsfeedModel {

    String username, userImage, description, imageUri;

    public NewsfeedModel() {

    }

    public NewsfeedModel(String username, String userImage, String description, String imageUri) {
        this.username = username;
        this.userImage = userImage;
        this.description = description;
        this.imageUri = imageUri;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostImage() {
        return imageUri;
    }

    public void setPostImage(String imageUri) {
        this.imageUri = imageUri;
    }

}

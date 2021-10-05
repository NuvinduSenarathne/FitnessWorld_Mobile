package com.example.fitnessapp;

public class CommentModel {

    String postID;
    String userID;
    String txtComment;

    public CommentModel() {

    }

    public CommentModel(String postID, String userID, String txtComment) {
        this.postID = postID;
        this.userID = userID;
        this.txtComment = txtComment;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String userID) {
        this.postID = postID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTxtComment() {
        return txtComment;
    }

    public void setTxtComment(String txtComment) {
        this.txtComment = txtComment;
    }

}

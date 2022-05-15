package com.example.task81c.model;

public class User {
    private int userId;
    private String userFullName;
    private String userName;
    private String userPassword;

    public User(String userFullName, String userName, String userPassword) {
        this.userFullName = userFullName;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User(int userId, String userFullName, String userName, String userPassword) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserFullName(String userFullName) {
        this.userName = userFullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}

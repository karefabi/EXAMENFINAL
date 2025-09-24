package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    @SerializedName("results")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
}

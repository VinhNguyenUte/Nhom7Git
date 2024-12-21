package com.example.btnhom7.Model;

import java.io.Serializable;

public class UserModel implements Serializable {
    private Long userId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private String avatar;
    private String createdAt;

    public UserModel(){

    }
    public UserModel(Long userId, String name, String email, String phoneNumber, String password, String avatarResource, String createdAt) {
        this.userId = userId;
        this.fullName = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.avatar = avatarResource;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getName() { return fullName; }
    public void setName(String name) { this.fullName = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatarResource) { this.avatar = avatarResource; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}

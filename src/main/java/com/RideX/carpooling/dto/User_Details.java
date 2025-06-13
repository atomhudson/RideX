package com.RideX.carpooling.dto;

import java.util.Date;

public class User_Details {
    private int age;
    private int coins;
    private Date joined_Date;
    private String userId;
    private String name;
    private String profileUrl;
    private String bio;
    private String gender;
    private String occupation;
    private String spokenLanguages;
    private String city;
    private String email;
    private String phone;
    private String govtIdType;
    private String verify_Request;
    private String role;
    private boolean isPhoneVerified;
    private boolean emailVerified;
    private boolean govtIdVerified;
    private boolean musicPreference;
    private boolean petsAllowed;
    private boolean smokingAllowed;
    private boolean talkative;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getJoined_Date() {
        return joined_Date;
    }

    public void setJoined_Date(Date joined_Date) {
        this.joined_Date = joined_Date;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(String spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isMusicPreference() {
        return musicPreference;
    }

    public void setMusicPreference(boolean musicPreference) {
        this.musicPreference = musicPreference;
    }

    public boolean isPetsAllowed() {
        return petsAllowed;
    }

    public void setPetsAllowed(boolean petsAllowed) {
        this.petsAllowed = petsAllowed;
    }

    public boolean isSmokingAllowed() {
        return smokingAllowed;
    }

    public void setSmokingAllowed(boolean smokingAllowed) {
        this.smokingAllowed = smokingAllowed;
    }

    public boolean isTalkative() {
        return talkative;
    }

    public void setTalkative(boolean talkative) {
        this.talkative = talkative;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPhoneVerified() {
        return isPhoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        isPhoneVerified = phoneVerified;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isGovtIdVerified() {
        return govtIdVerified;
    }

    public void setGovtIdVerified(boolean govtIdVerified) {
        this.govtIdVerified = govtIdVerified;
    }

    public String getGovtIdType() {
        return govtIdType;
    }

    public void setGovtIdType(String govtIdType) {
        this.govtIdType = govtIdType;
    }

    public String getVerify_Request() {
        return verify_Request;
    }

    public void setVerify_Request(String verify_Request) {
        this.verify_Request = verify_Request;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

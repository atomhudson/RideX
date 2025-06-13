package com.RideX.carpooling.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "user_details")
public class Details {
    @Id
    private String details_id;
    private String bio;
    private int age;
    private String gender;
    private String occupation;
    private String spokenLanguages;
    private String city;
    private boolean musicPreference;
    private boolean petsAllowed;
    private boolean smokingAllowed;
    private boolean talkative;
    private String dateOfBirth;
    private int completedRides;
    private int cancelledRides;
    private Date lastRideDate;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", nullable = false, unique = true)
    private User user;

    public String getDetails_id() {
        return details_id;
    }

    public void setDetails_id(String details_id) {
        this.details_id = details_id;
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

    public int getCompletedRides() {
        return completedRides;
    }

    public void setCompletedRides(int completedRides) {
        this.completedRides = completedRides;
    }

    public int getCancelledRides() {
        return cancelledRides;
    }

    public void setCancelledRides(int cancelledRides) {
        this.cancelledRides = cancelledRides;
    }

    public Date getLastRideDate() {
        return lastRideDate;
    }

    public void setLastRideDate(Date lastRideDate) {
        this.lastRideDate = lastRideDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Details{" +
                "details_id='" + details_id + '\'' +
                ", bio='" + bio + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", occupation='" + occupation + '\'' +
                ", spokenLanguages='" + spokenLanguages + '\'' +
                ", city='" + city + '\'' +
                ", musicPreference=" + musicPreference +
                ", petsAllowed=" + petsAllowed +
                ", smokingAllowed=" + smokingAllowed +
                ", talkative=" + talkative +
                ", completedRides=" + completedRides +
                ", cancelledRides=" + cancelledRides +
                ", lastRideDate=" + lastRideDate +
                '}';
    }

    public Details() {
    }
}

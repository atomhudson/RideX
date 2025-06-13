package com.RideX.carpooling.forms;

import com.RideX.carpooling.validators.ValidFile;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class DetailForm {

    private String fname;
    private String lname;
    private String bio;
    @NotBlank(message = "Date of Birth Required")
    private String dateOfBirth;
    private int age;
    @NotBlank(message = "Gender is Required")
    private String gender;
    private String occupation;
    private String spokenLanguages;
    @NotBlank(message = "City is Required")
    private String city;
    private boolean musicPreference;
    private boolean petsAllowed;
    private boolean smokingAllowed;
    private boolean talkative;

    @ValidFile(message = "Invalid File")
    private MultipartFile userImage;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public @NotBlank(message = "Date of Birth Required") String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NotBlank(message = "Date of Birth Required") String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public @NotBlank(message = "Gender is Required") String getGender() {
        return gender;
    }

    public void setGender(@NotBlank(message = "Gender is Required") String gender) {
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

    public @NotBlank(message = "City is Required") String getCity() {
        return city;
    }

    public void setCity(@NotBlank(message = "City is Required") String city) {
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

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public MultipartFile getUserImage() {
        return userImage;
    }

    public void setUserImage(MultipartFile userImage) {
        this.userImage = userImage;
    }

    @Override
    public String toString() {
        return "DetailForm{" +
                "bio='" + bio + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", occupation='" + occupation + '\'' +
                ", spokenLanguages='" + spokenLanguages + '\'' +
                ", city='" + city + '\'' +
                ", musicPreference=" + musicPreference +
                ", petsAllowed=" + petsAllowed +
                ", smokingAllowed=" + smokingAllowed +
                ", talkative=" + talkative +
                '}';
    }
}

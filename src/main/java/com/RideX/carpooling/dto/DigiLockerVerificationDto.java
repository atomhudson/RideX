package com.RideX.carpooling.dto;

import java.util.Date;

/**
 * DTO representing extracted and verified identity document data from DigiLocker.
 */
public class DigiLockerVerificationDto {

    private String docType;
    private String docNumber;
    private String fullName;
    private String dob;
    private String gender;
    private String issuer;
    private String photoUrl;
    private Date verifiedAt;
    private String verificationStatus;
    private String otp;

    public DigiLockerVerificationDto() {
        this.verifiedAt = new Date();
        this.verificationStatus = "SUCCESS";
    }

    public DigiLockerVerificationDto(String docType, String docNumber, String fullName, String dob, String gender, String issuer) {
        this.docType = docType;
        this.docNumber = docNumber;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.issuer = issuer;
        this.verifiedAt = new Date();
        this.verificationStatus = "SUCCESS";
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Date getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Date verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

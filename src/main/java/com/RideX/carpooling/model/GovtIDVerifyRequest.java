package com.RideX.carpooling.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class GovtIDVerifyRequest {
    @Id
    @Column(name = "request_id")
    private String requestId;
    @Column(name = "user_id",nullable = false, unique = true)
    private String userId;
    @Column(name = "govt_Id_unique_number")
    private String govtIdUniqueNumber;
    @Column(name = "is_verified")
    private boolean verified;
    @Column(name = "govt_id_image_url")
    private String govtIdImageUrl;
    @Column(name = "govt_image_public_id")
    private String govtIdImagePublicId;
    @Column(name = "govt_id_type")
    private String govtIdType;

    private boolean updated;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGovtIdUniqueNumber() {
        return govtIdUniqueNumber;
    }

    public void setGovtIdUniqueNumber(String govtIdUniqueNumber) {
        this.govtIdUniqueNumber = govtIdUniqueNumber;
    }

    public boolean getVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getGovtIdImageUrl() {
        return govtIdImageUrl;
    }

    public void setGovtIdImageUrl(String govtIdImageUrl) {
        this.govtIdImageUrl = govtIdImageUrl;
    }

    public String getGovtIdImagePublicId() {
        return govtIdImagePublicId;
    }

    public void setGovtIdImagePublicId(String govtIdImagePublicId) {
        this.govtIdImagePublicId = govtIdImagePublicId;
    }

    public String getGovtIdType() {
        return govtIdType;
    }

    public void setGovtIdType(String govtIdType) {
        this.govtIdType = govtIdType;
    }

    public boolean isVerified() {
        return verified;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "GovtIDVerifyRequest{" +
                "requestId='" + requestId + '\'' +
                ", userId='" + userId + '\'' +
                ", govtIdUniqueNumber='" + govtIdUniqueNumber + '\'' +
                ", verified=" + verified +
                ", govtIdImageUrl='" + govtIdImageUrl + '\'' +
                ", govtIdImagePublicId='" + govtIdImagePublicId + '\'' +
                ", govtIdType='" + govtIdType + '\'' +
                '}';
    }
}

package com.RideX.carpooling.model;

import com.RideX.carpooling.helpers.Providers;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "user")
@Table(name = "users")
@NamedEntityGraph(
        name = "User.carDetailsAndUserDetails",
        attributeNodes = {
                @NamedAttributeNode("carDetails"),
                @NamedAttributeNode("user_details")
        }
)
public class User implements UserDetails{
    @Id
    private String userId;

    @Column(name ="first_name", nullable = false)
    private String fName;

    @Column(name ="last_name", nullable = false)
    private String lName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    private String password;

    private boolean enabled = false;

    private boolean emailVerified = false;

    private boolean phoneVerified = false;

    private String profilePic;

    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;

    private String providerUserId;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

    private String emailToken;

    private Date dateCreate;

    private Date dateUpdate;

    private String cloudinaryImagePublicId;

    @Column(name = "govt_id_verified")
    private boolean govtIdVerified;

    @Column(name = "govt_id_upload")
    private String govtIdUpload;

    @Column(name = "govt_id_type")
    private String govtIdType;

    @Column(name = "govt_id_number")
    private String govtIdNumber;

    @Column(name = "verify_request_id")
    private String govtIdVerifyRequest;

    @Column(name = "coins", nullable = false)
    private int coins = 0;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CarDetails carDetails;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Details user_details;

    @ManyToMany(mappedBy = "passengers")
    private List<Rides> ridesJoined = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<RideRequest> rideRequests;

    public List<Rides> getRidesJoined() {
        return ridesJoined;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles = roleList
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public CarDetails getCarDetails() {
        return carDetails;
    }

    public void setCarDetails(CarDetails carDetails) {
        this.carDetails = carDetails;
    }

    public Details getUser_details() {
        return user_details;
    }

    public void setUser_details(Details user_details) {
        this.user_details = user_details;
    }

    public Providers getProvider() {
        return provider;
    }

    public void setProvider(Providers provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getCloudinaryImagePublicId() {
        return cloudinaryImagePublicId;
    }

    public void setCloudinaryImagePublicId(String cloudinaryImagePublicId) {
        this.cloudinaryImagePublicId = cloudinaryImagePublicId;
    }

    public boolean isGovtIdVerified() {
        return govtIdVerified;
    }

    public void setGovtIdVerified(boolean govtIdVerified) {
        this.govtIdVerified = govtIdVerified;
    }

    public String getGovtIdUpload() {
        return govtIdUpload;
    }

    public void setGovtIdUpload(String govtIdUpload) {
        this.govtIdUpload = govtIdUpload;
    }

    public List<RideRequest> getRideRequests() {
        return rideRequests;
    }

    public void setRideRequests(List<RideRequest> rideRequests) {
        this.rideRequests = rideRequests;
    }

    public String getGovtIdType() {
        return govtIdType;
    }

    public void setGovtIdType(String govtIdType) {
        this.govtIdType = govtIdType;
    }

    public String getGovtIdNumber() {
        return govtIdNumber;
    }

    public void setGovtIdNumber(String govtIdNumber) {
        this.govtIdNumber = govtIdNumber;
    }

    public String getGovtIdVerifyRequest() {
        return govtIdVerifyRequest;
    }

    public void setGovtIdVerifyRequest(String govtIdVerifyRequest) {
        this.govtIdVerifyRequest = govtIdVerifyRequest;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public User() {}
}

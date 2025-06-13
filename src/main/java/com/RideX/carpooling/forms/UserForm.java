package com.RideX.carpooling.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserForm {

    @NotBlank(message = "First is required")
    @Size(min = 3, message = "Min 3 Characters is required")
    private String firstName;

    @NotBlank(message = "First is required")
    @Size(min = 3, message = "Min 3 Characters is required")
    private String lastName;

    @Email(message = "Invalid Email Address")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 8, max = 12, message = "Invalid Phone Number")
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Min 6 Characters is required")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    public @NotBlank(message = "First is required") @Size(min = 3, message = "Min 3 Characters is required") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank(message = "First is required") @Size(min = 3, message = "Min 3 Characters is required") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank(message = "First is required") @Size(min = 3, message = "Min 3 Characters is required") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank(message = "First is required") @Size(min = 3, message = "Min 3 Characters is required") String lastName) {
        this.lastName = lastName;
    }

    public @Email(message = "Invalid Email Address") @NotBlank(message = "Email is required") String getEmail() {
        return email;
    }

    public void setEmail(@Email(message = "Invalid Email Address") @NotBlank(message = "Email is required") String email) {
        this.email = email;
    }

    public @Size(min = 8, max = 12, message = "Invalid Phone Number") String getPhone() {
        return phone;
    }

    public void setPhone(@Size(min = 8, max = 12, message = "Invalid Phone Number") String phone) {
        this.phone = phone;
    }

    public @NotBlank(message = "Password is required") @Size(min = 6, message = "Min 6 Characters is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") @Size(min = 6, message = "Min 6 Characters is required") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Password is required") String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank(message = "Password is required") String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}

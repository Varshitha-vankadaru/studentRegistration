package com.practice.training.dao;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class StudentDao {
    @NotNull(message = "First Name cannot be Null")
    @Pattern(regexp = "[A-Za-z ]+${2,30}", message = "First Name must contain only letters and spaces")
    private String firstName;
    @NotNull(message="Last Name cannot be null")
    @Pattern(regexp = "[A-Za-z ]+${2,30}", message = "Last Name must contain only letters and spaces")
    private String lastName;
    @NotNull(message="email cannot be null")
    @Email(message = "valid email must be entered")
    private String email;
    @NotNull(message = "phone Number cannot be Null")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
    @NotNull(message = "Address cannot be NUll")
    private String address;
}

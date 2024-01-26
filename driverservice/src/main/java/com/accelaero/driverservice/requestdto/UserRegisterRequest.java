package com.accelaero.driverservice.requestdto;

import com.accelaero.driverservice.util.CarType;
import com.accelaero.driverservice.validator.PasswordMatches;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@PasswordMatches
public class UserRegisterRequest {

    @NotNull(message = "First name cannot be null")
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    private String matchingPassword;

    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Phone number cannot be null")
    @NotEmpty(message = "Phone number cannot be empty")
    private String phone;

    @Min(value = 1, message = "Invalid current location ID")
    private long currentLocationId;

    @NotNull(message = "License plate number cannot be null")
    @NotEmpty(message = "License plate number cannot be empty")
    private String licPlateNo;

    @Min(value = 1, message = "Invalid car type.")
    private int carType;



    private String carDescription;
    private double longitude;
    private double latitude;

}

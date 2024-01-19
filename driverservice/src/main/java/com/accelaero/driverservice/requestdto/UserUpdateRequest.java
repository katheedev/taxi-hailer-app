package com.accelaero.driverservice.requestdto;

import com.accelaero.driverservice.validator.PasswordMatches;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserUpdateRequest {

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotEmpty
    @NotNull
    private String phone;



}

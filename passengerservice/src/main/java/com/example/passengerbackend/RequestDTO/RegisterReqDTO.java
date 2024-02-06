package com.example.passengerbackend.RequestDTO;

import com.example.passengerbackend.Validator.ValidEmail;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterReqDTO {


    private Long id;

    @NotNull
    @NotEmpty
    private String first_name;

    @NotNull
    @NotEmpty
    private  String last_name;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String password;
    
}

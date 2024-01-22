package com.example.passengerbackend.RequestDTO;

import com.example.passengerbackend.Validator.ValidEmail;
import lombok.Getter;
import lombok.Setter;

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
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    private String password;


    @Override
    public String toString() {
        return "PassengerDTO{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

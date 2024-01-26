package com.example.passengerbackend.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PassengerEditReqDTO {

    @NotNull
    @NotEmpty
    private String first_name;

    @NotNull
    @NotEmpty
    private  String last_name;

}

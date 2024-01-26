package com.example.passengerbackend.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassengerEditResDTO {

    private String email;
    private String first_name;
    private  String last_name;

}

package com.example.passengerbackend.ResponseDTO;


import com.example.passengerbackend.Entity.Passenger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResDTO {
    private String token;
    private Passenger passenger;

}

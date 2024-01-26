package com.example.passengerbackend.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResDTO {
    private String email;
    private String token;

}

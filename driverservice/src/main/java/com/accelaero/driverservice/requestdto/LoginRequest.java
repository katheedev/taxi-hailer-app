package com.accelaero.driverservice.requestdto;

import com.accelaero.driverservice.validator.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}


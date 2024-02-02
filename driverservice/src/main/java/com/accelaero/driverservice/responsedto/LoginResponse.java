package com.accelaero.driverservice.responsedto;

import com.accelaero.driverservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private User user;
}

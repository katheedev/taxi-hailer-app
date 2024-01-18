package com.accelaero.driverservice.service;

import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.requestdto.UserRegisterRequest;

public interface IUserService {
    User registerNewUserAccount(UserRegisterRequest userDto);
}
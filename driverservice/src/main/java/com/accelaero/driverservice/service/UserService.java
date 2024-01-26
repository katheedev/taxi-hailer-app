package com.accelaero.driverservice.service;

import com.accelaero.driverservice.ResponseDTO.CommonResponse;
import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.entity.VerificationToken;
import com.accelaero.driverservice.requestdto.UserUpdateRequest;
import com.accelaero.driverservice.requestdto.UserRegisterRequest;
import com.accelaero.driverservice.responsedto.UserResponse;

public interface UserService {
    User registerNewUserAccount(UserRegisterRequest userDto);

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    UserResponse editUser (UserUpdateRequest registerRequest);

    User getLoggedInDriver();

    CommonResponse availabilityChange(String availability);

    CommonResponse locationChange (long id) ;

    }
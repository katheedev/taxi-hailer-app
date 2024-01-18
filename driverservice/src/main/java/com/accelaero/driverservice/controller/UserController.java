package com.accelaero.driverservice.controller;

import com.accelaero.driverservice.exception.UserAlreadyExistException;
import com.accelaero.driverservice.requestdto.UserRegisterRequest;
import com.accelaero.driverservice.serviceimpl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.accelaero.driverservice.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/api")
public class UserController {

    public final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @PostMapping("/registration")
    public ResponseEntity<User> registerUserAccount(

            @RequestBody @Valid UserRegisterRequest userDto,
            HttpServletRequest request,
            Errors errors) {
        User registered = null;
        try {
             registered = userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException uaeEx) {
            return new ResponseEntity(registered,HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(registered,HttpStatus.CREATED);

    }
}
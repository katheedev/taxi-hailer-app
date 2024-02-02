package com.accelaero.driverservice.controller;

import com.accelaero.driverservice.config.JwtUtil;
import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.requestdto.LoginRequest;
import com.accelaero.driverservice.responsedto.LoginResponse;
import com.accelaero.driverservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;


    private JwtUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;

    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginReq)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String email = authentication.getName();
            User user = this.userService.getUserByEmail(email);
            String token = jwtUtil.createToken(user);
            LoginResponse loginRes = new LoginResponse(token,user);
            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
                throw e;
            //ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
          //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("INVALID CREDENTIALS");
        }catch (InternalAuthenticationServiceException e){
            throw new  BadCredentialsException("Invalid Email");
            //   ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
           // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
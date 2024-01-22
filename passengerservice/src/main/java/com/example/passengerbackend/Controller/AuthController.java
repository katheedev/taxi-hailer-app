package com.example.passengerbackend.Controller;


import com.example.passengerbackend.Auth.JWTUtil;
import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.RequestDTO.LoginReqDTO;
import com.example.passengerbackend.ResponseDTO.LoginResDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<LoginResDTO> login(@RequestBody LoginReqDTO loginReqDto)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword()));
            String email = authentication.getName();
            Passenger passenger = new Passenger();
            passenger.setEmail(email);
            String token = jwtUtil.createToken(passenger);
            LoginResDTO loginResDto = new LoginResDTO(email,token);

            return ResponseEntity.ok(loginResDto);

        }catch (BadCredentialsException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }
}

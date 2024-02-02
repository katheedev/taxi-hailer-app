package com.example.passengerbackend.Controller;


import com.example.passengerbackend.Auth.JWTUtil;
import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.RequestDTO.LoginReqDTO;
import com.example.passengerbackend.ResponseDTO.LoginResDTO;
import com.example.passengerbackend.Service.PassengerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PassengerService passengerService;
    private JWTUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager, PassengerService passengerService, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.passengerService = passengerService;
        this.jwtUtil = jwtUtil;

    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<LoginResDTO> login(@RequestBody LoginReqDTO loginReqDto)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReqDto.getEmail(), loginReqDto.getPassword()));
            String email = authentication.getName();
            Passenger passenger = this.passengerService.getPassengerByEmail(email);
            String token = jwtUtil.createToken(passenger);
            LoginResDTO loginResDto = new LoginResDTO(token, passenger);

            return ResponseEntity.ok(loginResDto);

        }catch (BadCredentialsException e){
            throw e;
        }catch (Exception e){
            throw e;
        }
    }
}

package com.accelaero.driverservice.service.serviceimpl;

import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.entity.VerificationToken;
import com.accelaero.driverservice.exception.UserAlreadyExistException;
import com.accelaero.driverservice.repository.UserRepository;
import com.accelaero.driverservice.repository.VerficationTokenRegistry;
import com.accelaero.driverservice.requestdto.UserRegisterRequest;
import com.accelaero.driverservice.requestdto.UserUpdateRequest;
import com.accelaero.driverservice.responsedto.UserResponse;
import com.accelaero.driverservice.service.TripService;
import com.accelaero.driverservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
 public class TripServiceImpl implements TripService {
    @Autowired
    private UserRepository userRepository;


}
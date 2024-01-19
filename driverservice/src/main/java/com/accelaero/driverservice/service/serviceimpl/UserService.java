package com.accelaero.driverservice.service.serviceimpl;

import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.entity.VerificationToken;
import com.accelaero.driverservice.exception.UserAlreadyExistException;
import com.accelaero.driverservice.repository.UserRepository;
import com.accelaero.driverservice.repository.VerficationTokenRegistry;
import com.accelaero.driverservice.requestdto.UserUpdateRequest;
import com.accelaero.driverservice.requestdto.UserRegisterRequest;
import com.accelaero.driverservice.responsedto.UserResponse;
import com.accelaero.driverservice.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
 public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerficationTokenRegistry tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerNewUserAccount(UserRegisterRequest userDto) throws UserAlreadyExistException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }

        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
     //   user.setRoles(Arrays.asList("ROLE_USER"));

        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmailIgnoreCase(email) != null;
    }

    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public UserResponse editUser(UserUpdateRequest updateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        User user = userRepository.findByEmailIgnoreCase(email);

        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setPhone(updateRequest.getPhone());

        user = userRepository.save(user);
        return userResponseConverer(user);
    }

    @Override
    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(null,token, user);
        tokenRepository.save(myToken);
    }

    private UserResponse userResponseConverer(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setFirstname(user.getFirstName());
        userResponse.setLastname(user.getLastName());
        userResponse.setPhone(user.getPhone());

        return userResponse;
    }
}
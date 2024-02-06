package com.accelaero.driverservice.service.serviceimpl;

import com.accelaero.driverservice.responsedto.CommonResponse;
import com.accelaero.driverservice.entity.Car;
import com.accelaero.driverservice.entity.Location;
import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.entity.VerificationToken;
import com.accelaero.driverservice.exception.InvalidInputException;
import com.accelaero.driverservice.exception.LocationNotFound;
import com.accelaero.driverservice.exception.UserAlreadyExistException;
import com.accelaero.driverservice.repository.LocationRepository;
import com.accelaero.driverservice.repository.UserRepository;
import com.accelaero.driverservice.repository.VerficationTokenRegistry;
import com.accelaero.driverservice.requestdto.UserUpdateRequest;
import com.accelaero.driverservice.requestdto.UserRegisterRequest;
import com.accelaero.driverservice.responsedto.UserResponse;
import com.accelaero.driverservice.service.TripService;
import com.accelaero.driverservice.status.DriverStatus;
import com.accelaero.driverservice.util.CarType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
 public class UserServiceImpl implements com.accelaero.driverservice.service.UserService {

    private final UserRepository userRepository;
    private final VerficationTokenRegistry tokenRepository;

    private final LocationRepository locationRepository;


    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, VerficationTokenRegistry tokenRepository, LocationRepository locationRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.locationRepository = locationRepository;
        this.passwordEncoder = passwordEncoder;
    }


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
        user.setPhone(userDto.getPhone());
        Location currentLocation = this.locationRepository.findById(userDto.getCurrentLocationId()).orElseThrow(()->new LocationNotFound("Location Not Found"));

        user.setCurrentLocationName(currentLocation.getName());
        Car car = new Car();
        car.setCarType(CarType.fromCode(userDto.getCarType()));
        car.setDescription(userDto.getCarDescription());
        car.setLicPlateNo(userDto.getLicPlateNo());
        user.setCar(car);


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
    public User editUser(UserUpdateRequest updateRequest) {
        User user = getLoggedInDriver();
        user.setFirstName(updateRequest.getFirstName());
        user.setLastName(updateRequest.getLastName());
        user.setPhone(updateRequest.getPhone());
        user.setCurrentLocationName(updateRequest.getCurrentLocationName());

        user = userRepository.save(user);
        return user;
    }

    @Override
    public User getLoggedInDriver() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        return userRepository.findByEmailIgnoreCase(email);
    }





    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmailIgnoreCase(email);
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
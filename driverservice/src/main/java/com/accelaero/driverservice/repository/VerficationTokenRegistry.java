package com.accelaero.driverservice.repository;

import com.accelaero.driverservice.entity.User;
import com.accelaero.driverservice.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerficationTokenRegistry extends JpaRepository<VerificationToken,Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

}

package com.accelaero.driverservice.repository;

import com.accelaero.driverservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByCurrentLocationNameAndStatus(String currentLocationName, int status);
    User findByEmailIgnoreCase(String email);

}

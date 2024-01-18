package com.accelaero.driverservice.repository;

import com.accelaero.driverservice.entity.User;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.streams.internals.ApiUtils;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmailIgnoreCase(String email);

}

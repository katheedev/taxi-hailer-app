package com.accelaero.driverservice.repository;

import com.accelaero.driverservice.entity.Location;
import com.accelaero.driverservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {


}

package com.accelaero.driverservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.retry.annotation.CircuitBreaker;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name= "user_account")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "email", unique = true,nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "registration_date")
    @CreatedDate
    private Date registrationDate;

    @Column(name = "enabled",columnDefinition = "boolean default false",nullable = false)
    private boolean enabled;

    @Column(name = "current_location_name",nullable = false)
    private String currentLocationName;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "latitude")
    private double latitude;

    @Column(name= "status",nullable = false)
    private int status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", referencedColumnName = "id",nullable = false)
    private Car car;
}

package com.accelaero.driverservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name= "trip")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="passenger_name")
    private String passengerName;

    @Column(name="passenger_id")
    private Long passengerId;

    @Column(name="driver_id")
    private Long driverId;

    @Column(name = "trip_request_id")
    private Long tripRequestId;

    @Column(name="pickup_location_name")
    private String pickupLocationName;

    @Column(name="destination_name")
    private String destinationName;

    @Column(name = "accepted_time")
    @CreatedDate
    private Date acceptedTime;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "status")
    private int status;

    @Column(name = "payment_status")
    private int paymentStatus;

    @Column(name = "total_fare")
    private double totalFare;


}

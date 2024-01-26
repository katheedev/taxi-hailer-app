package com.accelaero.driverservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name= "temp_trip_request")
@NoArgsConstructor
public class TempTripRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="trip_request_id")
    private Long tripRequestId;

    @Column(name="passenger_id")
    private Long passengerId;

    @Column(name="driver_id")
    private Long driverId;

    @Column(name="pickup_location_name")
    private String pickupLocationName;

    @Column(name="destination_name")
    private String destinationName;

    @Column(name = "status")
    private int status;

}


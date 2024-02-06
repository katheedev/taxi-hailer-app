package com.example.passengerbackend.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name= "trip_response")
@NoArgsConstructor
public class TripResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "trip_id")
    private long tripId;

    @Column(name = "trip_request_id" ,unique = true)
    private long tripRequestId;

    @Column(name = "passenger_id")
    private long passengerId;

    @Column(name = "driver_id")
    private Long driverId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pick_up_location_name", referencedColumnName = "name")
    private Location pickUpLocationName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destination_name", referencedColumnName = "name")
    private Location destination;

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "total_fare")
    private double totalFare;

    @Column(name = "accepted_time")
    private Date acceptedTime;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "paid_time")
    private Date paidTime;

    @Column(name = "status")
    private int status;

    @Column(name = "status_message")
    private String statusMessage;

    @Column(name = "payment_status")
    private int paymentStatus;
}

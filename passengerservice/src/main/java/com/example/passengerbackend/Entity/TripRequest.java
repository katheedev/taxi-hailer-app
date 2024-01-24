package com.example.passengerbackend.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name= "trip_request")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class TripRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="passenger_id", referencedColumnName = "id", updatable = false)
    private Passenger passenger;

    @Column(name="pick_location_id")
    private Long pickUpLocation_id;

    @Column(name="dest_location_id")
    private Long destination_id;

    @Column(name = "status")
    private int status;
}

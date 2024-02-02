package com.example.passengerbackend.RequestDTO;


import com.example.passengerbackend.Entity.Location;
import com.example.passengerbackend.ResponseDTO.LocationResDTO;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TripResponseReqDto {

    private long tripId;
    private long tripRequestId;
    private long passengerId;
    private Long driverId;

    private Location pickUpLocation;
    private Location destination;
    private String passengerName;
    private double totalFare;

    private Date acceptedTime;
    private Date startTime;
    private Date endTime;
    private Date paidTime;

    private int status;
    private String statusMessage;
    private int paymentStatus;

}

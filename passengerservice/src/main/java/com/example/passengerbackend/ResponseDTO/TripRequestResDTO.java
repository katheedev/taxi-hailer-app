package com.example.passengerbackend.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class TripRequestResDTO {

    private LocationResDTO pickUpLocation;
    private LocationResDTO destination;
    private long tripRequestId;
    private long passengerId;
    private String passengerName;
    private int status;
}

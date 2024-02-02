package com.accelaero.driverservice.requestdto;


import com.accelaero.driverservice.responsedto.LocationResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripRequestResDTO {

    private LocationResDTO pickUpLocation;
    private LocationResDTO destination;
    private long tripRequestId;
    private long passengerId;
    private String passengerName;
    private int status;
    private double totalFare;
}

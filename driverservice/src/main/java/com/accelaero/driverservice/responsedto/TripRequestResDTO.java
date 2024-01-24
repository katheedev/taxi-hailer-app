package com.accelaero.driverservice.responsedto;


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

    private int status;
}

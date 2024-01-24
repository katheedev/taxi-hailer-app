package com.example.passengerbackend.RequestDTO;


import com.example.passengerbackend.Entity.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TripRequestReqDTO {

    @NotNull
    private long pickUpLocationId;
    @NotNull
    private long destinationId;
}

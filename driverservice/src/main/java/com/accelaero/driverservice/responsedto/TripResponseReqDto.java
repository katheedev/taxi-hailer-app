package com.accelaero.driverservice.responsedto;

import com.accelaero.driverservice.entity.Location;
import lombok.*;



import com.accelaero.driverservice.responsedto.LocationResDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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


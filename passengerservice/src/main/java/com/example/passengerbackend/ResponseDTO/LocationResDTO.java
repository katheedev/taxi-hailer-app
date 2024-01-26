package com.example.passengerbackend.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class LocationResDTO {

    private Long id;

    private double longitude;

    private double latitude;

    private String name;

    private String description;
}

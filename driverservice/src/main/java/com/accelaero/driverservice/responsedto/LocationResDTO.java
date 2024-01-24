package com.accelaero.driverservice.responsedto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationResDTO {

    private Long id;

    private double longitude;

    private double latitude;

    private String name;

    private String description;
}

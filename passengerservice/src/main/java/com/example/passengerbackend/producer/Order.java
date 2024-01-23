package com.example.passengerbackend.producer;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Order {

    @Getter
    @Setter
    private String orderID;

    @Getter
    @Setter
    private Date dateOfCreation;

    @Getter
    @Setter
    private String content;

}
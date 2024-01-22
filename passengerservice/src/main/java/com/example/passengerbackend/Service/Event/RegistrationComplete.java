package com.example.passengerbackend.Service.Event;

import com.example.passengerbackend.Entity.Passenger;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

//triggered during the registration process to notify the system that a user registration has been completed
@Getter
@Setter

public class RegistrationComplete extends ApplicationEvent{
    private String appUrl;
    private Locale locale;
    private Passenger passenger;

    public RegistrationComplete(
            Passenger passenger, Locale locale, String appUrl) {
        super(passenger);

        this.passenger = passenger;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}

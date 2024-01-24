package com.example.passengerbackend.Service.Listener;

import com.example.passengerbackend.Entity.Passenger;
import com.example.passengerbackend.Service.Event.RegistrationComplete;
import com.example.passengerbackend.Service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationComplete> {

    @Autowired
    private PassengerService passengerService;

//    @Autowired
//    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(RegistrationComplete event){
        this.confirmRegistration(event);
    }

    private void confirmRegistration(RegistrationComplete event) {
        Passenger passenger = event.getPassenger();
        String token = UUID.randomUUID().toString();
        passengerService.createVerificationToken(passenger, token);

        String recipientAddress = passenger.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/api/registrationConfirm?token=" + token;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        String link = "Verify your email address following the below link" + "\r\n" + "http://localhost:8080" + confirmationUrl;
        System.out.println("LINK: " + link);
        email.setText(link);
        //  mailSender.send(email);
    }
}

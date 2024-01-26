package com.accelaero.driverservice.entity;

import com.accelaero.driverservice.util.CarType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name= "payment")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "trip_id")
    private Long tripId;

    @Column(name = "paid_time")
    @CreatedDate
    private Date paidTime;

    @Column(name = "paid_total_amount")
    private double paidTotalAmount;

}

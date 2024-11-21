package com.project.capstone.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Entity
@Table(name="booking")
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int noOfPersons ;

    private Double totalCalculated ;

    private String busName;

    private String date;

    private String time;

    private String tripStatus;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // Many bookings can belong to one user


    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", busName='" + busName + '\'' +
                ", date=" + date +
                ", noOfPersons=" + noOfPersons +
                ", totalCalculated=" + totalCalculated +
                ", tripStatus='" + tripStatus + '\'' +
                '}';
    }

}
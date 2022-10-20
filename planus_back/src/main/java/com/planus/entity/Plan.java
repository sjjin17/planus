package com.planus.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="plan_id")
    private long planId;

    @Column(name="trip_date")
    private LocalDate tripDate;

    @Column(name="start_time")
    private int startTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trip_id")
    private Trip trip;

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY)
    List<Timetable> timetableList;
}

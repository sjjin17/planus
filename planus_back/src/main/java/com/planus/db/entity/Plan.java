package com.planus.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "plan", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Timetable> timetableList;

    @Builder
    public Plan(long planId, LocalDate tripDate, int startTime, Trip trip, List<Timetable> timetableList) {
        this.planId = planId;
        this.tripDate = tripDate;
        this.startTime = startTime;
        this.trip = trip;
        this.timetableList = timetableList;
    }
}

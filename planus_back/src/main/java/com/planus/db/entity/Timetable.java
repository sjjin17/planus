package com.planus.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="timetable_id")
    private long timetableId;

    @Column(name="orders")
    private int orders;

    @Column(name="place")
    private String place;

    @Column(name="lat")
    private double lat;

    @Column(name="lng")
    private double lng;

    @Column(name="cost_time")
    private int costTime;

    @Enumerated(EnumType.STRING)
    @Column(name="transit")
    private Transit transit;

    @Column(name="move_time")
    private int moveTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="plan_id")
    private Plan plan;

    @Builder
    public Timetable(long timetableId, int orders, String place, double lat, double lng, int costTime, Transit transit, int moveTime, Plan plan) {
        this.timetableId = timetableId;
        this.orders = orders;
        this.place = place;
        this.lat = lat;
        this.lng = lng;
        this.costTime = costTime;
        this.transit = transit;
        this.moveTime = moveTime;
        this.plan = plan;
    }

}

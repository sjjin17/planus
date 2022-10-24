package com.planus.db.entity;

import javax.persistence.*;

@Entity
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
}

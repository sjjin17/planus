package com.planus.entity;


import javax.persistence.*;

@Entity
public class TripArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="triparea_id")
    private long tripAreaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trip_id")
    private Trip trip;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="area_id")
    private Area area;

}

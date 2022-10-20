package com.planus.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="trip_id")
    private long tripId;

    @Column(name="trip_url")
    private String tripUrl;

    @Column(name="admin")
    private long admin;

    @Column(name="create_time")
    private LocalDateTime createTime;

    @Column(name="start_date")
    private LocalDate startDate;

    @Column(name="period")
    private int period;

    @Column(name="complete")
    private boolean complete;

    @Column(name="image_url")
    private String imageUrl;

    @OneToMany(mappedBy="trip", fetch = FetchType.LAZY)
    List<Member> memberList;

    @OneToMany(mappedBy="trip", fetch = FetchType.LAZY)
    List<Bucket> bucketList;

    @OneToMany(mappedBy="trip", fetch = FetchType.LAZY)
    List<TripArea> tripAreaList;

    @OneToMany(mappedBy = "trip", fetch = FetchType.LAZY)
    List<Plan> planList;

}

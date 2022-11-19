package com.planus.db.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TripArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="triparea_id")
    private long tripAreaId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="trip_id")
    private Trip trip;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="area_id")
    private Area area;

    @Builder
    public TripArea(long tripAreaId, Trip trip, Area area) {
        this.tripAreaId = tripAreaId;
        this.trip = trip;
        this.area = area;
    }
}

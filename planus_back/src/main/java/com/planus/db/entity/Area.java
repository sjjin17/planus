package com.planus.db.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="area_id")
    private long areaId;

    @Column(name="do_name")
    private String doName;

    @Column(name="si_name")
    private String siName;

    @Column(name="image_url")
    private String imageUrl;

    @Column(name="lat")
    private double lat;

    @Column(name="lng")
    private double lng;

    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private List<TripArea> tripAreaList;

    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private List<Recommend> recommendList;
}

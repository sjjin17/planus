package com.planus.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @Builder
    public Area(long areaId, String doName, String siName, String imageUrl, double lat, double lng, List<TripArea> tripAreaList, List<Recommend> recommendList){
        this.areaId = areaId;
        this.doName = doName;
        this.siName = siName;
        this.imageUrl = imageUrl;
        this. lat = lat;
        this.lng = lng;
        this.tripAreaList = tripAreaList;
        this.recommendList = recommendList;
    }
}

package com.planus.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private long userId;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="kakao_id")
    private long kakaoId;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Member> memberList;

}

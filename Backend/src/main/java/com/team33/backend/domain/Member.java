package com.team33.backend.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 39)
    @NotBlank
    private String name;

    @Column(length = 2048)
    private String profileImageUrl;

    @OneToMany(mappedBy = "member")
    private List<Issue> issues = new ArrayList<>();
}

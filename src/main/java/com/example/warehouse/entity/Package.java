package com.example.warehouse.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "package")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private Long trackingNo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PkgImages> Images;

    public void setTrackingNo(Long trackingNo) {
        this.trackingNo = trackingNo;
    }

    public void setImages(List<PkgImages> images) {
        Images = images;
    }
}

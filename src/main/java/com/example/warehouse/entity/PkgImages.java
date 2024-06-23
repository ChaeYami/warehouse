package com.example.warehouse.entity;

import com.example.warehouse.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PkgImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    private String filename;

    @Enumerated(EnumType.STRING)
    private Type type;

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setType(Type type) {
        this.type = type;
    }

}

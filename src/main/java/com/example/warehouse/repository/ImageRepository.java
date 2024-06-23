package com.example.warehouse.repository;

import com.example.warehouse.entity.PkgImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<PkgImages, Long> {
}

package com.example.warehouse.controller;

import com.example.warehouse.Type;
import com.example.warehouse.entity.Package;
import com.example.warehouse.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/packages")
public class PackageController {

    private final PackageService packageService;

    @GetMapping
    public ResponseEntity<List<Package>> getAllPackages() {
        List<Package> packages = packageService.getAllPackages();
        return ResponseEntity.ok(packages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable Long id) {
        Package pkg = packageService.getPackageById(id);
        if (pkg != null) {
            return ResponseEntity.ok(pkg);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createPackage(
            @RequestParam("trackingNo") Long trackingNo,
            @RequestParam("type") Type type,
            @RequestParam("images") MultipartFile[] images) throws IOException {

        packageService.savePackage(trackingNo, images,type);

        return ResponseEntity.ok("저장되었습니다.");
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updatePackage(
            @PathVariable Long id,
            @RequestParam("trackingNo") Long trackingNo,
            @RequestParam("type") Type type,
            @RequestParam("images") MultipartFile[] images) throws IOException {

        Package updatedPackage = packageService.updatePackage(id, trackingNo, images, type);

        if (updatedPackage != null) {
            return ResponseEntity.ok("수정되었습니다.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }

}
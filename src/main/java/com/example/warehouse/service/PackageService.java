package com.example.warehouse.service;

import com.example.warehouse.Type;
import com.example.warehouse.entity.PkgImages;
import com.example.warehouse.entity.Package;
import com.example.warehouse.repository.ImageRepository;
import com.example.warehouse.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PackageService {

    private final PackageRepository packageRepository;
    private final ImageRepository imageRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public Package savePackage(Long trackingNo, MultipartFile[] images, Type type) throws IOException {

        Package pkg = new Package();
        pkg.setTrackingNo(trackingNo);
        Package savedPackage = packageRepository.save(pkg);

        List<PkgImages> imageEntities = new ArrayList<>();

        saveImages(images, type, savedPackage, imageEntities);


        return packageRepository.save(savedPackage);
    }

    public Package updatePackage(Long id, Long trackingNo, MultipartFile[] images, Type type) throws IOException {
        Package existingPackage = packageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 패키지를 찾을 수 없습니다. id : " + id));

        existingPackage.setTrackingNo(trackingNo);

        existingPackage.getImages().clear();
        packageRepository.save(existingPackage);

        List<PkgImages> existingImages = existingPackage.getImages();

        saveImages(images, type, existingPackage, existingImages);

        return packageRepository.save(existingPackage);
    }

    public Package getPackageById(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 패키지를 찾을 수 없습니다. id : " + id));
    }

    public List<Package> getAllPackages() {
        return packageRepository.findAll();
    }

    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }

    private void saveImages(MultipartFile[] images, Type type, Package savedPackage, List<PkgImages> imageEntities) throws IOException {
        for (MultipartFile imageFile : images) {
            String filename = UUID.randomUUID().toString() + "-" + imageFile.getOriginalFilename();
            String filePath = uploadDir + filename;

            Path path = Paths.get(filePath);
            Files.write(path, imageFile.getBytes());

            PkgImages image = new PkgImages();
            image.setFilename(filename);
            image.setType(type);
            imageEntities.add(image);

            imageRepository.save(image);
        }
        savedPackage.setImages(imageEntities);
    }
}
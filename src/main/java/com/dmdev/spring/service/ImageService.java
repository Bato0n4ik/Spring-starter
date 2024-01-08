package com.dmdev.spring.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${app.image.bucket:E:\\IdeaProjects\\spring-starter2\\images}")
    private final String bucket;

    @SneakyThrows
    public void upload(String imagePath, InputStream inputStream){
        var fullPath = Path.of(bucket, imagePath);
        try(inputStream){
            Files.createDirectories(fullPath.getParent());
            Files.write(fullPath, inputStream.readAllBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }

    }

    @SneakyThrows
    public Optional<byte[]> get(String imageName){
        var fullPath = Path.of(bucket, imageName);
        return Files.exists(fullPath)
                ? Optional.of(Files.readAllBytes(fullPath)) : Optional.empty();
    }
}

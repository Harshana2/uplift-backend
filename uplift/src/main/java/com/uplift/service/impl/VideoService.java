package com.uplift.service.impl;

import com.uplift.model.Video;
import com.uplift.repo.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoService {

    private final VideoRepo videoRepository;
    private final Path rootLocation = Paths.get("uploads/videos");

    @Autowired
    public VideoService(VideoRepo videoRepository) {
        this.videoRepository = videoRepository;
    }

    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!", e);
        }
    }

    public Video storeVideo(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        Path filePath = this.rootLocation.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        Video video = new Video();
        video.setTitle(fileName.replace(".mp4", ""));
        video.setFileName(fileName);
        video.setFilePath(filePath.toString());
        video.setFileSize(file.getSize());
        video.setFileType(file.getContentType());

        return videoRepository.save(video);
    }

    public Video getVideo(String id) {
        return videoRepository.findById(id).orElseThrow(() -> new RuntimeException("Video not found!"));
    }
}

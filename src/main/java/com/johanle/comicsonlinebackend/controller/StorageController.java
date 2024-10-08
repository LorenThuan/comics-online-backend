package com.johanle.comicsonlinebackend.controller;

import com.johanle.comicsonlinebackend.model.FileData;
import com.johanle.comicsonlinebackend.model.ImageResponse;
import com.johanle.comicsonlinebackend.repository.ChapterRepository;
import com.johanle.comicsonlinebackend.repository.FileDataRepository;
import com.johanle.comicsonlinebackend.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:5173/")
public class StorageController{

    @Autowired
    private StorageService storageService;
    @Autowired
    private FileDataRepository fileDataRepository;
    @Autowired
    private ChapterRepository chapterRepository;

    @PostMapping("/image/fileSystem/{chapterId}")
    public ResponseEntity<?> uploadImagesToFileSystem
            (@RequestParam(value = "folderPath") String folderPath, @RequestParam("images")List<MultipartFile> files,
             @PathVariable int chapterId) throws Exception{
        List<String> uploadResults = storageService.uploadImagesToFileSystem(files, chapterId, folderPath);
        return ResponseEntity.status(HttpStatus.OK).body(uploadResults);
    }

    @GetMapping("/api/images/{chapterId}")
    public ResponseEntity<List<ImageResponse>> findImagesByChapterId(@PathVariable int chapterId) throws Exception{
        List<ImageResponse> imageData = storageService.findImagesByChapterId(chapterId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(imageData);
    }
}

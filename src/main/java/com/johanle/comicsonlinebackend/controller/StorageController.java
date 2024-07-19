package com.johanle.comicsonlinebackend.controller;

import com.johanle.comicsonlinebackend.model.FileData;
import com.johanle.comicsonlinebackend.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173/")
public class StorageController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/image/fileSystem/{chapterId}")
    public ResponseEntity<?> uploadImagesToFileSystem
            (@RequestParam("images")List<MultipartFile> files, @PathVariable int chapterId) throws Exception{
        List<String> uploadResults = storageService.uploadImagesToFileSystem(files, chapterId);
        return ResponseEntity.status(HttpStatus.OK).body(uploadResults);
    }

    @GetMapping("/fileSystem/{chapterId}")
    public ResponseEntity<?> findImagesByChapterId(@PathVariable int chapterId) {
        List<FileData> fileDataList = storageService.findImagesByChapterId(chapterId);
        return ResponseEntity.ok(fileDataList);
    }
}

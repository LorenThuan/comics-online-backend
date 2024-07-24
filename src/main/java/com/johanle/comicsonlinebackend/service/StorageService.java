package com.johanle.comicsonlinebackend.service;


import com.johanle.comicsonlinebackend.model.Chapter;
import com.johanle.comicsonlinebackend.model.FileData;
import com.johanle.comicsonlinebackend.model.ImageResponse;
import com.johanle.comicsonlinebackend.repository.ChapterRepository;
import com.johanle.comicsonlinebackend.repository.FileDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StorageService {

    @Autowired
    private FileDataRepository fileDataRepository;

    @Autowired
    private ChapterRepository chapterRepository;

//    private static final String FOLDER_PATH = "C:/Users/thuan/Desktop/MyFiles/Vol. 1 Ch. 1 - Different Dimension Freshmen/";

//    public String uploadImage(MultipartFile file) throws IOException {
//        ImageData imageData = repository.save(ImageData.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .imageData(ImageUtils.compressImage(file.getBytes())).build());
//        if (imageData != null) {
//            return "file uploaded successfully : " + file.getOriginalFilename();
//        }
//        return null;
//    }

    public List<String> uploadImagesToFileSystem(List<MultipartFile> files, int chapterId, String folderPath) {
        return files.stream().map(file -> {
            try {
                Optional<Chapter> chapterOpt = chapterRepository.findById(chapterId);
                if (!chapterOpt.isPresent()) {
                    return "Chapter not found: " + chapterId;
                }

                Chapter chapter = chapterOpt.get();
                if (chapter.getFileDataList() == null) {
                    chapter.setFileDataList(new ArrayList<>());
                }

                String filePath = folderPath + file.getOriginalFilename();
                List<FileData> existingFiles = fileDataRepository.findByChapter(chapter);

                boolean fileExists = existingFiles.stream()
                        .anyMatch(f -> f.getName().equals(file.getOriginalFilename()));

                if (fileExists) {
                    return "File data already in file: " + filePath;
                }

                FileData fileData = new FileData();
                fileData.setName(file.getOriginalFilename());
                fileData.setType(file.getContentType());
                fileData.setFilePath(filePath);
                fileData.setChapter(chapter);
                chapter.getFileDataList().add(fileData);

                fileDataRepository.save(fileData);
                chapterRepository.save(chapter);

                file.transferTo(new File(filePath));

                return "File uploaded successfully: " + filePath;
            } catch (Exception e) {
                return "Failed to upload file: " + e.getMessage();
            }
        }).collect(Collectors.toList());
    }

    public List<ImageResponse> findImagesByChapterId(int chapterId) {
        List<ImageResponse> imageResponses = new ArrayList<>();

        try {
            Optional<Chapter> chapterOptional = chapterRepository.findById(chapterId);
            if (chapterOptional.isPresent()) {
                Chapter chapter = chapterOptional.get();
                List<FileData> fileDataList = fileDataRepository.findByChapter(chapter);
                if (fileDataList != null) {
                    for (FileData fileData : fileDataList) {
                        String filePath = fileData.getFilePath();
                        byte[] images = Files.readAllBytes(new File(filePath).toPath());
                        String base64EncodedImage = Base64.getEncoder().encodeToString(images);
                        ImageResponse imageResponse = new ImageResponse();
                        imageResponse.setData(base64EncodedImage);
                        imageResponse.setContentType(fileData.getType());
                        imageResponse.setFilename(fileData.getName());
                        imageResponses.add(imageResponse);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Can't not find chapter by id: " + e.getMessage());
        }
        return imageResponses;
    }

    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {
        Optional<FileData> fileData = fileDataRepository.findByName(fileName);
        String filePath = fileData.get().getFilePath();
        byte[] images = Files.readAllBytes(new File(filePath).toPath());
        return images;
    }
}

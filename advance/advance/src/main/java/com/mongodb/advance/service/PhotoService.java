package com.mongodb.advance.service;

import com.mongodb.advance.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    String addPhoto(String originalFilename, MultipartFile image) throws IOException;

    Photo getPhoto(String id);

    String deletePhoto(String id);
}

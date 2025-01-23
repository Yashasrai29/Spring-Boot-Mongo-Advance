package com.mongodb.advance.controller;

import com.mongodb.advance.model.Photo;
import com.mongodb.advance.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping
    public String addPhoto(@RequestParam("image")MultipartFile image) throws IOException {
       String id=photoService.addPhoto(image.getOriginalFilename(),image);
       return "Added Photo successfully with id: "+id;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> download(@PathVariable String id){
        Photo photo=photoService.getPhoto(id);
        Resource resource=new ByteArrayResource(photo.getImage().getData());

        return  ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + photo.getTitle() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable String id){
         photoService.deletePhoto(id);
        return "Deleted successfully";
    }

}

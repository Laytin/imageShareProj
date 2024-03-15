package com.laytin.kafkaProjectMain.controller;


import com.laytin.kafkaProjectMain.dto.UploadResponce;
import com.laytin.kafkaProjectMain.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class ApiController {
    private final ImageService imageService;
    @Autowired

    public ApiController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping()
    public UploadResponce uploadFile(@RequestParam("attachment") MultipartFile mpfile,@RequestParam("count") int count){
        return imageService.uploadr2(mpfile,count);
    }
    @DeleteMapping("/{url}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable("url") String url){
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

package com.laytin.kafkaProjectMain.controller;


import com.laytin.kafkaProjectMain.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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
    public String uploadFile(@RequestParam("attachment") MultipartFile mpfile){
        String fileName = StringUtils.cleanPath(mpfile.getOriginalFilename());
        Object o = imageService.uploadAndGetUri(mpfile);
        return String.valueOf(o);
    }
    @DeleteMapping("/{url}")
    public ResponseEntity<HttpStatus> deleteFile(@PathVariable("url") String url){
        return ResponseEntity.ok(HttpStatus.OK);
    }
}

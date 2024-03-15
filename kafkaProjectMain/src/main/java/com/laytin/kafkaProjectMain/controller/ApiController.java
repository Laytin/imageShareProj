package com.laytin.kafkaProjectMain.controller;

import com.laytin.kafkaProjectMain.dto.UploadResponce;
import com.laytin.kafkaProjectMain.service.FileService;
import com.laytin.kafkaProjectMain.utils.errors.ErrorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class ApiController {
    private final FileService fileService;
    @Autowired
    public ApiController(FileService fileService) {
        this.fileService = fileService;
    }
    @PostMapping()
    public UploadResponce uploadFile(@RequestParam("attachment") MultipartFile mpfile,@RequestParam("count") int count){
        return fileService.uploadr2(mpfile,count);
    }
    @GetMapping("/{url}")
    public RedirectView getFileUri(@PathVariable("url") String url){
        String uri = fileService.generatePreSignedUrl(url);
        if(uri.equals("error"))
            throw new RuntimeException("File not found in storage. Expired?");
        return new RedirectView();
    }
    @ExceptionHandler
    private ResponseEntity<ErrorRequest> handleException(RuntimeException e) {
        ErrorRequest response = new ErrorRequest(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}

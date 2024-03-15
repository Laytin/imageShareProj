package com.laytin.kafkaProjectMain.dto;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UploadResponce {
    private ResponseEntity<HttpStatus> responceType = ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
    private String message = "Some error during uploading file.";

    public UploadResponce() {
    }

    public UploadResponce(ResponseEntity<HttpStatus> responceType, String message) {
        this.responceType = responceType;
        this.message = message;
    }

    public ResponseEntity<HttpStatus> getResponceType() {
        return responceType;
    }

    public void setResponceType(ResponseEntity<HttpStatus> responceType) {
        this.responceType = responceType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

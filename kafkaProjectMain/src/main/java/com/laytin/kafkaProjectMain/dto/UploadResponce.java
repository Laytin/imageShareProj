package com.laytin.kafkaProjectMain.dto;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

public class UploadResponce {
    public ResponseEntity<HttpEntity> responceType;
    public String message;

    public UploadResponce() {
    }

    public UploadResponce(ResponseEntity<HttpEntity> responceType, String message) {
        this.responceType = responceType;
        this.message = message;
    }

    public ResponseEntity<HttpEntity> getResponceType() {
        return responceType;
    }

    public void setResponceType(ResponseEntity<HttpEntity> responceType) {
        this.responceType = responceType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

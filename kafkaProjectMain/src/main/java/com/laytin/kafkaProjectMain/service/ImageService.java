package com.laytin.kafkaProjectMain.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.laytin.kafkaProjectMain.dto.UploadResponce;
import com.laytin.kafkaProjectMain.model.ImageCounter;
import com.laytin.kafkaProjectMain.utils.Utils;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class ImageService {
    @Value("${cloud.aws.bucket.name}")
    private String bukkitName;
    @Value("${app.domain}")
    private String domain;
    private final KafkaService kafkaService;
    private final AmazonS3 s3;
    @Autowired
    public ImageService(KafkaService kafkaService, AmazonS3 s3) {
        this.kafkaService = kafkaService;
        this.s3 = s3;
    }
    public UploadResponce uploadr2(MultipartFile mpfile, int count) {
        UploadResponce responce = new UploadResponce();
        File fileObj = Utils.convertMultiPartFileToFile(mpfile);
        String fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(mpfile.getOriginalFilename());
        ImageCounter req = new ImageCounter(fileName, count);
        try {
            String s = (String)kafkaService.kafkaRegisterReply(req);
            if(s.equals("false"))
                return responce;
            s3.putObject(new PutObjectRequest(bukkitName, fileName, fileObj));
            fileObj.delete();
            responce.setResponceType(ResponseEntity.ok(HttpStatus.OK));
            responce.setMessage("File url: " + domain + "/" + fileName +". Times remaining: " + req.getCount());
        } catch (Exception e) {
            System.out.println(e);
        }
        return responce;
    }
}

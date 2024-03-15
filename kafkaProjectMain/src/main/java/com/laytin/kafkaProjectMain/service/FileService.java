package com.laytin.kafkaProjectMain.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laytin.kafkaProjectMain.dto.UploadResponce;
import com.laytin.kafkaProjectMain.model.FileCounter;
import com.laytin.kafkaProjectMain.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

@Service
public class FileService {
    private ObjectMapper objectMapper;
    @Value("${cloud.aws.bucket.name}")
    private String bukkitName;
    @Value("${app.domain}")
    private String domain;
    private final KafkaService kafkaService;
    private final AmazonS3 s3;
    @Autowired
    public FileService(ObjectMapper objectMapper, KafkaService kafkaService, AmazonS3 s3) {
        this.objectMapper = objectMapper;
        this.kafkaService = kafkaService;
        this.s3 = s3;
    }
    public UploadResponce uploadr2(MultipartFile mpfile, int count) {
        UploadResponce responce = new UploadResponce();
        File fileObj = Utils.convertMultiPartFileToFile(mpfile);
        String fileName = System.currentTimeMillis() + "_" + StringUtils.cleanPath(mpfile.getOriginalFilename());
        FileCounter req = new FileCounter(fileName, count);
        try {
            String s = (String)kafkaService.kafkaRequestReply(req,"register-topic");
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
    public String generatePreSignedUrl(String filename) {
        //TODO: check if file counts is down
        try {
            FileCounter fc = objectMapper.readValue((String)kafkaService.kafkaRequestReply(filename,"get-specific-topic"), FileCounter.class);
            if(fc==null || fc.getName()==null || fc.getCount()==0)
                throw new RuntimeException("File unavaible for downloading");
            if(fc.getCount()!=-1){
                fc.setCount(fc.getCount()-1);
                kafkaService.kafkaRequestReply(objectMapper.writeValueAsString(fc),"set-topic");
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE,5);
            String s = s3.generatePresignedUrl("neoksomeshit",filename,cal.getTime(), HttpMethod.GET).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "error";
    }
    @Scheduled(fixedDelay = 50000)
    public void deleteExpired() {
        System.out.println("shuduled");
    }
}

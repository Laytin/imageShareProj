package com.laytin.kafkaProjectMain.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.core.type.TypeReference;
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
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
                throw new RuntimeException("DB side: no answer");
            s3.putObject(new PutObjectRequest(bukkitName, fileName, fileObj));
            fileObj.delete();
            responce.setResponceType(ResponseEntity.ok(HttpStatus.OK));
            responce.setMessage("File url: " + domain + "/" + fileName +". Times remaining: " + req.getCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responce;
    }
    public String generatePreSignedUrl(String filename) {
        try {
            FileCounter fc = objectMapper.readValue((String)kafkaService.kafkaRequestReply(filename,"get-specific-topic"), FileCounter.class);
            if(fc.getName()==null || fc.getName().equals(""))
                throw new RuntimeException("File unavaible for downloading");
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.MINUTE,1);
            String s = s3.generatePresignedUrl(bukkitName,filename,cal.getTime(), HttpMethod.GET).toString();
            return s;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Scheduled(fixedDelay = 50000)
    public void deleteExpired() {
        try {
            List<FileCounter> asList = objectMapper.readValue((String)kafkaService.kafkaRequestReply("","get-expired-list-topic"),
                    new TypeReference<List<FileCounter>>() { });
            if(asList.isEmpty())
                return;
            ArrayList<KeyVersion> keys = new ArrayList<KeyVersion>();
            asList.forEach(f->{
                s3.putObject(bukkitName, f.getName(), "Object to delete:" + f.getName());
                keys.add(new KeyVersion(f.getName()));
            });
            DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bukkitName)
                    .withKeys(keys)
                    .withQuiet(false);
            DeleteObjectsResult delObjRes = s3.deleteObjects(multiObjectDeleteRequest);
            delObjRes.getDeletedObjects().forEach(f-> System.out.println("Object deleted: " + f.getKey()));
            } catch (AmazonServiceException e) {
                e.printStackTrace();
            } catch (SdkClientException e) {
                e.printStackTrace();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
}

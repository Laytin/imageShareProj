package com.laytin.kafkaProjectMain;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.laytin.kafkaProjectMain.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class KafkaProjectApplicationTests {
	@Autowired
	private FileService fileService;
	@Test
	void contextLoads() {
		fileService.deleteExpired();
	}

}

package com.laytin.kafkaProjectMain;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class KafkaProjectApplicationTests {
	@Value("${cloud.aws.credentials.access-key}")
	private String accessKey;

	@Value("${cloud.aws.credentials.secret-key}")
	private String accessSecret;
	@Value("${cloud.aws.region.static}")
	private String region;
	@Value("${cloud.accountid}")
	private String accountid;

	public AmazonS3 s3Client() {
		AWSCredentials credentials = new BasicAWSCredentials(
				accessKey,
				accessSecret
		);
		AmazonS3 s3client = AmazonS3ClientBuilder
				.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("https://"+accountid+".r2.cloudflarestorage.com",region))
				.withCredentials(new AWSStaticCredentialsProvider(credentials))
				.build();
		return s3client;
	}
	@Test
	void contextLoads() {
	}

}

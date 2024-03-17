package com.laytin.kafkaProjectSelectel;

import com.laytin.kafkaProjectSelectel.model.FileCounter;
import com.laytin.kafkaProjectSelectel.service.FileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class KafkaProjectApplicationTests {
	@Autowired
	private FileService cs;
	@Test
	void contextLoads() {
		List<FileCounter> fc =  cs.getExpiredList();
		System.out.println("Executed");
		Assert.notEmpty(fc, "asdsadadadada");
	}

}

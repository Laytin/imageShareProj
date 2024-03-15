package com.laytin.kafkaProjectMain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.concurrent.TimeUnit;

@Service
public class KafkaService {
    private ReplyingKafkaTemplate<String, Object, Object> template;
    private ObjectMapper mapper;
    @Autowired
    public KafkaService(ReplyingKafkaTemplate<String, Object, Object> template, ObjectMapper mapper) {
        this.template = template;
        this.mapper = mapper;
    }
    public Object kafkaRequestReply(Object request, String channel) throws Exception {
        ProducerRecord<String, Object> record = new ProducerRecord<>(channel, mapper.writeValueAsString(request));
        RequestReplyFuture<String, Object, Object> replyFuture = template.sendAndReceive(record);
        SendResult<String, Object> sendResult = replyFuture.getSendFuture().get(10, TimeUnit.SECONDS);
        ConsumerRecord<String, Object> consumerRecord = replyFuture.get(10, TimeUnit.SECONDS);
        return consumerRecord.value();
    }
}

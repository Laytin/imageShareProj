package com.laytin.kafkaProjectSelectel.listener;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laytin.kafkaProjectSelectel.model.FileCounter;
import com.laytin.kafkaProjectSelectel.service.FileService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerClass {
    private final FileService fileService;
    private final ObjectMapper mapper;
    @Autowired
    public KafkaListenerClass(FileService fileService, ObjectMapper mapper) {
        this.fileService = fileService;
        this.mapper = mapper;
    }
    @KafkaListener(groupId="${myproject.consumer-group}", topics = "${myproject.send-topics}")
    @SendTo
    public Message<?> listen1(ConsumerRecord<String, Object> consumerRecord) throws JsonProcessingException {
        FileCounter img = mapper.readValue(consumerRecord.value().toString(), FileCounter.class);
        return MessageBuilder.withPayload(String.valueOf(fileService.registerImageCounter(img)))
                .build();
    }

    @KafkaListener(groupId="${myproject.consumer-group}", topics = "get-specific-topic")
    @SendTo
    public Message<?> listen2(ConsumerRecord<String, Object> consumerRecord) throws JsonProcessingException {
        return MessageBuilder.withPayload(mapper.writeValueAsString(fileService.getImageCounterByName(consumerRecord.value().toString())))
                .build();
    }
    @KafkaListener(groupId="${myproject.consumer-group}", topics = "get-expired-list-topic")
    @SendTo
    public Message<?> listen3(ConsumerRecord<String, Object> consumerRecord) throws JsonProcessingException {
        return MessageBuilder.withPayload(mapper.writeValueAsString(fileService.getExpiredList()))
                .build();
    }
}

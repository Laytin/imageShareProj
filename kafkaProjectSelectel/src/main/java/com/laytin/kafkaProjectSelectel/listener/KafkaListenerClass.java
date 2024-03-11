package com.laytin.kafkaProjectSelectel.listener;


import com.laytin.kafkaProjectSelectel.service.ImageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaListenerClass {
    private final ImageService imageService;
    @Autowired
    public KafkaListenerClass(ImageService imageService) {
        this.imageService = imageService;
    }

    @KafkaListener(groupId="${myproject.consumer-group}", topics = "${myproject.send-topics}")
    @SendTo
    public Message<?> listen(ConsumerRecord<String, Object> consumerRecord) {
        imageService.saveImageLocaly((byte[]) consumerRecord.value());
        return MessageBuilder.withPayload("my answer" )
                .build();
    }
}

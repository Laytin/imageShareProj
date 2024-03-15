package com.laytin.kafkaProjectSelectel.service;

import com.laytin.kafkaProjectSelectel.model.ImageCounter;
import com.laytin.kafkaProjectSelectel.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CounterService {
    private final ImageRepository imageRepository;
    @Autowired
    public CounterService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }
    public ImageCounter getImageCounterByName(){
        return null;

    }
    @Transactional
    public boolean registerImageCounter(ImageCounter img){
        ImageCounter newimg = imageRepository.save(img);
        return true;
    }
}

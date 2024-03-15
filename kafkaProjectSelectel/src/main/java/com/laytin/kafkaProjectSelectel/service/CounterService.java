package com.laytin.kafkaProjectSelectel.service;

import com.laytin.kafkaProjectSelectel.model.FileCounter;
import com.laytin.kafkaProjectSelectel.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CounterService {
    private final FileRepository fileRepository;
    @Autowired
    public CounterService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
    public FileCounter getImageCounterByName(String filename){
        Optional<FileCounter> fc = fileRepository.findByName(filename);
        if(!fc.isPresent())
            return new FileCounter();
        FileCounter fc1 = fc.get();
        if(fc1.getCount()==-1)
            return fc.get();
        else if(fc1.getCount()==1)
            fileRepository.delete(fc1);
        else{
            fc.get().setCount(fc.get().getCount()-1);
            fileRepository.save(fc.get());
        }
        return fc.get();
    }
    public boolean registerImageCounter(FileCounter img){
        img.setTm(Timestamp.valueOf(LocalDateTime.now()));
        FileCounter newimg = fileRepository.save(img);
        return true;
    }
    public List<FileCounter> getExpiredList(){
        Timestamp tm = Timestamp.valueOf(LocalDateTime.now().minusWeeks(1));
        List<FileCounter> fc = fileRepository.findByTmBefore(tm);
        if(fc.isEmpty())
            return new ArrayList<>();
        fileRepository.deleteByTmBefore(tm);
        return fc;
    }
}

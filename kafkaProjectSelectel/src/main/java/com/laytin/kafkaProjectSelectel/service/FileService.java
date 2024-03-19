package com.laytin.kafkaProjectSelectel.service;

import com.laytin.kafkaProjectSelectel.model.FileCounter;
import com.laytin.kafkaProjectSelectel.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    @Value("${app.expiredays}")
    private int expireDays;
    private final FileRepository fileRepository;
    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
    public FileCounter getImageCounterByName(String filename){
        Optional<FileCounter> fc = fileRepository.findByName(filename);
        if(!fc.isPresent() || fc.get().getCount()==0)
            return new FileCounter();
        FileCounter fc1 = fc.get();
        if(fc1.getCount()==-1)
            return fc1;
        else{
            fc1.setCount(fc1.getCount()-1);
            fileRepository.save(fc1);
        }
        return fc1;
    }
    public boolean registerImageCounter(FileCounter img){
        img.setTm(Timestamp.valueOf(LocalDateTime.now()));
        FileCounter newimg = fileRepository.save(img);
        return true;
    }
    public List<FileCounter> getExpiredList(){
        Timestamp tm = Timestamp.valueOf(LocalDateTime.now().minusDays(expireDays));
        List<FileCounter> fc = fileRepository.findByTmBeforeOrCountIsLessThan(tm,0);
        if(fc.isEmpty())
            return new ArrayList<>();
        fileRepository.deleteByTmBeforeOrCountIsLessThan(tm,0);
        return fc;
    }
}

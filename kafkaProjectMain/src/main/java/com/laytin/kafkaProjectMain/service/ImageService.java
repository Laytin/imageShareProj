package com.laytin.kafkaProjectMain.service;

import com.laytin.kafkaProjectMain.listener.KafkaListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {
    private final KafkaListener kafkaListener;
    @Autowired
    public ImageService(KafkaListener kafkaListener) {
        this.kafkaListener = kafkaListener;
    }
    public Object uploadAndGetUri(MultipartFile f){
        BufferedImage image = null;
        Object o=null;
        try {
            image = ImageIO.read(f.getInputStream());
            if(image!=null)
                o= kafkaListener.kafkaRequestReply(serializeImage(image));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return o;
    }
    private static byte[] serializeImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}

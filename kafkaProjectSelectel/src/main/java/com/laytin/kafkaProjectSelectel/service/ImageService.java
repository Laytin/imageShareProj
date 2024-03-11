package com.laytin.kafkaProjectSelectel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
@Service
public class ImageService {
    @Value("${myproject.save_type}")
    private String save_type;
    private final Path uploadPath = Paths.get("Files-Upload");
    public ImageService() {
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public String saveImage(byte[] bytes){
        BufferedImage image = deserializeImage(bytes);

        if(save_type.equals("local"))
            saveImageLocaly(image);
        else
            saveImageFtp(image);
        return url;
    }
    private void saveImageFtp(BufferedImage bf){

    }
    private void saveImageLocaly(BufferedImage bf){
        try {
            File myfile  =new File("image.png");
            ImageIO.write(bf, "png", myfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static BufferedImage deserializeImage(byte[] imageBytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        try {
            return ImageIO.read(byteArrayInputStream);
        } catch (IOException e) {
            return null;
        }
    }
}

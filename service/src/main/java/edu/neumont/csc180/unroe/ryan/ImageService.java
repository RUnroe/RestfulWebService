package edu.neumont.csc180.unroe.ryan;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageService {
    String directory = "image/";

    public BufferedImage getImage(String name) {
//        BufferedImage selectedImage = ImageIO.read(directory);
        BufferedImage selectedImage = null;

        return selectedImage;
    }

    public BufferedImage getGrayscaleImage(String name) {
        BufferedImage image = getImage(name);
        //make grayscale

        return image;
    }

    public BufferedImage getRotatedImage(String name) {
        BufferedImage image = getImage(name);
        //rotate image

        return image;
    }

    public boolean createImage(MultipartFile image) {
       String imageName = image.getOriginalFilename();

       Path path = Paths.get(directory);
       //Create directory if not there
       if(!Files.exists(path)) {
           try {
               Files.createDirectory(path);
           } catch (IOException e) {
               e.printStackTrace();

           }
       }
       //Create the image
        try(InputStream input = image.getInputStream()){
            Path filePath = path.resolve(imageName);
            Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            //Found image with same name already
            return false;
        }
        return true;
    }

    public boolean deleteImage(String name) {
        boolean removed = false;
        Path path = Paths.get(directory + name);

        try {
            Files.delete(path);
            removed = true;
        } catch (IOException e) {
            e.printStackTrace();
            removed = false;
        }

        return removed;
    }

}

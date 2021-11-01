package edu.neumont.csc180.unroe.ryan;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
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

    public BufferedImage getBufferedImage(String name) {
        File image = new File(directory + name);
        if(!image.exists()) return null;
        try {
            return ImageIO.read((image));
        } catch (IOException e) {
            return null;
        }
    }

    public byte[] getImage(String name) {
//        BufferedImage selectedImage = ImageIO.read(directory);

        File image = new File(directory + name);
        if(!image.exists()) return null;
        BufferedImage selectedImage;
        ByteArrayOutputStream outputStream = null;
        try {
            selectedImage = getBufferedImage(name);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(selectedImage, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return outputStream.toByteArray();
    }

    public byte[] getGrayscaleImage(String name) {
        //https://www.tutorialspoint.com/java_dip/grayscale_conversion.htm
        BufferedImage image = getBufferedImage(name);
        if(image == null) return null;
        int width = image.getWidth();
        int height = image.getHeight();
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {
                Color c = new Color(image.getRGB(j, i));
                int red = (int)(c.getRed() * 0.299);
                int green = (int)(c.getGreen() * 0.587);
                int blue = (int)(c.getBlue() *0.114);
                Color newColor = new Color(red+green+blue,red+green+blue,red+green+blue);
                image.setRGB(j,i,newColor.getRGB());
            }
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            return null;
        }
        return outputStream.toByteArray();
    }

    public byte[] getRotatedImage(String name) {
        byte[] image = getImage(name);
        //rotate image

        return null;
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

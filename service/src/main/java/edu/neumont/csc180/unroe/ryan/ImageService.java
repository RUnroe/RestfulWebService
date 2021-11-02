package edu.neumont.csc180.unroe.ryan;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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

@Service
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
        //https://blog.idrsolutions.com/2019/05/image-rotation-in-java/
        BufferedImage image = getBufferedImage(name);
        if(image == null) return null;
        //rotate image
        final double rads = Math.toRadians(90);
        final double sin = Math.abs(Math.sin(rads));
        final double cos = Math.abs(Math.cos(rads));
        final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
        final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
        final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
        final AffineTransform at = new AffineTransform();
        at.translate(w / 2, h / 2);
        at.rotate(rads,0, 0);
        at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
        final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        rotateOp.filter(image,rotatedImage);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(rotatedImage, "png", outputStream);
        } catch (IOException e) {
            return null;
        }
        return outputStream.toByteArray();
    }

    public boolean createImage(MultipartFile image) {
       String imageName = image.getOriginalFilename();

       Path path = Paths.get(directory);
       //Create directory if not there
       if(!Files.exists(path)) {
           try {
               Files.createDirectory(path);
           } catch (IOException e) {
               return false;

           }
       }
       //Create the image
        try(InputStream input = image.getInputStream()){
            Path filePath = path.resolve(imageName);
            Files.copy(input, filePath);
        } catch (IOException e) {
            //Found image with same name already
            System.out.println("return false");
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
            removed = false;
        }

        return removed;
    }

}

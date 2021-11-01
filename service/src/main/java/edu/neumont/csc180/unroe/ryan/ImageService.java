package edu.neumont.csc180.unroe.ryan;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageService {
    List<RestImage> images = new ArrayList<>();

    public BufferedImage getImage(String name) {
        BufferedImage selectedImage = null;
        for (RestImage image: images) {
            if(Objects.equals(image.name, name)) {
                selectedImage = image.image;
            }
        }
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

    public boolean createImage(RestImage image) {
        if (getImage(image.name) != null) return false;
        //add to list
        images.add(image);
        return true;
    }

    public boolean deleteImage(String name) {
        boolean removed = false;
        for (RestImage image: images) {
            if(Objects.equals(image.name, name)) {
                images.remove(image);
                removed = true;

            }
        }
        return removed;
    }

}

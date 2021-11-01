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

}

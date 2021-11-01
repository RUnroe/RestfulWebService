package edu.neumont.csc180.unroe.ryan.controller;

import edu.neumont.csc180.unroe.ryan.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.awt.image.BufferedImage;

@RestController
public class FileController {
    ImageService imageService = new ImageService();

    @PostMapping("/image")
    public ResponseEntity<Image> createImage() {

        return ResponseEntity.ok().body(null);
    }


    @DeleteMapping("/image/{name}")
    public ResponseEntity<Image> deleteImage(@PathVariable(value = "name") String name) {
        Image image = null;
        return ResponseEntity.ok().body(image);
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<Image> getImageTransform(@PathVariable(value = "name") String name, @RequestParam String transform) {
        //Create three cases (empty, rotate, greyscale)
        BufferedImage image;
        switch(transform) {
            case "grayscale":
                image = imageService.getGrayscaleImage(name);

                break;
            case "rotate_clockwise":
                image = imageService.getRotatedImage(name);
                break;
            default:
                image = imageService.getImage(name);
        }
        return ResponseEntity.ok().body(image);
    }
}

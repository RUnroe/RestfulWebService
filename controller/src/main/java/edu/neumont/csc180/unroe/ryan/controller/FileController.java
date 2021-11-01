package edu.neumont.csc180.unroe.ryan.controller;

import edu.neumont.csc180.unroe.ryan.ImageService;
import edu.neumont.csc180.unroe.ryan.RestImage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class FileController {
    ImageService imageService = new ImageService();

    //DELETE ME
    @GetMapping("/ahh")
    public ResponseEntity<String> ahh() {
        return new ResponseEntity<>("ahh", HttpStatus.OK);
    }

    @PostMapping("/image")
    public ResponseEntity<Image> createImage(@RequestParam("file") MultipartFile imageFile) {


        if(imageService.createImage(imageFile))
            return new ResponseEntity<>(HttpStatus.CREATED); //201

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST); //400
    }


    @DeleteMapping("/image/{name}")
    public ResponseEntity<Image> deleteImage(@PathVariable(value = "name") String name) {

        if(!imageService.deleteImage(name)) return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
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
        if(image == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        return new ResponseEntity<>(image, HttpStatus.OK); //200
    }
}

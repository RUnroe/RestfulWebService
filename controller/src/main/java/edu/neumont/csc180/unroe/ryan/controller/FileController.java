package edu.neumont.csc180.unroe.ryan.controller;

import edu.neumont.csc180.unroe.ryan.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

@RestController
public class FileController {
    ImageService imageService = new ImageService();

    //DELETE ME
    @GetMapping("/ahh")
    public ResponseEntity<String> ahh() {
        return new ResponseEntity<>("ahh", HttpStatus.OK);
    }

    @PostMapping("/image")
    public ResponseEntity<String> createImage(@RequestParam("file") MultipartFile imageFile) {


        if(imageService.createImage(imageFile))
            return ResponseEntity.status(HttpStatus.CREATED).body(imageFile.getOriginalFilename()); //201

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: the name already exists"); //400
    }


    @DeleteMapping("/image/{name}")
    public ResponseEntity<Image> deleteImage(@PathVariable(value = "name") String name) {
        System.out.println(name);
        if(!imageService.deleteImage(name)) return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
    }

    @GetMapping(value = "/image/{name}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImageTransform(@PathVariable(value = "name") String name, @RequestParam(required=false) String transform) {
        //Create three cases (empty, rotate, greyscale)
        byte[] image;
        System.out.println(transform);
        System.out.println(name);
        if(transform != null) {
            switch (transform) {
                case "grayscale":
                    image = imageService.getGrayscaleImage(name);
                    break;
                case "rotate_clockwise":
                    image = imageService.getRotatedImage(name);
                    break;
                default:
                    image = imageService.getImage(name);
            }
        }
        else {image = imageService.getImage(name);}
        if(image == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image); //200

//        return new ResponseEntity<>(image, HttpStatus.OK); //200
    }
}

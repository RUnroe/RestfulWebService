package edu.neumont.csc180.unroe.ryan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping("/api/v1")
public class FileController {

    @PostMapping("/image")
    public ResponseEntity<Image> createImage() {

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<Image> getImage(@PathVariable(value = "name") String name) {
        Image image = null;
        return ResponseEntity.ok().body(image);
    }

    @DeleteMapping("/image/{name}")
    public ResponseEntity<Image> deleteImage(@PathVariable(value = "name") String name) {
        Image image = null;
        return ResponseEntity.ok().body(image);
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<Image> getImageTransform(@PathVariable(value = "name") String name, @RequestParam String transform) {
        Image image = null;
        return ResponseEntity.ok().body(image);
    }
}

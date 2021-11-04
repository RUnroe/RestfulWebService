package edu.neumont.csc180.unroe.ryan;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
//import org.apache.commons.io.FileUtils;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceTest {

    ImageService imageService = new ImageService();
    String dataDirectory = "src/test/java/edu/neumont/csc180/unroe/ryan/data/";
    String directory = "src/test/java/edu/neumont/csc180/unroe/ryan/actual/";



    @AfterEach
    void tearDown() {
        File dir = new File(directory);
        File[] files = dir.listFiles();
        if(files != null && files.length > 0) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @Test
    public void testCreateImage_happyPath() throws Exception {
        String fileName = "FileTest.png";
        //Add image to directory
        byte[] bytes = imageService.getImage(dataDirectory, fileName);
        imageService.createImage(directory, fileName, bytes);

        FileInputStream fis = new FileInputStream(directory + fileName);
        byte[] expectedResult = fis.readAllBytes();
        assertTrue(Arrays.equals(expectedResult, bytes));

    }

    @Test
    public void testCreateImage_fileAlreadyExists_false() throws Exception {
        //Add image to directory
        byte[] bytes = imageService.getImage(dataDirectory, "alreadyExists.png");
        imageService.createImage(directory, "alreadyExists.png", bytes);


        String fileName = "alreadyExists.png";
        //Try to add same image to directory
        assertFalse(imageService.createImage(directory, fileName, bytes));

    }


    @Test
    public void testGetImage_happyPath() throws Exception {
        String fileName = "FileTest.png";
        //Add image to directory
        byte[] bytes = imageService.getImage(dataDirectory, fileName);
        imageService.createImage(directory, fileName, bytes);

        assertTrue(imageService.getImage(directory, fileName) != null);

    }

    @Test
    public void testGetImage_fileNotFound_null() throws Exception {
        String fileName = "FileTest.png";

        assertTrue(imageService.getImage(directory, fileName) == null);

    }

    @Test
    public void testDeleteImage_happyPath() throws Exception {
        String fileName = "FileTest.png";
        //Add image to directory
        byte[] bytes = imageService.getImage(dataDirectory, fileName);
        imageService.createImage(directory, fileName, bytes);

        assertTrue(imageService.deleteImage(directory, fileName));

    }

    @Test
    public void testDeleteImage_fileNotFound_false() throws Exception {
        String fileName = "FileTest.png";

        assertFalse(imageService.deleteImage(directory, fileName));

    }



    @Test
    public void testGetGreyscaleImage_happyPath() throws Exception {
        String fileName = "FileTest.png";
        //Add image to directory
        byte[] bytes = imageService.getImage(dataDirectory, fileName);
        imageService.createImage(directory, fileName, bytes);

        assertTrue(imageService.getGrayscaleImage(directory, fileName) != null);

    }

    @Test
    public void testGetGreyscaleImage_fileNotFound_null() throws Exception {
        String fileName = "FileTest.png";
        assertTrue(imageService.getGrayscaleImage(directory, fileName) == null);

    }

    @Test
    public void testGetRotatedImage_happyPath() throws Exception {
        String fileName = "FileTest.png";
        //Add image to directory
        byte[] bytes = imageService.getImage(dataDirectory, fileName);
        imageService.createImage(directory, fileName, bytes);

        assertTrue(imageService.getRotatedImage(directory, fileName) != null);

    }

    @Test
    public void testGetRotatedImage_fileNotFound_null() throws Exception {
        String fileName = "FileTest.png";
        assertTrue(imageService.getRotatedImage(directory, fileName) == null);

    }

}
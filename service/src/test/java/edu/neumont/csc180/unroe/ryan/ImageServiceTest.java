package edu.neumont.csc180.unroe.ryan;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ImageServiceTest {
    @Autowired
    ImageService imageService;
    String directory;
    void setUp() {
        imageService = new ImageService();
        directory = "service/src/test/java/edu/neumont/csc180/unroe/ryan/actual/"; // figure it out
        //create Image so that we can test already exists case
    }

    @AfterEach
    void tearDown() {
        //TODO - Delete everything inside of actual
    }

    @Test
    public void testCreateImage_happyPath() throws Exception {
        String fileName = "FileTest";
        //make image instead
        //store test image in data package
        byte[] fileContent = "filecontent".getBytes(StandardCharsets.UTF_8);

        imageService.createImage(directory, fileName, fileContent);

        FileInputStream fis = new FileInputStream("/images/FileTest.png");
        byte[] expectedResult = fis.readAllBytes();
        FileInputStream fis2 = new FileInputStream(fileName);
        byte[] actualResult = fis2.readAllBytes();
        assertEquals(expectedResult, actualResult);

    }

    @Test
    public void testCreateImage_fileAlreadyExists_fail() throws Exception {
        String fileName = "FileTest";
        byte[] fileContent = "filecontent".getBytes(StandardCharsets.UTF_8);

        assertFalse(imageService.createImage(directory, fileName, fileContent));



    }



}
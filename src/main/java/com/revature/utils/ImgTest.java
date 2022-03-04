package com.revature.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

public class ImgTest {

    public static void testFunction() {
        System.out.println("Initializing test yo: ");
        File file = new File("C:\\Users\\Bobby\\Documents\\Coding\\Revature_Projects\\Swag_photos\\swag_mug.png");

        try {
            Image image = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] fileContent;

        try {
            fileContent = Files.readAllBytes(file.toPath());
            System.out.println(fileContent);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

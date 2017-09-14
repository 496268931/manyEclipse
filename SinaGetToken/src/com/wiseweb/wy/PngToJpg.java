package com.wiseweb.wy;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PngToJpg {

   public static void main(String[] args) {

    BufferedImage bufferedImage;

    try {

      //read image file
      bufferedImage = ImageIO.read(new File("D:\\AutoScreenCapture\\xh3m.jpg"));

      // create a blank, RGB, same width and height, and a white background
      BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
            bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

     //TYPE_INT_RGB:����һ��RBGͼ��24λ��ȣ��ɹ���32λͼת����24λ

      newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

      // write to jpeg file
      ImageIO.write(newBufferedImage, "jpg", new File("D:\\AutoScreenCapture\\png\\xxyy.jpg"));

      System.out.println("Done");

    } catch (IOException e) {

      e.printStackTrace();

    }

   }

}
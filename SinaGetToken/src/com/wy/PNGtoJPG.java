package com.wy;
 
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.STRING;

public class PNGtoJPG {
	
	 
   public static  void pngTOjpg(String pngPATH,String jpgPATH) {

    BufferedImage bufferedImage;

    try {

      int i=0;
	//read image file
      bufferedImage = ImageIO.read(new File(pngPATH));

      // create a blank, RGB, same width and height, and a white background
      BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
            bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

    //TYPE_INT_RGB:创建一个RBG图像，24位深度，成功将32位图转化成24位

      newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

      // write to jpeg file
      ImageIO.write(newBufferedImage, "jpg", new File(jpgPATH));

      System.out.println("pngTOjpg is Done");

    } catch (IOException e) {

      e.printStackTrace();

    }

   }

}
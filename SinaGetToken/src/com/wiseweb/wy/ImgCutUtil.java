package com.wiseweb.wy;

import java.awt.Rectangle;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.util.Iterator;  
  
import javax.imageio.ImageIO;  
import javax.imageio.ImageReadParam;  
import javax.imageio.ImageReader;  
import javax.imageio.stream.ImageInputStream;  
  
public class ImgCutUtil {  
  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
  
        ImgCutUtil.cut(120, 575, 300, 65, "D:\\AutoScreenCapture\\jpg\\1.jpg", "D:\\AutoScreenCapture\\jpg\\11.jpg");  
  
    }  
  
    /** 
     * ͼƬ���� 
     * @param x1 ѡ���������Ͻǵ�x���� 
     * @param y1 ѡ���������Ͻǵ�y���� 
     * @param width ѡ������Ŀ�� 
     * @param height ѡ������ĸ߶� 
     * @param sourcePath ԴͼƬ·�� 
     * @param descpath ���к�ͼƬ�ı���·�� 
     */  
    public static void cut(int x1, int y1, int width, int height,  
            String sourcePath, String descpath) {  
  
        FileInputStream is = null;  
        ImageInputStream iis = null;  
        try {  
            is = new FileInputStream(sourcePath);  
            String fileSuffix = sourcePath.substring(sourcePath  
                    .lastIndexOf(".") + 1);  
            Iterator<ImageReader> it = ImageIO  
                    .getImageReadersByFormatName(fileSuffix);  
            ImageReader reader = it.next();  
            iis = ImageIO.createImageInputStream(is);  
            reader.setInput(iis, true);  
            ImageReadParam param = reader.getDefaultReadParam();  
            Rectangle rect = new Rectangle(x1, y1, width, height);  
            param.setSourceRegion(rect);  
            BufferedImage bi = reader.read(0, param);  
            ImageIO.write(bi, fileSuffix, new File(descpath));  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        } finally {  
            if (is != null) {  
                try {  
                    is.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                is = null;  
            }  
            if (iis != null) {  
                try {  
                    iis.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
                iis = null;  
            }  
        }  
  
    }  
  
}  
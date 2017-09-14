package com.wiseweb.wy;

/**
 * 
 */
 

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * @date 2012-11-26
 * @author xhw
 * 
 */
public class JpgCut {

    /**
     * ԴͼƬ·��������:c:\1.jpg
     */
    //private String srcpath = "e:/poool.jpg";
    private String srcpath = "D:\\AutoScreenCapture\\xh3m.jpg";
    
    /**
     * ����ͼƬ���·������.��:c:\2.jpg
     */
    //private String subpath = "e:/pool_end";
    private String subpath = "D:\\AutoScreenCapture\\wy.jpg";
    /**
     * jpgͼƬ��ʽ
     */
    private static final String IMAGE_FORM_OF_JPG = "jpg";
    /**
     * pngͼƬ��ʽ
     */
    private static final String IMAGE_FORM_OF_PNG = "png";
    /**
     * ���е�x����
     */
    private int x;

    /**
     * ���е�y����
     */
    private int y;

    /**
     * ���е���
     */
    private int width;

    /**
     * ���е�߶�
     */
    private int height;

    public JpgCut() {

    }

    public JpgCut(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public static void main(String[] args) throws Exception {
        JpgCut imageCut = new JpgCut(134, 0, 366, 366);
        imageCut.cut(imageCut.getSrcpath(), imageCut.getSubpath());
    }

    /**
     * ���ذ������е�ǰ��ע�� ImageReader �� Iterator����Щ ImageReader �����ܹ�����ָ����ʽ��
     * ������formatName - ��������ʽ��ʽ���� .������ "jpeg" �� "tiff"���� ��
     * 
     * @param postFix
     *            �ļ��ĺ�׺��
     * @return
     */
    public Iterator<ImageReader> getImageReadersByFormatName(String postFix) {
        switch (postFix) {
        case IMAGE_FORM_OF_JPG:
            return ImageIO.getImageReadersByFormatName(IMAGE_FORM_OF_JPG);
        case IMAGE_FORM_OF_PNG:
            return ImageIO.getImageReadersByFormatName(IMAGE_FORM_OF_PNG);
        default:
            return ImageIO.getImageReadersByFormatName(IMAGE_FORM_OF_JPG);
        }
    }

    /**
     * ��ͼƬ�ü������Ѳü��군��ͼƬ���� ��
     * @param srcpath ԴͼƬ·��
     * @param subpath ����ͼƬ���·��
     * @throws IOException
     */
    public void cut(String srcpath, String subpath) throws IOException {
        FileInputStream is = null;
        ImageInputStream iis = null;
        try {
            // ��ȡͼƬ�ļ�
            is = new FileInputStream(srcpath);

            // ��ȡ�ļ��ĺ�׺��
            String postFix = getPostfix(srcpath);
            System.out.println("ͼƬ��ʽΪ��" + postFix);
            /*
             * ���ذ������е�ǰ��ע�� ImageReader �� Iterator����Щ ImageReader �����ܹ�����ָ����ʽ��
             * ������formatName - ��������ʽ��ʽ���� .������ "jpeg" �� "tiff"���� ��
             */
            Iterator<ImageReader> it = getImageReadersByFormatName(postFix);

            ImageReader reader = it.next();
            // ��ȡͼƬ��
            iis = ImageIO.createImageInputStream(is);

            /*
             * <p>iis:��ȡԴ.true:ֻ��ǰ���� </p>.�������Ϊ ��ֻ��ǰ��������
             * ��������ζ�Ű���������Դ�е�ͼ��ֻ��˳���ȡ���������� reader ���⻺���������ǰ�Ѿ���ȡ��ͼ����������ݵ���Щ���벿�֡�
             */
            reader.setInput(iis, true);

            /*
             * <p>������ζ������н������<p>.����ָ�����������ʱ�� Java Image I/O
             * ��ܵ��������е���ת��һ��ͼ���һ��ͼ�������ض�ͼ���ʽ�Ĳ�� ������ ImageReader ʵ�ֵ�
             * getDefaultReadParam �����з��� ImageReadParam ��ʵ����
             */
            ImageReadParam param = reader.getDefaultReadParam();

            /*
             * ͼƬ�ü�����Rectangle ָ��������ռ��е�һ������ͨ�� Rectangle ����
             * �����϶�������꣨x��y������Ⱥ͸߶ȿ��Զ����������
             */
            Rectangle rect = new Rectangle(x, y, width, height);

            // �ṩһ�� BufferedImage���������������������ݵ�Ŀ�ꡣ
            param.setSourceRegion(rect);
            /*
             * ʹ�����ṩ�� ImageReadParam ��ȡͨ������ imageIndex ָ���Ķ��󣬲��� ����Ϊһ��������
             * BufferedImage ���ء�
             */
            BufferedImage bi = reader.read(0, param);

            // ������ͼƬ
            ImageIO.write(bi, postFix, new File(subpath + "_" + new Date().getTime() + "." + postFix));
        } finally {
            if (is != null)
                is.close();
            if (iis != null)
                iis.close();
        }

    }

    /**
     * ��ȡinputFilePath�ĺ�׺�����磺"e:/test.pptx"�ĺ�׺��Ϊ��"pptx"<br>
     * 
     * @param inputFilePath
     * @return
     */
    public String getPostfix(String inputFilePath) {
        return inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSrcpath() {
        return srcpath;
    }

    public void setSrcpath(String srcpath) {
        this.srcpath = srcpath;
    }

    public String getSubpath() {
        return subpath;
    }

    public void setSubpath(String subpath) {
        this.subpath = subpath;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
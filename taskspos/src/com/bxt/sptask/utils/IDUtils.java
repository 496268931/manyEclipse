package com.bxt.sptask.utils;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Random;

public class IDUtils{
  private static SimpleDateFormat dateformatMillis = new SimpleDateFormat("yyyyMMddHHmmssSSS");

  public static String genImageName()
  {
    long millis = System.currentTimeMillis();

    Random random = new Random();
    int end3 = random.nextInt(999);

    String str = millis + String.format("%03d", new Object[] { Integer.valueOf(end3) });

    return str;
  }

  public static long genItemId()
  {
    String dateStr = "";

    long millis = System.currentTimeMillis();

    GregorianCalendar gc = new GregorianCalendar();
    gc.setTimeInMillis(millis);
    dateStr = dateformatMillis.format(gc.getTime());
    Random random = new Random();
    int end2 = random.nextInt(99);

    dateStr = dateStr + String.format("%02d", new Object[] { Integer.valueOf(end2) });
    long id = new Long(dateStr).longValue();
    return id;
  }

  public static void main(String[] args) {
    System.out.println(genItemId());
  }
}
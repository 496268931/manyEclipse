package com.wy;

import java.util.ArrayList;
import java.util.List;



public class Test {
	public static void main(String args[]) {
		java.util.Random random=new java.util.Random();
		
	 
		List<Integer> listBLUE = new ArrayList<Integer>();
		List<Integer> listRED = new ArrayList<Integer>();
		
		
		for (int i = 1; i <=6; i++) {
			int blue=random.nextInt(34);
			blue=blue+1;
			listBLUE.add(blue); 
			
		}
		
		int red=random.nextInt(17)+1;
		red=red+1;
		listRED.add(red);
		
		System.out.println(listBLUE);
		System.out.println(listRED);
	}
}

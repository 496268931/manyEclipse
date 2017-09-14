package com.test;

public class TestObject {
	
	public void testRegex(){
		String  sregex = "http://bbs\\.auto\\.sina\\.com\\.cn/.+ http://bbs\\.auto\\.sina\\.com\\.cn/logging\\.php\\?action=.+";
		String[] ss = sregex.split("[\\r\\n,\\s]+");
		System.out.print(ss.toString());
	}
	
	
	public static void main(String[] args) {
		TestObject toj = new TestObject();
		toj.testRegex();
	}

}

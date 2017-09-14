package com.test;

import java.util.Date;

import com.bxt.sptask.utils.DateTimeUtil;

public class TestDateTime {

	public static void main(String[] args) {
		DateTimeUtil dtu = new DateTimeUtil();
		System.out.println(dtu.toDateStr(new Date()));
	}
}

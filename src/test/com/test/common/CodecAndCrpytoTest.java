package com.test.common;


import org.apache.shiro.codec.Base64;
import org.junit.Test;

public class CodecAndCrpytoTest {
	@Test
	public void testBase64(){
		String str="shouke-plus.2016";
		System.out.println(Base64.encodeToString(str.getBytes()));
		//byte[] aa=Base64.decode("kPH+bIxk5D2deZiIxcaaaA==");
		byte[] aa=Base64.decode("kPH+4AvVhmFLUs0KTA3Kprsdag==");
		System.out.println(aa.length);
	}
}

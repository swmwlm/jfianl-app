package com.test.xssfilter;

import com.langmy.jFinal.handler.xss.xssfilter.XssFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ImgTagTest {

	private XssFilter xssFilter;

	@Before
	public void setup() {
		xssFilter = XssFilter.getInstance();
	}

	@Test
	public void javascriptTest() {
		String dirty = "<img src=\"javascript:alert('XSS');\">";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("<img />"));
	}

	@Test
	public void vbscriptTest() {
		String dirty = "<IMG SRC='vbscript:msgbox(\"XSS\")'>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("<img />"));
	}

	@Test
	public void encoding1Test() {
		String dirty = "<img src=&#106;&#97;&#118;&#97;&#115;&#99; &#114;&#105;&#112;&#116;&#58;&#97;&#108;&#101; &#114;&#116;&#40;&#39;&#88;&#83;&#83;&#39;&#41;>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("<img />"));
	}

	@Test
	public void encoding2Test() {
		String dirty = "<img src=&#0000106&#0000097&#0000118&#0000097 &#0000115&#0000099&#0000114&#0000105&#0000112 &#0000116&#0000058&#0000097&#0000108&#0000101 &#0000114&#0000116&#0000040&#0000039&#0000088 &#0000083&#0000083&#0000039&#0000041>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("<img />"));
	}

	@Test
	public void encoding3Test() {
		String dirty = "<IMG SRC=&#x6A&#x61&#x76&#x61&#x73&#x63&#x72&#x69 &#x70&#x74&#x3A&#x61&#x6C&#x65&#x72&#x74&#x28&#x27 &#x58&#x53&#x53&#x27&#x29>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("<img />"));
	}

}
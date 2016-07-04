package com.test.xssfilter;

import com.shoukeplus.jFinal.handler.xss.xssfilter.XssFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ScriptTest {

	private XssFilter xssFilter;

	@Before
	public void setup() {
		xssFilter = XssFilter.getInstance();
	}

	@Test
	public void encodingTest() {
		String dirty = "\"%3cscript%3ealert(document.cookie)%3c/script%3e";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("&quot;%3cscript%3ealert(document.cookie)%3c/script%3e"));
	}

	@Test
	public void basicScript1Test() {
		String dirty = "<script>alert('attack');</script>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals(""));
	}

	@Test
	public void basicScript2Test() {
		String dirty = "<SCRIPT>x=/XSS/  alert(x.source)</SCRIPT>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals(""));
	}

	@Test
	public void basicSrcTest() {
		String dirty = "<SCRIPT SRC=http://evil-site.com/xss.js> </SCRIPT>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals(""));
	}

	@Test
	public void reiterate1Test() {
		String dirty = "<scr<script>ipt>alert(document.cookie)</script>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("ipt&gt;alert(document.cookie)"));
	}

	@Test
	public void reiterate2Test() {
		String dirty = "<SCRIPT%20a=\">\"%20SRC=\"http://attacker/xss.js\"></SCRIPT>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("&quot;%20SRC=&quot;http://attacker/xss.js&quot;&gt;"));
	}

	@Test
	public void reiterate3Test() {
		String dirty = "<script&param=>[...]</&param=script>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("[...]"));
	}

}
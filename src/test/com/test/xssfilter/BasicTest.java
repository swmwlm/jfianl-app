package com.test.xssfilter;

import com.langmy.jFinal.handler.xss.xssfilter.XssFilter;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BasicTest {

	private XssFilter xssFilter;

	@Before
	public void setup() {
		xssFilter = XssFilter.getInstance();
	}
	
	@Test
	public void oriTest() {
		String dirty = "<b>이것은 볼드체</b>1<br/>2<br>3<br > <br />4띄어쓰기";
		String clean = Jsoup.clean(dirty, Whitelist.none());
		Assert.assertTrue(clean.equals("이것은 볼드체123 4띄어쓰기"));
	}

	@Test
	public void parameter1Test() {
		String dirty = "Site.com/search.php?search=<script>alert(\"XSS\");</script>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("Site.com/search.php?search="));
	}

	@Test
	public void parameter2Test() {
		String dirty = "http://example.com/index.php?user=<script>alert(123)</script>&p=123";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("http://example.com/index.php?user=&amp;p=123"));
	}

	@Test
	public void parameter3Test() {
		String dirty = "http://example.com/index.php?user=<script>window.onload = function() {var AllLinks=document.getElementsByTagName(\"a\"); AllLinks[0].href = \"http://badexample.com/malicious.exe\"; }</script> ";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("http://example.com/index.php?user="));
	}

	@Test
	public void onfocusTest() {
		String dirty = "\" onfocus=\"alert(document.cookie)";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("&quot; onfocus=&quot;alert(document.cookie)"));
	}

	@Test
	public void onmouseoverTest() {
		String dirty = "<b onmouseover=alert('Wufff!')>click me!</b>";
		String clean = XssFilter.getInstance().doFilter(dirty);
		Assert.assertTrue(clean.equals("<b>click me!</b>"));
	}

}
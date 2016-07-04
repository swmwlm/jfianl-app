package com.test.xssfilter;

import com.shoukeplus.jFinal.handler.xss.xssfilter.XssFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EtcTest {

	private XssFilter xssFilter;

	@Before
	public void setup() {
		xssFilter = XssFilter.getInstance();
	}

	@Test
	public void iframeTest() {
		String dirty = "<iframe src=http://evil-site.com/evil.html ";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals(""));
	}

	@Test
	public void bodyTest() {
		String dirty = "<BODY BACKGROUND=\"javascript:alert('XSS')\"> ";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals(""));
	}

	@Test
	public void linkTest() {
		String dirty = "<LINK REL=\"stylesheet\" HREF=\"javascript:alert('XSS');\">";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals(""));
	}

	@Test
	public void metaTest() {
		String dirty = "<META HTTP-EQUIV=\"refresh\" CONTENT=\"0;url=javascript:alert('XSS');\">";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals(""));
	}

	@Test
	public void innerForm() {
		String dirty = "<form>폼안에 있는 내용은 어떻게 되나요?</form>";
		String clean = xssFilter.doFilter(dirty);
		Assert.assertTrue(clean.equals("폼안에 있는 내용은 어떻게 되나요?"));
	}

}
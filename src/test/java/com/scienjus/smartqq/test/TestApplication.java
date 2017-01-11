package com.scienjus.smartqq.test;

import junit.framework.TestSuite;
import junit.textui.TestRunner;

/**
 * @author <a href="mailto:xianguang.zhou@outlook.com">Xianguang Zhou</a>
 * @since 2017/01/11.
 */
public class TestApplication {
	public static void main(String[] args) {
		TestSuite testSuite = new TestSuite(LoginTest.class, "testLogin");
		TestRunner.run(testSuite);
	}
}

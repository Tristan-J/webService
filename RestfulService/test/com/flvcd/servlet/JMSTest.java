/**
 * 
 */
package com.flvcd.servlet;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;

/**
 * @author tristan
 *
 */
public class JMSTest {

	// including asynchronously receive test
	@Test
	public void testProvideAndConsumeMessage() {
		try {
			for (int i = 0;i < 10; i++){
				MyPublish provider = new MyPublish();
				if (true != provider.sendMessage("this is a test")){
					fail("ERROR");
				}
			}
		} catch(Exception e) {
			System.out.println(e);
			fail("ERROR");
		}
	}
}

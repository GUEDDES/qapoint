package com.android.qapoint.restclient;

import junit.framework.TestCase;

public class RestClientTest extends TestCase {

	public void testConnect() {

		RestClient.connect("http://10.0.2.2:8080/rest/todo");
	}

}

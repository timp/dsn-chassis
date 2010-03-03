package org.cggh.chassis.generic.http.test;


public class AtomAuthorFilterPostTest extends AtomAuthorFilterDeleteTest {

	public AtomAuthorFilterPostTest(String name) {
		super(name);
	}

	protected String getMethod() { 
		return "POST";
	}
}

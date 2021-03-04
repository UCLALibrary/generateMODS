package edu.ucla.library.dep.GenerateMods;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class OpenCsvIdepTest {

	@Test
	void testCreateSubjectElement() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateDateElement() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateLanguage() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateRelatedItemElement() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateElementFromString() {
		fail("Not yet implemented");
	}
	@Test
	void testGetInput() {

	    String input = "MEAP";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);

	    assertEquals("MEAP", OpenCsvIdep.getInput("Enter program : "));

	}

	@Test
	void testMain() {
		fail("Not yet implemented");
	}

}

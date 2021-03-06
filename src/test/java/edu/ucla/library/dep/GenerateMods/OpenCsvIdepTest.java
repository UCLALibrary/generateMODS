package edu.ucla.library.dep.GenerateMods;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OpenCsvIdepTest {

	@Test
	@DisplayName("Create Subject")
	@Disabled
	void testCreateSubjectElement() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Create Date")
	@Disabled
	void testCreateDateElement() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Create Language")
	@Disabled
	void testCreateLanguage() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Create Related Element")
	@Disabled
	void testCreateRelatedItemElement() {
		fail("Not yet implemented");
	}

	@Test
	@DisplayName("Create Element")
	@Disabled
	void testCreateElementFromString() {
		fail("Not yet implemented");
	}
	
	@Test
	@DisplayName("Get User Input")
	void testGetInput() {

	    String input = "MEAP";
	    InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);

	    assertEquals("MEAP", OpenCsvIdep.getInput("Enter program : "));

	}

	@Test
	@DisplayName("Main")
	@Disabled
	void testMain() {
		fail("Not yet implemented");
	}

}

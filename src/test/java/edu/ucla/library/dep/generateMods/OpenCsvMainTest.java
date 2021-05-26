package edu.ucla.library.dep.generateMods;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

class OpenCsvMainTest {


    @Test
    void testGetInput() {

        String input = "MEAP";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals("MEAP", OpenCsvMain.getInput("Enter program : "));

    }


}

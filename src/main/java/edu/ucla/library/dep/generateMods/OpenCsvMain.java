package edu.ucla.library.dep.generateMods;

import java.io.IOException;
import java.util.Scanner;

import edu.ucla.library.dep.generateMods.services.AssembleMods;

public class OpenCsvMain {

	public static void main(String[] args) throws IllegalStateException, IOException {
		String infilePath = getInput("InputFilePath is: ");
		String outfilePath = getInput("OutputFilePath is: ");
		String program = getInput("Enter program (IDEP or MEAP): ");

		System.out.println("Program is: " + program);
		if (null == program || null == infilePath || null == outfilePath) {
			System.out.println(
					"Proper Usage is: java -jar GenerateMods.jar \n Enter user input for program, inputfilepath, outputfilepath");
			System.exit(0);
		}
		String projectName = getInput("Project name is: ");
		String projectURL = getInput("Project URL is: ");

		AssembleMods assembleMods = new AssembleMods(projectName, projectURL, program, infilePath, outfilePath);
		assembleMods.generateMods();

	}

	public static String getInput(String inputText) {
		String input;
		Scanner myObj = new Scanner(System.in);
		
		// Enter input file path and press Enter
		System.out.println("Enter " + inputText);
		input = myObj.nextLine();

		System.out.println(inputText + input);
		myObj.close();
		return input;
	}

}

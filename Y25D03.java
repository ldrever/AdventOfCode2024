import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Y25D03 {

	public static void main(String[] args) {
		ArrayList<String> inputLines = new ArrayList<>();
		Scanner diskScanner = null;
		try {
			diskScanner = new Scanner(new File("Y:\\code\\github_mastered\\cloned_2025-11-27\\AdventOfCode2024\\Y25D03F1.dat"));
		} catch (Exception e) {
			// trust no IOExceptions occur
		}
		while (diskScanner.hasNext()) inputLines.add(diskScanner.nextLine());
		diskScanner.close();

		long joltage = 0;
		int batteriesNeeded = 12; // or 2 for part 1

		for (String inputLine : inputLines) {
			Y25D03_ProgressElement ele = new Y25D03_ProgressElement("", inputLine, batteriesNeeded);
			long lineResult = Long.parseLong(ele.evaluate());
			joltage += lineResult;
		}

		System.out.println("Joltage: " + joltage);

	}
}
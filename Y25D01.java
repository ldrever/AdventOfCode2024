import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Y25D01 {

	public static void main(String[] args) {
		ArrayList<String> inputLines = new ArrayList<>();
		Scanner diskScanner = null;
		try {
			diskScanner = new Scanner(new File("Y:\\code\\github_mastered\\cloned_2025-11-27\\AdventOfCode2024\\Y25D01F1.dat"));
		} catch (Exception e) {
			// trust no IOExceptions occur
		}
		while (diskScanner.hasNext()) inputLines.add(diskScanner.nextLine());
		diskScanner.close();

		int position = 50;
		int zeroCount = 0;
		int lineNumber = 0;

		int problemPart = 2;

		for(String inputLine : inputLines) {
			String direction = inputLine.substring(0,1);
			int adjustment = Integer.parseInt(inputLine.substring(1)); // LDFIXME LEARNT that can take a single argument to do "all BUT left"


			// OK let's do it the stupid way, seeing as the clever way isn't getting there...
			int movement = 1;
			if(direction.equalsIgnoreCase("L")) movement *= -1;

			for(int i = 0; i < adjustment; i++) {
				position += movement;
				position %= 100;
				if(position == 0) zeroCount++;
			}

			// DAMMIT that worked - how's the proper way going wrong? guess need to build a yad that keeps a running difference tween them, to REALLY grok the blem..

			// position += adjustment;




/*
			if(problemPart == 2) {
				if(adjustment > 0) {
					while(position > 100) {
						zeroCount++;
						position -= 100;
					}
				}

				if(adjustment < 0) {
					while(position < 0) {
						zeroCount++;
						position += 100;
					}
				}

			}

			position %= 100;
			if (position == 0) zeroCount++;
*/
			// LDFIXME
			// REN REPO
			// REN OLD FILE
			// DAT CONSOLE THING FROM THAT STANDALONE YAD
			// MEANS O HANDLING CGHTS!

			// fcourse sabout the syntax of trycatch, string convert, yadda yadda yadda
			// fcourse humility fra wrong answers..

			// OH AND FCOURSE IT TEACHES YOU TETH SUBTLE OFF BY 1 ERRORS...
			// ensystematize encapture o such lessa?
			// obvs HOW DA IT BEEN DONE?

			// test suite such that get many inputs and their outputs in a grid for verification!!!


		} // loop
		System.out.print("Zeroes found ");
		System.out.println(zeroCount);
	} // main
}
// import java.lang.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Y25D02 {

	public static void main(String[] args) {
		long output = 0L;
		String strInput = "199617-254904,7682367-7856444,17408-29412,963327-1033194,938910234-938964425,3207382-3304990,41-84,61624-105999,1767652-1918117,492-749,85-138,140-312,2134671254-2134761843,2-23,3173-5046,16114461-16235585,3333262094-3333392446,779370-814446,26-40,322284296-322362264,6841-12127,290497-323377,33360-53373,823429-900127,17753097-17904108,841813413-841862326,518858-577234,654979-674741,773-1229,2981707238-2981748769,383534-468118,587535-654644,1531-2363";
		String[] rangeStrings = strInput.split(",");

		for (String rangeString : rangeStrings) {
			System.out.print(rangeString + ": ");
			ArrayList<Y25D02_Range> attackables = new Y25D02_Range(rangeString).breakup();

			for(Y25D02_Range range : attackables) {
				//System.out.print(range.toString() + "; ");
				ArrayList<Long> validOnes = range.findValidMembers();
				for(long validOne : validOnes) output += validOne;
			}
			//System.out.println();

		}

		System.out.println("Final answer: " + output);

/*

		Y25D02_Range myRange = new Y25D02_Range(1000,9999);
		for(long l : myRange.findValidMembers()) System.out.println(l);
		//for(Y25D02_Range attackable : myRange.breakup()) System.out.print(attackable.toString()+"; ");


		Y25D02_Range newRange = new Y25D02_Range("40-50");
		System.out.println("this is..." + newRange.toString());

		System.out.println((int) Math.log10(100));

		System.out.println((int)4.8);

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

		*/
	} // main
}
import java.util.*;
import java.io.*;

public class Day16_Retry{

	public static void main(String[] args) {
			boolean debug = true;

			String filePath = "Y:\\code\\github_mastered\\cloned_2025-11-27\\AdventOfCode2024\\Day16small.dat";
			Day16LetterGrid lg = null;
			try {
				lg = new Day16LetterGrid(filePath);
			}
			catch (Exception e) {System.out.println("file processing error");}

			lg.displayArray();



			Day16RoomState start = lg.findStartRoomState(true);

			if(debug) System.out.println("Now to paths");

			/*

				Remember why we need ROOMS and not just any old corridor-cell? It's because...
				actually I'm not sure any more.

			*/

			Day16RoomHistoryPath rhp = new Day16RoomHistoryPath(start);

			ArrayList<Day16RoomHistoryPath> results = rhp.extend(debug);

			for(Day16RoomHistoryPath result : results) result.display();

			// LDFIXME next up is to recurse and build longer paths
			// LDFIXME also implement showment on a grid!!


			// LDFIXME EVENTUALLY remove the old day16
	}

}
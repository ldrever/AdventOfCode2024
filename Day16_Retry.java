import java.util.*;
import java.io.*;

public class Day16_Retry{

	public static void main(String[] args) {

			boolean debug = false;

			String filePath = "Y:\\code\\github_mastered\\cloned_2025-11-27\\AdventOfCode2024\\Day16small.dat";
			Day16LetterGrid lg = null;
			try {
				lg = new Day16LetterGrid(filePath);
			}
			catch (Exception e) {System.out.println("file processing error");}

			//lg.display();

			Day16RoomState start = lg.findStartRoomState(true);

			Day16CompleteZone newZone = new Day16CompleteZone(start, 0, 0);
			int interval = 1001;
			for(int i = 1; ; i++) {
				Day16CompleteZone currentZone = newZone;
				int min = 1 + (i-1) * 1001;
				int max = 1001 * i;
				newZone = currentZone.buildNewZone(min, max);
				//newZone.show();
				System.out.print("Here are all endpoints reachable with least cost between ");
				System.out.print(min);
				System.out.print(" and ");
				System.out.println(max);


				int endCost = newZone.getEndCost();

				if(endCost != -1) {


					System.out.print("FINAL COST: ");
					System.out.println(endCost);
					break;
				}

				/*

					The approach for part 2 will be to keep track of "Viable Winning Elements" -
					paths that start from a state in one completion zone, and end in the next.
					I.e., they deliver that destination at minimal cost.

					The process of identifying rooms on ANY winning path is then going to be:
					-	starting with the LAST complete zone, i.e. the one containing the end
						room, identify the subset of rooms in the zone before that can get
						there, and the paths by which they do so
					-	recursively treat that subset in the same way, and repeat all the way
						back to the starting square

				*/



			} // i loop


			// LDFIXME EVENTUALLY remove the old day16
	}

}
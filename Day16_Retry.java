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

			lg.display();

			Day16RoomState start = lg.findStartRoomState(true);

			Day16CompleteZone newZone = new Day16CompleteZone(start, 0, 0);
			int interval = 1001;
			for(int i = 1; ; i++) {
				Day16CompleteZone currentZone = newZone;
				int min = 1 + (i-1) * 1001;
				int max = 1001 * i;
				newZone = currentZone.buildNewZone(min, max);
				System.out.print("endpoints reachable with least cost between ");
				System.out.print(min);
				System.out.print(" and ");
				System.out.print(max);
				System.out.println(" includes a state-count of " + newZone.getStateCount());

				int endCost = newZone.getEndCost();

				if(endCost != -1) {


					System.out.print("State-based and hard-comparisons. Final cost: ");
					System.out.println(endCost);
					break;
				}

			} // i loop


			// LDFIXME EVENTUALLY remove the old day16
	}

}
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

			//lg.display();

			Day16RoomState start = lg.findStartRoomState(true);

			int interval = 1001;

			ArrayList<Day16CompleteZone> zones = new ArrayList<Day16CompleteZone>();
			zones.add(new Day16CompleteZone(start, 0, 0));

			for(int i = 1; ; i++) {

				Day16CompleteZone currentZone = zones.get(zones.size() - 1);

				int min = 1 + (i-1) * 1001;
				int max = 1001 * i;
				Day16CompleteZone newZone = currentZone.buildNewZone(min, max);
				zones.add(newZone);
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




			} // i loop

			/*

				Now for part 2. The zones we captured along the way should provide enough information
				to determine OTHER winning paths, not just the one we happened to use as a demonstration.



			*/


			// let's take the final zone, and prune it down to JUST the winning states
			zones.get(zones.size() - 1).restrictToWinners();

			// now in a position to winnow down any zone, so that its only survivors are those who can reach
			// the next-higher-valued / last-processed zone

			int i = zones.size();

			Day16RoomHistoryPath sittableSpots = new Day16RoomHistoryPath();
			HashSet<String> spots = new HashSet<String>();

			do {
				i--;
				Day16CompleteZone higherZone = zones.get(i);
				Day16CompleteZone lowerZone = zones.get(i - 1);
				ArrayList<Day16RoomState> result = lowerZone.restrictToHigherZoneReachers(higherZone);

				for(Day16RoomState element : result) {
					sittableSpots.addRoomWithoutCostUpdate(element);
					spots.add(element.toString());

				}

				System.out.print(i);System.out.println(" zone's finest:");
				//if(i % 20 == 0) lowerZone.show();
			} while (i > 1);


			sittableSpots.show();

			System.out.println("Seems viable?");

			System.out.println(spots.size());


			// LDFIXME EVENTUALLY remove the old day16
	}

}
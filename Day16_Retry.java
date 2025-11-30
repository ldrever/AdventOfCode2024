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
/*
			if(debug) System.out.println("Now to paths?");

			ArrayList<Day16RoomHistoryPath> p1 = new ArrayList<Day16RoomHistoryPath>();

			p1.add(new Day16RoomHistoryPath(start));
		p1.show();

			Day16RoomHistoryPath p2 = p1.extend(false).get(0);
			p2.show();

			Day16RoomHistoryPath p3 = p2.extend(false).get(0);
			p3.show();

			Day16RoomHistoryPath p4 = p3.extend(false).get(0);
			p4.show();
*/

/*
			for(int i = 1; i < 4; i++) {
				ArrayList<Day16RoomHistoryPath> extendedness = new ArrayList<Day16RoomHistoryPath>();
				for(Day16RoomHistoryPath p : p1) {
					//p.show();

					ArrayList<Day16RoomHistoryPath> p2 = p.extend(false);
					for(Day16RoomHistoryPath pp : p2) {
						extendedness.add(pp);

					}


				}

				p1 = extendedness;

				//p1 = p2;

			} // loop


				for(Day16RoomHistoryPath p : p1) p.show();





			Day16CompleteZone z2 = originZone.buildNewZone(1, 2000);
			z2.show();
			Day16CompleteZone z3 = z2.buildNewZone(2001, 3000);
			z3.show();
*/


			Day16CompleteZone newZone = new Day16CompleteZone(start, 0, 0);
			int interval = 1001;
			for(int i = 1; ; i++) {
				Day16CompleteZone currentZone = newZone;
				int min = 1 + (i-1) * 1001;
				int max = 1001 * i;
				newZone = currentZone.buildNewZone(min, max);
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
			ArrayList<Day16RoomHistoryPath> explorations = new Day16RoomHistoryPath(start).explore(1980);

			for(Day16RoomHistoryPath discovery : explorations) discovery.show();
*/
			//start.showPathsOfLength(3);

			/*

				Remember why we need ROOMS and not just any old corridor-cell? It's because...
				actually I'm not sure any more.

			*/
/*
			Day16RoomHistoryPath rhp = new Day16RoomHistoryPath(start);

			ArrayList<Day16RoomHistoryPath> results = rhp.extend(debug);

			for(Day16RoomHistoryPath result : results) result.display();

			rhp.show();
*/

// LDFIXME the rectangle goes on ALL clels in path, not jsut head

			// LDFIXME next up is to recurse and build longer paths
			// LDFIXME also implement showment on a grid!!


			// LDFIXME EVENTUALLY remove the old day16
	}

}
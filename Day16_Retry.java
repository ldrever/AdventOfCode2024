import java.util.*;
import java.io.*;

public class Day16_Retry{


	private static boolean doContinue(Scanner sc) {
		System.out.println("Do you want to continue? (y/n): ");
		String input = sc.next();
		return input.toLowerCase().equals("y");
	}


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
			Day16RoomState end = lg.getEnd(false);

			Day16CompleteZone newZone = new Day16CompleteZone(start, 0, 0);
			int interval = 1001;
			//for(int i = 1; ; i++) {

				int i = 0;
			//	Scanner scanner = new Scanner(System.in);
			//while(doContinue(scanner)) {

			ArrayList<Day16CompleteZone> zoneRecord = new ArrayList<Day16CompleteZone>();
			zoneRecord.add(newZone);
			int endCost = 0;
			while(true) {

				i = zoneRecord.size();
				Day16CompleteZone currentZone = zoneRecord.get(i - 1);

				int min = 1 + (i-1) * 1001;
				int max = 1001 * i;
				newZone = currentZone.buildNewZone(min, max);
				zoneRecord.add(newZone);


				ArrayList<Day16RoomHistoryPath> interZonePathways = currentZone.reachOtherZone(newZone);

				//System.out.println(interZonePathways.size() + " ways of getting from zone " + i + " to zone " + (i+1));


				System.out.print("endpoints reachable with least cost between ");
				System.out.print(min);
				System.out.print(" and ");
				System.out.print(max);
				System.out.println(" includes a state-count of " + newZone.getStateCount());

				endCost = newZone.getEndCost();

				if(endCost != -1) {


					System.out.print("State-based and hard-comparisons. Final cost: ");
					System.out.println(endCost);

					newZone.finalPrune();

					System.out.print("Post-prune verification: final zone now costs ");
					System.out.print(newZone.getEndCost());
					System.out.println(" and includes the following states: ");
					newZone.show();

					break;
				}

				//newZone.showWithBro(currentZone);



			} // i loop

			// now loop back downwards
			// we know that the final one has been pruned

			Day16RoomHistoryPath victoryPath = new Day16RoomHistoryPath();
			i = zoneRecord.size() - 2; // the VERY last one got pruned in its own special way
			while(i >= 0) {

				//System.out.print(pathsUp.size() + " original ways of getting from zone " + i + " to " + (i+1));
				zoneRecord.get(i).pruneWithRespectTo(zoneRecord.get(i + 1));
				//pathsUp = zoneRecord.get(i).reachOtherZone(zoneRecord.get(i + 1));
				//System.out.println("; " + pathsUp.size() + "after pruning.");

				ArrayList<Day16RoomHistoryPath> pathsUp = zoneRecord.get(i).reachOtherZone(zoneRecord.get(i + 1));

				for(Day16RoomHistoryPath path : pathsUp) {
					for(Day16RoomState rs : path.getHistory()) {
						victoryPath.addRoom(rs);
						lg.setCell(rs, 'O');

					}

				}

				i--;
			}

			victoryPath.show();

			System.out.println("endCost: " + endCost);
			ArrayList<Day16RoomHistoryPath> trackback = new Day16RoomHistoryPath(start).explore(endCost, 'O');
			System.out.print(trackback.size() + " paths found; ");

			Day16RoomHistoryPath victoryPath2 = new Day16RoomHistoryPath();
			HashSet<String>victoryHash = new HashSet<String>();
			int winners = 0;
			for(Day16RoomHistoryPath path : trackback) {
				if(path.getCost() == endCost && path.getHead().matches(end)) {

					winners++;
				// path.show();
					for(Day16RoomState rs : path.getHistory()) {
						victoryPath2.addRoom(rs);
						victoryHash.add(rs.toString());
						lg.setCell(rs, 'O');

					}
				}

			}

			System.out.println(winners + " are winners.");

			victoryPath2.show();

			System.out.println("finalest of answies: " + victoryHash.size());

			// LDFIXME EVENTUALLY remove the old day16
	}

}
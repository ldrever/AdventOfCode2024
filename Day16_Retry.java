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

			Day16CompleteZone newZone = new Day16CompleteZone(start, 0, 0);
			int interval = 1001;
			//for(int i = 1; ; i++) {

				int i = 0;
			//	Scanner scanner = new Scanner(System.in);
			//while(doContinue(scanner)) {

			ArrayList<Day16CompleteZone> zoneRecord = new ArrayList<Day16CompleteZone>();
			zoneRecord.add(newZone);

			while(true) {
				i++;

				Day16CompleteZone currentZone = zoneRecord.get(zoneRecord.size() - 1);
				int min = 1 + (i-1) * 1001;
				int max = 1001 * i;
				newZone = currentZone.buildNewZone(min, max);
				zoneRecord.add(newZone);


				ArrayList<Day16RoomHistoryPath> interZonePathways = currentZone.reachOtherZone(newZone);

				System.out.println(interZonePathways.size() + " ways of getting from zone " + i + " to zone " + (i+1));


				System.out.print("endpoints reachable with least cost between ");
				System.out.print(min);
				System.out.print(" and ");
				System.out.print(max);
				System.out.println(" includes a state-count of " + newZone.getStateCount());

				int endCost = newZone.getEndCost();

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





			// LDFIXME EVENTUALLY remove the old day16
	}

}
import java.util.ArrayList;
import java.util.HashSet;

public class Day16CompleteZone {
	private int min;
	private int max;
	private ArrayList<Day16RoomHistoryPath> pathsIntoZone;
	private ArrayList<Day16RoomState> ancestorZone;

	public ArrayList<Day16RoomHistoryPath> getPathsIntoZone() {return this.pathsIntoZone;}
	public int getMax() {return this.max;}
	/*

		Defined such that EVERY room that's POSSIBLY reachable
		with min <= cost <= max is represented here precisely ONCE,
		by being the head of one of these paths - none of which is
		beatable by a cheaper path that ends in its head room.
		(Matchable yes, beatable no.)

	*/

	public Day16CompleteZone(Day16RoomState origin, int min, int max) {
		this.min = min;
		this.max = max;
		this.pathsIntoZone = new ArrayList<Day16RoomHistoryPath>();
		pathsIntoZone.add(new Day16RoomHistoryPath(origin));
		this.ancestorZone = new ArrayList<Day16RoomState>(); // empty at this point

	} // origin constructor

	private Day16CompleteZone(int newMin, int newMax, ArrayList<Day16RoomHistoryPath> newPaths, ArrayList<Day16RoomState> forbiddenZone) {
		this.min = newMin;
		this.max = newMax;
		this.pathsIntoZone = newPaths;
		this.ancestorZone = forbiddenZone;

	} // private constructor

	public void show() {

		// slightly dirty, because it's not a path really, but a collection of end-points
		Day16RoomHistoryPath showZone = new Day16RoomHistoryPath();
		for(Day16RoomHistoryPath validPath : this.pathsIntoZone) {
			showZone.addRoomWithoutCostUpdate(validPath.getHead());

		} // valid loop
		showZone.show();

	} // show



	public Day16CompleteZone buildNewZone(int min, int max) {
		ArrayList<Day16RoomHistoryPath> output = new ArrayList<Day16RoomHistoryPath>();

		// starting from the paths into this zone, extend them onwards
		// (the explore function implements the max filter)
		for(Day16RoomHistoryPath currentPath : this.pathsIntoZone) {
			for(Day16RoomHistoryPath exploration : currentPath.explore(max)) {

				// min filter
				if(exploration.getCost() >= min) output.add(exploration);

			} // loop over this path's explorations
		} // loop over paths in the zone

		// filter so that we can't dip into the current zone or the ancestor zone - any path that
		// did so would be outcompeted by a path that simply came FROM the site of the dip
		ArrayList<Day16RoomState> forbiddenZone = new ArrayList<Day16RoomState>();
		for(Day16RoomState ancestorState : ancestorZone) forbiddenZone.add(ancestorState);
		for(Day16RoomHistoryPath currentPath : this.pathsIntoZone) forbiddenZone.add(currentPath.getHead());

		for(Day16RoomState forbiddenState : forbiddenZone) {
			for(int outputIndex = output.size() - 1; outputIndex >= 0; outputIndex--) {
				if(forbiddenState.matches(output.get(outputIndex).getHead())) {
					output.remove(outputIndex);
				} // match check
			} // output checking loop
		} // forbidden states loop

		// starting at zero, we'll gradually make every member of output be the cheapest possible
		for (int i = 0; i < output.size(); i++) {
			// it'll always be the j one that gets removed, so do these working downwards
			for(int j = output.size() - 1; j > i; j--) {

				// after every comparison with a match, the cheaper survivor will be i, not j
				if(output.get(i).getHead().matches(output.get(j).getHead())) {
					if(output.get(i).getCost() > output.get(j).getCost()) {
						output.set(i, output.get(j));
					} // j-cheaper condition
					output.remove(j); // LDFIXME NOTE THAT THIS KILLS OFF EQUAL-COST PATHS
				} // matching costs condition
			} // j loop
		} // i loop
		return new Day16CompleteZone(min, max, output, forbiddenZone);

	} // buildNewZone



	public int getEndCost() {
		for(Day16RoomHistoryPath path : this.pathsIntoZone) {

			if(path.getHead().isEnd()) {
				path.show();
				return path.getCost();
			} // end-check

		} // path loop
		return -1;
	} // getEndCost

	public void restrictToWinners() {
		for (int i = this.pathsIntoZone.size() - 1; i >= 0; i--) {
			if(this.pathsIntoZone.get(i).getHead().isEnd()) {
				//System.out.println("Preserving this winning path:");
				//pathsIntoZone.get(i).show();
			} else {
				//System.out.println("Killing this losing path:");
				//pathsIntoZone.get(i).show();
				this.pathsIntoZone.remove(i);
			}
		} // path loop
	} // restrictToWinners

	public ArrayList<Day16RoomState> restrictToHigherZoneReachers(Day16CompleteZone higherZone) {
		ArrayList<Day16RoomState> output = new ArrayList<Day16RoomState>(); // things going BEYOND this zone

		HashSet<Day16RoomHistoryPath> validHash = new HashSet<Day16RoomHistoryPath>();
		ArrayList<Day16RoomState> demandedZone = new ArrayList<Day16RoomState>();
		for(Day16RoomHistoryPath pathIntoHigherZone : higherZone.getPathsIntoZone()) {
			demandedZone.add(pathIntoHigherZone.getHead());
		}

		// starting from the paths into this zone, extend them onwards
		// (the explore function implements the max filter)
		for(Day16RoomHistoryPath currentPath : this.pathsIntoZone) {
			for(Day16RoomHistoryPath exploration : currentPath.explore(higherZone.getMax())) {
				for(Day16RoomState demandedSpot : demandedZone) {
					if(exploration.getHead().matches(demandedSpot)) {

						// firstly, means we can keep it:
						validHash.add(currentPath);

						// secondly, means its cells are part of the final answer
						for(Day16RoomState rs : exploration.getHistory()) {
							output.add(rs);
						}

						break;



					} // match check
				} // demanded loop
			} // loop over this path's explorations
		} // loop over paths in this zone


		for(int i = this.pathsIntoZone.size() - 1; i >= 0; i--) {
			if(validHash.contains(this.pathsIntoZone.get(i))) {

				// keep it
			} else {
				this.pathsIntoZone.remove(i);
			}

		}
		return output;
	} // restrictToHigherZoneReachers

} // class
import java.util.ArrayList;

public class Day16CompleteZone {
	private int min;
	private int max;
	private ArrayList<Day16RoomHistoryPath> pathsIntoZone;
	private ArrayList<Day16RoomState> zoneStates;
	private ArrayList<Day16RoomState> ancestorZone;
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

		this.zoneStates = new ArrayList<Day16RoomState>();
		this.zoneStates.add(origin);

	} // origin constructor

	private Day16CompleteZone(int newMin, int newMax, ArrayList<Day16RoomHistoryPath> newPaths, ArrayList<Day16RoomState> forbiddenZone) {
		this.min = newMin;
		this.max = newMax;
		this.pathsIntoZone = newPaths;
		this.ancestorZone = forbiddenZone;

		this.zoneStates = new ArrayList<Day16RoomState>();
		for(Day16RoomHistoryPath path : newPaths) this.zoneStates.add(path.getHead());


	} // private constructor

	public void show() {

		// slightly dirty, because it's not a path really, but a collection of end-points
		Day16RoomHistoryPath showZone = new Day16RoomHistoryPath();
		for(Day16RoomState rs : this.zoneStates) showZone.addRoom(rs);
		/*
		for(Day16RoomHistoryPath validPath : this.pathsIntoZone) {
			showZone.addRoom(validPath.getHead());

		} // valid loop
		*/
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
				if(forbiddenState.matchesPositionAndDirection(output.get(outputIndex).getHead())) {
					output.remove(outputIndex);
				} // match check
			} // output checking loop
		} // forbidden states loop

		// starting at zero, we'll gradually make every member of output be the cheapest possible
		for (int i = 0; i < output.size(); i++) {
			// it'll always be the j one that gets removed, so do these working downwards
			for(int j = output.size() - 1; j > i; j--) {

				// after every comparison with a match, the cheaper survivor will be i, not j
				if(output.get(i).getHead().matchesPositionAndDirection(output.get(j).getHead())) {
					if(output.get(i).getCost() > output.get(j).getCost()) {
						output.set(i, output.get(j));
					} // j-cheaper condition
					output.remove(j); // LDFIXME NOTE THAT THIS KILLS OFF EQUAL-COST PATHS
				} // matching costs condition
			} // j loop
		} // i loop
		return new Day16CompleteZone(min, max, output, forbiddenZone);

	} // buildNewZone

	public int getStateCount() {
		return this.zoneStates.size();
	}

	public int getEndCost() {
		for(Day16RoomHistoryPath path : this.pathsIntoZone) {

			if(path.getHead().isEnd()) {
				path.show();
				return path.getCost();
			} // end-check

		} // path loop
		return -1;
	} // getEndCost

} // class
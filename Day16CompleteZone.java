import java.util.ArrayList;

public class Day16CompleteZone {
	private ArrayList<Day16RoomState> zoneStates;
	private int min; //LDFIXME minCost/maxCost might be better,long-term
	private int max;
	private ArrayList<Day16RoomHistoryPath> pathsIntoZone;
	private ArrayList<Day16RoomState> ancestorZone;
	/*

		Defined as every possible Day16RoomState whose MINIMUM cost of
		reaching lies within the prescribed limits.

		We also keep track of every possible path that:
		-	starts in the previous CompleteZone
		-	ends in the current CompleteZone
		-	achieves the minimum cost when arriving at its head

	*/

	public Day16CompleteZone(Day16RoomState origin, int min, int max) {
		this.min = min;
		this.max = max;
		this.pathsIntoZone = new ArrayList<Day16RoomHistoryPath>(); // empty at this point
		//pathsIntoZone.add(new Day16RoomHistoryPath(origin));
		this.ancestorZone = new ArrayList<Day16RoomState>(); // empty at this point
		this.zoneStates = new ArrayList<Day16RoomState>();
		this.zoneStates.add(origin);

	} // origin constructor

	// LDFIXME reorder args; maybe even rename all these Day16 objects...
	private Day16CompleteZone(int min, int max, ArrayList<Day16RoomHistoryPath> pathsIntoZone, ArrayList<Day16RoomState> ancestorZone, ArrayList<Day16RoomState> zoneStates) {
		this.min = min;
		this.max = max;
		this.pathsIntoZone = pathsIntoZone;
		this.ancestorZone = ancestorZone;
		this.zoneStates = zoneStates;

	} // private constructor

	public void show() {

		// slightly dirty, because it's not a path really, but a collection of end-points
		Day16RoomHistoryPath showZone = new Day16RoomHistoryPath();
		for(Day16RoomState rs : this.zoneStates) {
			showZone.addRoomWithoutCostUpdate(rs);

		} // valid loop
		showZone.show();

	} // show



	public Day16CompleteZone buildNewZone(int min, int max) {
		ArrayList<Day16RoomHistoryPath> outwardPaths = new ArrayList<Day16RoomHistoryPath>();

		// find paths with TAILS IN THIS ZONE
		// (the explore function implements the max filter)
		for(Day16RoomState tail : this.zoneStates) {
			for(Day16RoomHistoryPath exploration : tail.explore(max)) {

				// min filter // LDFIXME not sure if this is worth doing, if we say that min will always
				// be one more than prev max
				/*if(exploration.getCost() >= min) */ outwardPaths.add(exploration);

			} // loop over this path's explorations
		} // loop over paths in the zone

		// filter so that we can't dip into the current zone or the ancestor zone - any path that
		// did so would be outcompeted by a path that simply came FROM the site of the dip
		ArrayList<Day16RoomState> forbiddenZone = new ArrayList<Day16RoomState>();
		for(Day16RoomState ancestorState : ancestorZone) forbiddenZone.add(ancestorState);
		for(Day16RoomState tail : this.zoneStates) forbiddenZone.add(tail);
	//	for(Day16RoomHistoryPath currentPath : this.pathsIntoZone) forbiddenZone.add(currentPath.getHead());

		// for every forbidden state...
		for(Day16RoomState forbiddenState : forbiddenZone) {
			// ... consider every still-existing path...
			for(int outputIndex = outwardPaths.size() - 1; outputIndex >= 0; outputIndex--) {
				Day16RoomHistoryPath outputMember = outwardPaths.get(outputIndex);

				// ... looking at every member thereof, EXCEPT THE TAIL, hence 1 and not 0 here
				ArrayList<Day16RoomState> pathSegments = outputMember.getHistory();
				for(int pathSegmentIndex = 1; pathSegmentIndex < pathSegments.size(); pathSegmentIndex++) {
					Day16RoomState outputMemberSegment = pathSegments.get(pathSegmentIndex);

					// and check if it's in the forbidden zone...
					//if(forbiddenState.matchesPositionAndDirection(outputMemberSegment)) {
					if(forbiddenState.matches(outputMemberSegment)) { // not quite sure why we don't need direction too // LDFIXME
						// ... in which case jump directly to the next path
						outwardPaths.remove(outputIndex);
						break;
					} // match check
				} // every segment of that path loop
			} // outwardPaths checking loop
		} // forbidden states loop

		// starting at zero, we'll gradually make every member of outwardPaths be the cheapest possible
		for (int i = 0; i < outwardPaths.size(); i++) {
			// it'll always be the j one that gets removed, so do these working downwards
			for(int j = outwardPaths.size() - 1; j > i; j--) {

				// after every comparison with a match, the cheaper survivor will be i, not j
				if(outwardPaths.get(i).getHead().matches(outwardPaths.get(j).getHead())) {
					if(outwardPaths.get(i).getCost() > outwardPaths.get(j).getCost()) {
						outwardPaths.set(i, outwardPaths.get(j));
						outwardPaths.remove(j);
					} // j-cheaper condition
				} // matching costs condition
			} // j loop
		} // i loop
		ArrayList<Day16RoomState> outputHeads = new ArrayList<Day16RoomState>();
		for(Day16RoomHistoryPath path : outwardPaths) outputHeads.add(path.getHead());
		return new Day16CompleteZone(min, max, outwardPaths, forbiddenZone, outputHeads);

	} // buildNewZone



	public int getEndCost() {
		int returnable = -1;
		for(Day16RoomState rs : this.zoneStates) {
			if(rs.isEnd()) {
				returnable = rs.getCostSoFar();
				System.out.print(returnable);
				System.out.println(" found as a winner.");
				//return rs.getCostSoFar();
			}
		}
		/*
		for(Day16RoomHistoryPath path : this.pathsIntoZone) {

			if(path.getHead().isEnd()) {
				path.show();
				return path.getCost();
			} // end-check

		} // path loop
		*/
		return returnable;
	} // getEndCost

} // class
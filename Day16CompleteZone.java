import java.util.ArrayList;

public class Day16CompleteZone {
	/*

		// LDFIXME inclusive exclusive

		Defined such that EVERY room that's POSSIBLY reachable
		with min <= cost <= max is represented here precisely ONCE,
		by being the head of one of these paths - none of which is
		beatable by a cheaper path that ends in its head room.
		(Matchable yes, beatable no.)

	*/

	// LDFIXME inclusive exclusive
	private int min; // inclusive
	private int max; // inclusive
	private ArrayList<Day16RoomHistoryPath> validPaths;
	private ArrayList<Day16RoomState> forbiddenZone;

	public Day16CompleteZone(Day16RoomState origin, int min, int max) {
		this.min = min;
		this.max = max;
		this.validPaths = new ArrayList<Day16RoomHistoryPath>();
		validPaths.add(new Day16RoomHistoryPath(origin));
		this.forbiddenZone = new ArrayList<Day16RoomState>(); // empty at this point

	} // origin constructor

	private Day16CompleteZone(int newMin, int newMax, ArrayList<Day16RoomHistoryPath> newPaths, ArrayList<Day16RoomState> newForbiddenZone) {
		this.min = newMin;
		this.max = newMax;
		this.validPaths = newPaths;
		this.forbiddenZone = newForbiddenZone;

	} // private constructor

	public void show() {

		// slightly dirty, because it's not a path really, but a collection of end-points
		Day16RoomHistoryPath showZone = new Day16RoomHistoryPath();
		for(Day16RoomHistoryPath validPath : this.validPaths) {
			showZone.addRoomWithoutCostUpdate(validPath.getHead());

		} // valid loop
		showZone.show();

	} // show

	public Day16CompleteZone buildNewZone(int min, int max) {
		ArrayList<Day16RoomHistoryPath> output = new ArrayList<Day16RoomHistoryPath>();

		for(Day16RoomHistoryPath currentPath : this.validPaths) {

			// starting from the paths inside this zone, extend them onwards
			// explore function implements the max filter
			for(Day16RoomHistoryPath exploration : currentPath.explore(max)) {

				// min filter //LDFIXME INCLUSIVE EXCLUSIVE
				if(exploration.getCost() >= min) {
					output.add(exploration);

				}

			} // loop over explorations

		} // loop over current zone

		// filter so that we can't go back into the current zone we're using to build the new zone
		// not only can the HEAD not be in the current zone - no new path-piece can. Because if we
		// had a valid path for the next zone that needed to take even ONE further step inside THIS
		// zone, it would be beatable by a path starting FROM that step

		// in fact, we'll also need to check the previous zone...
		ArrayList<Day16RoomState> newForbiddenZone = new ArrayList<Day16RoomState>();
		for(Day16RoomState parent : forbiddenZone) newForbiddenZone.add(parent);
		for(Day16RoomHistoryPath currentPath : this.validPaths) newForbiddenZone.add(currentPath.getHead());


		for(Day16RoomState forbiddenRoom : newForbiddenZone) {

			for(int outputIndex = output.size() - 1; outputIndex >= 0; outputIndex--) {

				if(forbiddenRoom.matches(output.get(outputIndex).getHead())) {

					output.remove(outputIndex);


				}


			} // down-counting integer loop because it can remove what it's looking at

		} // current zone loop

		// LDFIXME ok need to prune for cheapestness here. also, above not yet checked wrt basic working or not...

		for (int i = 0; i < output.size(); i++) {
			// starting at zero, we'll gradually make every member of output be the cheapest possible

			for(int j = output.size() - 1; j > i; j--) {
				// it'll always be the j one that gets removed, so do these working downwards

				// so let's say that after every comparison with a match, the cheaper survivor will be
				// assigned to the i version
				if(output.get(i).getHead().matches(output.get(j).getHead())) {
					if(output.get(i).getCost() > output.get(j).getCost()) {
						output.set(i, output.get(j));

					} // j-cheaper condition
					output.remove(j);
				} // matching costs condition
			} // j loop
		} // i loop
		ArrayList<Day16RoomState> newParents = new ArrayList<Day16RoomState>();
		for(Day16RoomHistoryPath path : this.validPaths) newParents.add(path.getHead());
		for(Day16RoomState oldForbiddenRoom : this.forbiddenZone) newParents.add(oldForbiddenRoom);
		return new Day16CompleteZone(min, max, output, newParents);


	}

} // class
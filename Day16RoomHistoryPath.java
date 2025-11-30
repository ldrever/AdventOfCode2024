import java.util.ArrayList;

public class Day16RoomHistoryPath {
	/*

		Captures a sequence of room-inhabitations through the maze,
		and the cost of each.

		Each room is captured along with the direction by which it
		was ENTERED.

		And the cost of the entire path is tracked.

		Note that no room ever appears twice in one of these.

		If we enter some room from one direction, but leave it in a
		different direction, then the fact that we have turned is
		captured by the fact that the second room was entered from
		that different direction.

	*/

	private ArrayList<Day16RoomState> history;
	private int cost;

	public int getCost() {return this.getHead().getCostSoFar();} // LDFIXME "so far" a useful part of the name?

	public Day16RoomHistoryPath(Day16RoomState start) {
		this.history = new ArrayList<Day16RoomState>();
		this.history.add(start);
		this.cost = 0;
	} // known-first-room constructor

	public Day16RoomHistoryPath() {
		this.history = new ArrayList<Day16RoomState>();
		this.cost = 0;
	} // empty-path constructor

	// we'll say that the tail is at the start
	public Day16RoomState getHead() {
		return this.history.get(this.history.size() - 1);
	}


/*
	private void setCost(int newCost){this.cost = newCost;}
*/
	// should really be private, but used to form an invalid path purely
	// for showing the frontier
	public void addRoomWithoutCostUpdate(Day16RoomState newHead) { // LDFIXME probably fine now that costs outsourced ie rename
		this.history.add(newHead);
	} // addRoomWithoutCostUpdate



	public Day16RoomHistoryPath clone() {
		Day16RoomHistoryPath output = new Day16RoomHistoryPath();
		// output.setCost(this.getCost());

		for(Day16RoomState myHistoryItem : this.history) {
			output.addRoomWithoutCostUpdate(myHistoryItem);
		} // add-roomstate loop

		return output;
	} // clone

	public void display() {
		for(Day16RoomState historyMember : this.history) historyMember.display();
		System.out.println(); // because the above stays on a single line

	} // display
	// LDFIXME rename ST graphical and textal display are more obvious

	public ArrayList<Day16RoomHistoryPath> extend(boolean debug) {
		if(debug) {
			System.out.print("Seeking extensions of path ");
			this.display();

		} // debug

		ArrayList<Day16RoomHistoryPath> output = new ArrayList<Day16RoomHistoryPath>(); // this might be empty
		Day16RoomState head = this.getHead();
		ArrayList<Day16RoomState> headNeighbours = head.getNeighbours(debug);

		for (Day16RoomState candidateNeighbour : headNeighbours) {
			boolean plausibleCandidate = true;
			/*
				We've already ruled out neighbour-cells that are walls as opposed
				to open space. We now need to rule out any that are already part
				of the path's history. E.g. when there's a loop, it's not valid to
				go all the way round it, then start retracing the steps we took to
				enter it.

				We check the history starting with the head not the tail, because
				after every	call to getNeighbours(), we know that one of the
				results is just	going to be the cell prior to the head. So we may
				as well use the history-checking logic to rule that one out. And
				we do size() - 2 instead of size() - 1, because we trust that
				the head's neighbour will never be the head itself.
			*/
			for(int check = this.history.size() - 2; check >= 0; check--) {
				if (this.history.get(check).matches(candidateNeighbour)) {
					if(debug) {
						System.out.print("Neighbour at ");
						candidateNeighbour.display();
						System.out.print(" not considered (already in this path's history)");
						} // debug
					plausibleCandidate = false;
					break;
				} // if
			} // for

			if(plausibleCandidate) {
				if(debug) {
					System.out.print("Neighbour at ");
					candidateNeighbour.display();
					System.out.println(" is valid");
				} // debug
				Day16RoomHistoryPath newPath = this.clone();
				newPath.addRoomWithoutCostUpdate(candidateNeighbour);
				/* // LDFIXME outsourced to room-level
				int additionalCost = 1; // for a single step
				if(candidateNeighbour.getDxEntry() == head.getDxEntry() && candidateNeighbour.getDyEntry() == head.getDyEntry()) {


				} else {
					additionalCost += 1000; // for a turn
				}


				newPath.setCost(additionalCost + this.getCost());
				*/
				output.add(newPath);

			} // plausibleCandidate

		} // candidateNeighbour loop

		return output;
	} // extend

	public void show() {
		Day16LetterGrid newGrid = this.getHead().getParentGrid().duplicate();

		for(Day16RoomState roomState : this.history) {
			newGrid.setCell(roomState.getXCoord(), roomState.getYCoord(),'█');

		} // room loop


		newGrid.display();

		System.out.print("Cost: ");
		System.out.println(this.getCost());

	} // show



	public ArrayList<Day16RoomHistoryPath> explore(int max) {
		/*

			Starting from one given path, find its children, and their children,
			and so on recursively until we guarantee that EVERY such descendant
			path - as long as its cost lies in the bounds provided - is included.

			It is possible that the output could include multiple different paths
			that end at the same cell.

			It doesn't include the originating path.

		*/


		ArrayList<Day16RoomHistoryPath> output = new ArrayList<Day16RoomHistoryPath>();

		// generation zero will be our input path
		ArrayList<Day16RoomHistoryPath> nextGeneration = new ArrayList<Day16RoomHistoryPath>();
		nextGeneration.add(this);
		while(nextGeneration.size() > 0)
		{
			ArrayList<Day16RoomHistoryPath> currentGeneration = nextGeneration;
			nextGeneration = new ArrayList<Day16RoomHistoryPath>();

			for(Day16RoomHistoryPath path : currentGeneration) {
				ArrayList<Day16RoomHistoryPath> currentChildren = path.extend(false);

				for(Day16RoomHistoryPath child : currentChildren) {
					if(child.getCost() <= max) {
						nextGeneration.add(child);
						output.add(child);
					}

				} // child for-each


			} // for-each



		} // while

		return output;

	} //explore
} // class
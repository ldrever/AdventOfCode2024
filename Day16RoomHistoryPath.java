import java.util.ArrayList;

public class Day16RoomHistoryPath {

	private ArrayList<Day16RoomState> history;
	private int cost;

	public int getCost() {return this.cost;}

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



	// private methods that must be used together
	private void setCost(int newCost){this.cost = newCost;}

	private void addRoomWithoutCostUpdate(Day16RoomState newHead) {
		this.history.add(newHead);
	} // addRoomWithoutCostUpdate



	public Day16RoomHistoryPath clone() {
		Day16RoomHistoryPath output = new Day16RoomHistoryPath();
		output.setCost(this.getCost());

		for(Day16RoomState myHistoryItem : this.history) {
			output.addRoomWithoutCostUpdate(myHistoryItem);
		} // add-roomstate loop

		return output;
	} // clone

	public void display() {
		for(Day16RoomState historyMember : this.history) historyMember.display();
		System.out.println(); // because the above stays on a single line

	} // display

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
				newPath.setCost(0); // LDFIXME
				output.add(newPath);

			} // plausibleCandidate

		} // candidateNeighbour loop

		return output;
	} // extend

} // class
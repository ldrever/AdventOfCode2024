import java.util.ArrayList;
public class Day16RoomState {

	private int xCoord;
	private int yCoord;
	private int dxEntry;
	private int dyEntry;
	private int costSoFar;
	private Day16LetterGrid parentGrid;

	/*

		Represent the state of having just entered a particular room
		from a particular direction. Also keep track of the cost we
		have incurred so far, by entering this state.

	*/

	public int getXCoord(){return this.xCoord;}
	public int getYCoord(){return this.yCoord;}
	public int getDxEntry(){return this.dxEntry;}
	public int getDyEntry(){return this.dyEntry;}
	public int getCostSoFar(){return this.costSoFar;}

	public String getCompassDirection() {
		if(this.dxEntry == 1) return "East";
		if(this.dxEntry == -1) return "West";
		if(this.dyEntry == 1) return "North";
		if(this.dyEntry == -1) return "South";
		return "";

	}

	public void display() {
		System.out.print("(");
		System.out.print(this.getXCoord());
		System.out.print(",");
		System.out.print(this.getYCoord());
		System.out.print(") ");
		System.out.print(this.getCompassDirection());
		System.out.print("; ");

	}
/*
	public Day16RoomState(Day16LetterGrid parentGrid, int xCoord, int yCoord, int dxEntry, int dyEntry) {
		this.parentGrid = parentGrid;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.dxEntry = dxEntry;
		this.dyEntry = dyEntry;
	}
*/
	public Day16RoomState(Day16LetterGrid parentGrid, int xCoord, int yCoord, int dxEntry, int dyEntry, int costSoFar) {
		this.parentGrid = parentGrid;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.dxEntry = dxEntry;
		this.dyEntry = dyEntry;
		this.costSoFar = costSoFar;
	}

	public Day16LetterGrid getParentGrid() {
		return this.parentGrid;
	}

	public ArrayList<Day16RoomState> getNeighbours(boolean debug) {
		ArrayList<Day16RoomState> output = new ArrayList<Day16RoomState>();

		for(int extendmentDx = -1; extendmentDx <= 1; extendmentDx++) {
			for(int extendmentDy = -1; extendmentDy <= 1; extendmentDy++) {

				// precisely one must be zero
				if(extendmentDx == 0 && extendmentDy != 0 || extendmentDx != 0 && extendmentDy == 0) {

					char cell = this.getParentGrid().getCell(this.getXCoord() + extendmentDx, this.getYCoord() + extendmentDy);
					Day16RoomState newRS;

					int additionalCost = 1; // for a single step
					if(extendmentDx == this.getDxEntry() && extendmentDy == this.getDyEntry()) {


					} else {
						additionalCost += 1000; // for a turn
					}



					// check it's not a wall
					if(cell != '#') {
						newRS = new Day16RoomState(
							  this.getParentGrid()
							, this.getXCoord() + extendmentDx
							, this.getYCoord() + extendmentDy
							, extendmentDx
							, extendmentDy
							, this.getCostSoFar() + additionalCost
							);

						if(debug) {
							System.out.print("Neighbour at ");
							newRS.display();
							System.out.println(" is not a wall.");

						} // debug



						output.add(newRS);
					} // wall check

				} // single step check

			} // dy

		} // dx

		return output;
	} // getNeighbours

	public boolean matches(Day16RoomState newRS) {
		return (newRS.getXCoord() == this.getXCoord() && newRS.getYCoord() == this.getYCoord());
	} // matches

	public boolean matchesPositionAndDirection(Day16RoomState newRS) {
		return this.matches(newRS) && this.getDxEntry() == newRS.getDxEntry() && this.getDyEntry() == newRS.getDyEntry();
	}


	// LDFIXME why can't this handle an input of 3, but 2 is fine?
	public void showPathsOfLength(int length) {
		if(length < 1) {
			System.out.println("Path length to show must be at least 1.");
			return;
		}

		ArrayList<Day16RoomHistoryPath> oldPaths = new ArrayList<Day16RoomHistoryPath>();
		ArrayList<Day16RoomHistoryPath> newPaths = new ArrayList<Day16RoomHistoryPath>();

		oldPaths.add(new Day16RoomHistoryPath(this));
		int lengthFullyCaptured = 1;

		while (lengthFullyCaptured < length) {

			for(Day16RoomHistoryPath path : oldPaths) {
				path.show();
			} // path for-each

			newPaths.clear();

			for(Day16RoomHistoryPath oldPath : oldPaths) {
				ArrayList<Day16RoomHistoryPath> childPaths = oldPath.extend(true); // LDFIXME HARD VALUED BOOLEAN DEBUG
				oldPath.display();
				System.out.print(" has ");
				System.out.print(childPaths.size());
				System.out.println(" child paths.");
				for(Day16RoomHistoryPath childPath : childPaths) {
					newPaths.add(childPath);

				} // child path loop
			} // old path loop
			System.out.print(lengthFullyCaptured);
			System.out.print(" has been fully captured. ");
			System.out.print(oldPaths.size());
			System.out.print(" oldPaths and ");
			System.out.print(newPaths.size());
			System.out.println(" newPaths.");



			lengthFullyCaptured++;

			oldPaths = newPaths;

			System.out.print(oldPaths.size());
			System.out.print(" oldPaths and ");
			System.out.print(newPaths.size());
			System.out.println(" newPaths.");


		} // while

		// at this point we can trust that the "old" array has everything expected, so just show it
		// LDFIXE separate out showment & return
/*
		for(Day16RoomHistoryPath path : oldPaths) {
			path.show();
		} // path for-each
*/
	} // showPathsOfLength




	public ArrayList<Day16RoomHistoryPath> explore(int max) {
		/*

			Starting from a roomstate, find PATHS for its children, and their children,
			and so on recursively until we guarantee that EVERY such descendant-reaching
			path - as long as its cost lies in the bounds provided - is included.

			It is possible that the output could include multiple different paths
			that end at the same cell.

			It doesn't include the originating path.

		*/


		ArrayList<Day16RoomHistoryPath> output = new ArrayList<Day16RoomHistoryPath>();

		// generation zero will be our input room
		ArrayList<Day16RoomHistoryPath> nextGeneration = new ArrayList<Day16RoomHistoryPath>();
		nextGeneration.add(new Day16RoomHistoryPath(this));
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



	public boolean isEnd() {
		return (this.parentGrid.getCell(xCoord, yCoord) == 'E');

	} // isEnd

}
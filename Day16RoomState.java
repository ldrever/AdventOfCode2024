import java.util.ArrayList;
public class Day16RoomState {

	/*

		Represent the state of having just entered a particular room
		from a particular direction.

	*/

	private Day16LetterGrid parentGrid;

	private int xCoord;
	private int yCoord;
	private int dxEntry;
	private int dyEntry;

	public int getXCoord(){return this.xCoord;}
	public int getYCoord(){return this.yCoord;}
	public int getDxEntry(){return this.dxEntry;}
	public int getDyEntry(){return this.dyEntry;}

	public String getCompassDirection() {
		if(this.dxEntry == 1) return "East";
		if(this.dxEntry == -1) return "West";
		if(this.dyEntry == 1) return "North";
		if(this.dyEntry == -1) return "South";
		return "";

	}

	public String toString() {
		return "(" + this.getXCoord() + ", " + this.getYCoord() + ")";

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

	public Day16RoomState(Day16LetterGrid parentGrid, int xCoord, int yCoord, int dxEntry, int dyEntry) {
		this.parentGrid = parentGrid;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.dxEntry = dxEntry;
		this.dyEntry = dyEntry;
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

					// check it's not a wall
					if(cell != '#') {
						newRS = new Day16RoomState(this.getParentGrid(), this.getXCoord() + extendmentDx, this.getYCoord() + extendmentDy, extendmentDx, extendmentDy);

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

	public boolean isEnd() {
		return (this.parentGrid.getCell(xCoord, yCoord) == 'E');

	} // isEnd

}
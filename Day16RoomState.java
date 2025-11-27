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

	public void display() {
		System.out.print("I have just entered room (");
		System.out.print(this.getXCoord());
		System.out.print(",");
		System.out.print(this.getYCoord());
		System.out.print(") facing ");
		System.out.println(this.getCompassDirection());

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

	public void getNeighbours() {

		for(int extendmentDx = -1; extendmentDx <= 1; extendmentDx++) {

			for(int extendmentDy = -1; extendmentDy <= 1; extendmentDy++) {

				// precisely one must be zero

				if(extendmentDx == 0 && extendmentDy != 0 || extendmentDx != 0 && extendmentDy == 0) {

					// now need to check for valid square

					char cell = this.getParentGrid().getCell(this.getXCoord() + extendmentDx, this.getYCoord() + extendmentDy);
					Day16RoomState newRS;

					if(cell != '#') {
						newRS = new Day16RoomState(this.getParentGrid(), this.getXCoord() + extendmentDx, this.getYCoord() + extendmentDy, extendmentDx, extendmentDy);
						newRS.display();
					}

					// now need to check for history


				}


			}

		}




	}

}
import java.util.ArrayList;

public class Day16RoomHistoryPath {

	private ArrayList<Day16RoomState> history;
	private int cost;

	public Day16RoomHistoryPath(Day16RoomState start) {
		this.history = new ArrayList<Day16RoomState>();
		this.history.add(start);
		this.cost = 0;
	}

	// we'll say that the tail is at the start
	public Day16RoomState getHead() {
		return this.history.get(this.history.size() - 1);
	}

	public ArrayList<Day16RoomHistoryPath> extend() {
		Day16RoomState head = this.getHead();
		/*
		int dxEntry = head.getDxEntry();
		int dyEntry = head.getDyEntry();
		*/

		// rather than carefully avoid going back the way we just came, let's just find all neighbours
		// and use the no-history-intersection check to achieve this

		ArrayList<Day16RoomHistoryPath> output = new ArrayList<Day16RoomHistoryPath>();

		for(int extendmentDx = -1; extendmentDx <= 1; extendmentDx++) {

			for(int extendmentDy = -1; extendmentDy <= 1; extendmentDy++) {

				// precisely one must be zero

				if(extendmentDx == 0 && extendmentDy != 0 || extendmentDx != 0 && extendmentDy == 0) {

					// now need to check for valid square



					// now need to check for history


				}


			}

		}



	} // extend


/*
	public Day16RoomHistoryPath

	public ArrayList<Day16RoomHistoryPath> getContinuations() {



	}

*/
}
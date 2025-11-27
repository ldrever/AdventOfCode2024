import java.util.*;
import java.io.*;

public class Day16_Retry{

	public static void main(String[] args) {
			boolean debug = false;

			String filePath = "Y:\\code\\github_mastered\\cloned_2025-11-27\\AdventOfCode2024\\Day16small.dat";
			Day16LetterGrid lg = null;
			try {
				lg = new Day16LetterGrid(filePath);
			}
			catch (Exception e) {System.out.println("file processing error");}

			lg.displayArray();

			Day16RoomState start = lg.findStartRoomState(debug);
			start.getNeighbours();


	}

}
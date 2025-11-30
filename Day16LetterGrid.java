import java.util.Scanner;
import java.nio.file.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Day16LetterGrid {

	private String filePath;
	private int width, height;
	private char[][] grid;

	public String getFilePath() {
		return this.filePath;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public char[][] getGrid() {
		return this.grid;
	}

	public char getCell(int X, int Y) {
		return this.grid[X][Y];
	}

	public void setCell(int X, int Y, char c) {
		this.grid[X][Y] = c;
	}

	public Day16LetterGrid duplicate() {
		// presumably there was no exception if this got set up
		try {
			return new Day16LetterGrid(this.getFilePath());
		} catch (Exception e) {return null;}
	} // clone

	public Day16LetterGrid(String filePath) throws IOException {
		this.filePath = filePath;
		ArrayList<char[]> inputLines = new ArrayList<char[]>();
		Scanner diskScanner = new Scanner(new File(this.filePath));

		int width = 0;
		int height = 0;

		while (diskScanner.hasNext()) {
			String text = diskScanner.nextLine();
			inputLines.add(text.toCharArray());
			height++;
			if (width < text.length()) width = text.length();
		}
		diskScanner.close();

		this.grid = new char[width][height];

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				try {
					this.grid[x][y] = inputLines.get(height - y - 1)[x];

				} catch(Exception e) {
					// presuming it's just a file where not every line has an equal character count, we ignore it
				}
			}
		}

		this.height = height;
		this.width = width;

	} // constructor



	public Day16RoomState findStartRoomState(boolean debug) {
		for(int X = 0; X < this.width; X++) {
			for(int Y = 0; Y < this.height; Y++) {
				char cellChar = this.getCell(X,Y);
				if(cellChar == 'S') {
					if (debug) System.out.println("New map. deer starts at (" + X + ", " + Y + ")");
					return new Day16RoomState(this, X, Y, 1, 0, 0); // start facing east; cost is zero
				}
			}
		}
		return null;
	} // findStartRoomState


/*

	public void findEnd(boolean debug) {

		for(int Y = 0; Y < this.height; Y++) {
			for(int X = 0; X < this.width; X++) {

				char cellChar = this.getCell(Y,X);
				if(cellChar == 'E') {
					this.endCol = X;
					this.endY = Y;
					if (debug) System.out.println("New map. deer ends at (" + Y + ", " + X + ")");
					return;
				}
			}
		}
	} // findEnd method




	public String paddedCoords(int y, int x) {
		// motivation here is that we sort coordinates as strings, hence
		// don't want (10,2) appearing before (2,2) etc.

		int max = Math.max(this.height, this.width);
		String longEnough = "" + max;
		int digits = longEnough.length();

		String strFormat = "%0" + digits + "d";
		String strY = String.format(strFormat, y);
		String strX = String.format(strFormat, x);
		return "(" + strY + "," + strX + ")";

	} // paddedCoords method

*/

	public void display() {

		for(int i = this.height - 1; i >= 0; i--) {
			for(int j = 0; j < this.width; j++)
				System.out.print(this.grid[j][i]);

			System.out.println();
		}

	} // displayArray method

}
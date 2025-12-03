// import java.lang.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Y25D02 {

	public static void main(String[] args) {
		long output = 0L;
		String strInput = "199617-254904,7682367-7856444,17408-29412,963327-1033194,938910234-938964425,3207382-3304990,41-84,61624-105999,1767652-1918117,492-749,85-138,140-312,2134671254-2134761843,2-23,3173-5046,16114461-16235585,3333262094-3333392446,779370-814446,26-40,322284296-322362264,6841-12127,290497-323377,33360-53373,823429-900127,17753097-17904108,841813413-841862326,518858-577234,654979-674741,773-1229,2981707238-2981748769,383534-468118,587535-654644,1531-2363";
		String[] rangeStrings = strInput.split(",");

		for (String rangeString : rangeStrings) {
			System.out.print(rangeString + ": ");
			ArrayList<Y25D02_Range> attackables = new Y25D02_Range(rangeString).breakup(7);

			for(Y25D02_Range range : attackables) {
				System.out.print(range.toString() + "; ");
				//ArrayList<Long> validOnes = range.findValidMembers();
				//for(long validOne : validOnes) output += validOne;
			}
			System.out.println();

		}

		//System.out.println("Final answer: " + output);

		//System.out.println(Y25D02_Range.isValid(21212121, 2));
	} // main
}
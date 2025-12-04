// import java.lang.*;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

class Y25D02 {

	public static void main(String[] args) {

		String strInput = "199617-254904,7682367-7856444,17408-29412,963327-1033194,938910234-938964425,3207382-3304990,41-84,61624-105999,1767652-1918117,492-749,85-138,140-312,2134671254-2134761843,2-23,3173-5046,16114461-16235585,3333262094-3333392446,779370-814446,26-40,322284296-322362264,6841-12127,290497-323377,33360-53373,823429-900127,17753097-17904108,841813413-841862326,518858-577234,654979-674741,773-1229,2981707238-2981748769,383534-468118,587535-654644,1531-2363";
		String[] rangeStrings = strInput.split(",");

		HashSet<Long> dedupedSet = new HashSet<Long>();


		for(int N = 2; N < 20; N++) {

			for (String rangeString : rangeStrings) {
				System.out.print(rangeString + ": ");
				ArrayList<Y25D02_Range> attackables = new Y25D02_Range(rangeString).breakup(N);

				for(Y25D02_Range range : attackables) {
					ArrayList<Long> validOnes = range.findValidMembers(N);
					for(long validOne : validOnes) {
						dedupedSet.add(validOne);
					}
				}
				System.out.println();

			}
		}

		long output = 0L;
		for(long survivor : dedupedSet) output += survivor;
		System.out.println("Final answer: " + output);

	} // main
}
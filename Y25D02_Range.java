import java.util.ArrayList;
import java.lang.*;

class Y25D02_Range {
	private long min;
	private long max;

	public Y25D02_Range(Long min, Long max) {

		this.min = min;
		this.max = max;
	} // longs-based constructor

	public Y25D02_Range(int min, int max) {
		this.min = (long) min;
		this.max = (long) max;
	} // ints-based constructor

	public Y25D02_Range(String strInput) {
		// trust absolutely that our string consists of two integers, both at least 1
		// and both within the bounds of Long, separated only by a hyphen
		String[] bounds = strInput.split("-");
		this.min = Long.parseLong(bounds[0]);
		this.max = Long.parseLong(bounds[1]);
	} // string-based constructor

	public ArrayList<Long> tenPowersWithin() {
		// return every positive power of ten (i.e. not 1) that lies between the limits, inclusively
		ArrayList<Long> output = new ArrayList<Long>();

		for(long comparator = 10L; comparator <= this.max; comparator *= 10L) {
			if(comparator >= this.min) output.add(comparator);
		} // comparator-multiplication loop

		return output;
	} // tenPowersWithin

	public String toString() {
		return this.min + "-" + this.max;

	} // toString


	public static boolean isValid(long input, int repeatCount) {
		int digitCount = 0;

		for(long comparator = 1L; comparator <= input; comparator *= 10L) {
			digitCount++;
		}
		// immediately rule out numbers of a non-multiple digit count
		if(digitCount % repeatCount != 0) return false;

		long relevantTenPower = (long) Math.pow(10, digitCount / repeatCount);

		ArrayList<Long> numberParts = new ArrayList<Long>();

		while(input > relevantTenPower) {
			long newNumberPart = input % relevantTenPower;
			for(long numberPart : numberParts) {
				if(numberPart != newNumberPart) return false;
			}
			numberParts.add(newNumberPart);
			input /= relevantTenPower;

		}
		return true;

	}
// ldfixme we are going to need to be careful not to double-count e.g. 4-valids as also being 2-valids...


	// by virtue of having a number of digits that's a multiple of N, any integer whose decimal
	// representation consists of one string of digits repeated N times must lie between
	// 10^(N * k - 1) and 10^(N * k) - 1, for some positive integer k
	// (e.g. 555 lies between 10^(3 - 1) and 10^3 - 1, for N=3 and k = 1)

	// so let's start with the task of transforming any given range into zero or more
	// "attackable" ranges, such that any of those has the following properties:
	// 		- it starts with a number that has an N-multiple of digits
	//		- it ends with a number that has an N-multiple of digits

	public ArrayList<Y25D02_Range> breakup(int N) {

		ArrayList<Y25D02_Range> output = new ArrayList<Y25D02_Range>();
		ArrayList<Long> powersOfTen = this.tenPowersWithin();

		// burn away the case where min = max
		if(this.min == this.max) {
			if (isValid(this.min, 2)) {
				output.add(this);
			}
			return output;
		}

		// and when it starts or ends on a power
		if(powersOfTen.size() > 0) {
			if(powersOfTen.get(0) == this.min)  {// thanks to the case above we know there's a second value
				Y25D02_Range revisedRange = new Y25D02_Range(this.min + 1, this.max);
				return revisedRange.breakup(N);
			}

			if(powersOfTen.get(0) == this.max)  {
				Y25D02_Range revisedRange = new Y25D02_Range(this.min, this.max - 1);
				return revisedRange.breakup(N);
			}
		}

		// burn away any chunk at the start of the range that is not multiply-digitted
		if(this.getOpeningDigitCount() % N != 0) {
			if(powersOfTen.size() == 0) return output; // the no-multiple-digits empty case
			Y25D02_Range revisedRange = new Y25D02_Range(powersOfTen.get(0), this.max); // eventually this will get us running on a range that begins with a desired multiple as its digit count
			return revisedRange.breakup(N);
		} // opening digit count check

		if(powersOfTen.size() > 0) {
			// after that burning, we now trust that we start with N*k digits for some k
			// mainstream case - return an array that includes everything UP to & exclusing that power, followed by the recursive answer to what's left
			Y25D02_Range head = new Y25D02_Range(this.min, powersOfTen.get(0) - 1);
			Y25D02_Range rump = new Y25D02_Range(powersOfTen.get(0), this.max); // tempting to add 1, knowing that a ten-power can't repeat, BUT that might EQUAL max
			output = rump.breakup(N);
			output.add(0, head);
			return output;

		} else {
			// N*k digits at start and no powers of ten means we can
			// rubber-stamp the whole input as an attackable range
			output.add(this);
			return output;
		}

	} // breakup

	// OK now we can trust we're in an attackable range - same number of digits at start and end
	// and this number being a multiple of N
	public ArrayList<Long> findValidMembers(int N) {
		ArrayList<Long> output = new ArrayList<Long>();

		// When N = 2, a repeater is just a multiple of some "root" number of the form 10^n + 1.
		// E.g. 1001 * 123 = 123123

		// For more general N, it will be the sum of 10^ik, where i ranges from N-1 to 0, and
		// k is the digit count of the repeating portion.
		// E.g. for N=3 and k=2, our root is 10^4 + 10^2 + 10^0 = 10101
		long root = 0L;
		int digitCountOfRepeater = this.getOpeningDigitCount() / N;
		for(int i = 0; i < N; i++) {
			root += (long) Math.pow(10, i * digitCountOfRepeater);
		}

		// easier to do it this way
		this.max -= (this.max % root);
		for(long i = this.max; i >= this.min; i -= root) output.add(0, i);
		return output;
	}


	public int getOpeningDigitCount() {
		return 1 + (int) Math.log10(this.min);
	} // getOpeningDigitCount

} // class

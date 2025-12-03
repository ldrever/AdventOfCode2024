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


	public static boolean isValid(long input) {
		int digitCount = 0;

		for(long comparator = 1L; comparator <= input; comparator *= 10L) {
			digitCount++;
		}
		// immediately rule out numbers of an odd digit count
		if(digitCount % 2 != 0) return false;

		long relevantTenPower = (long) Math.pow(10, digitCount / 2);
		long lhs = input / relevantTenPower;
		long rhs = input % relevantTenPower;

		return lhs == rhs;

	}



	// by virtue of having an even number of digits, any integer whose decimal
	// representation consists of one string of digits repeated twice must lie between
	// 10^(2n-1) and 10^(2n) - 1, for some positive integer n
	// (e.g. 22 lies between 10^1 and 99, for n = 1)

	// so let's start with the task of transforming any given range into zero or more
	// "attackable" ranges, such that any of those has the following properties:
	// 		- it starts with a number that has an even number of digits
	//		- it ends with a number that has an even number of digits

	public ArrayList<Y25D02_Range> breakup() {

		ArrayList<Y25D02_Range> output = new ArrayList<Y25D02_Range>();
		ArrayList<Long> powersOfTen = this.tenPowersWithin();

		// burn away the case where min = max
		if(this.min == this.max) {
			if (isValid(this.min)) {
				output.add(this);
			}
			return output;
		}

		// and when it starts or ends on a power
		if(powersOfTen.size() > 0) {
			if(powersOfTen.get(0) == this.min)  {// thanks to the case above we know there's a second value
				Y25D02_Range revisedRange = new Y25D02_Range(this.min + 1, this.max);
				return revisedRange.breakup();
			}

			if(powersOfTen.get(0) == this.max)  {
				Y25D02_Range revisedRange = new Y25D02_Range(this.min, this.max - 1);
				return revisedRange.breakup();
			}
		}

		// burn away any chunk at the start of the range that is odd-digitted
		if(this.getOpeningDigitCount() % 2 == 1) {
			if(powersOfTen.size() == 0) return output; // the no-even-digits empty case
			Y25D02_Range revisedRange = new Y25D02_Range(powersOfTen.get(0), this.max); // digit count check means we can trust the next power to be an odd power, ie starting off a region of even digits
			return revisedRange.breakup();
		} // odd count check

		if(powersOfTen.size() > 0) {

			// mainstream case - return an array that includes everything UP to & exclusing that power, followed by the recursive answer to what's left
			Y25D02_Range head = new Y25D02_Range(this.min, powersOfTen.get(0) - 1);
			Y25D02_Range rump = new Y25D02_Range(powersOfTen.get(0), this.max); // tempting to add 1, knowing that a ten-power can't repeat, BUT that might EQUAL max
			output = rump.breakup();
			output.add(0, head);
			return output;

		} else {
			// even digits at start and no powers of ten means we can
			// rubber-stamp the whole input as an attackable range
			output.add(this);
			return output;
		}

	} // breakup

	// OK now we can trust we're in an attackable range - same, even number of digits at start and end
	public ArrayList<Long> findValidMembers() {
		ArrayList<Long> output = new ArrayList<Long>();

		// a repeater is just a multiple of some "root" number of the form 10^n + 1.
		// e.g. 1001 * 123 = 123123
		long root = 1L + (long) Math.pow(10, this.getOpeningDigitCount() / 2);

		// easier to do it this way
		this.max -= (this.max % root);
		for(long i = this.max; i >= this.min; i -= root) output.add(0, i);
		return output;
	}


	public int getOpeningDigitCount() {
		return 1 + (int) Math.log10(this.min);
	} // getOpeningDigitCount

} // class

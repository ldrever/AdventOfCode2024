class Y25D03_ProgressElement {
	String openingString;
	String facingString;
	int placesLeft;

	public Y25D03_ProgressElement(String openingString, String facingString, int placesLeft) {
		this.openingString = openingString;
		this.facingString = facingString;
		this.placesLeft = placesLeft;
	}

	public String evaluate() {
		if(this.placesLeft == 0) return this.openingString;

		// only in the case where 1 place is left can we look at the WHOLE
		// facing string for a maximum
		char bestChar = (char) 0;
		int bestPosition = 0;
		char[] ra = this.facingString.toCharArray();

		for(int position = 0; position < ra.length + 1 - this.placesLeft; position++) {
			if(ra[position] > bestChar) {
				bestChar = ra[position];
				bestPosition = position;
			}

		}

		String newOpeningString = this.openingString + bestChar;
		String newFacingString = this.facingString.substring(bestPosition + 1);
		int newPlacesLeft = this.placesLeft - 1;
		Y25D03_ProgressElement ele = new Y25D03_ProgressElement(newOpeningString, newFacingString, newPlacesLeft);
		return ele.evaluate();
	}

	public String toString() {
		return this.openingString + "[and " + this.placesLeft + " from]" + this.facingString;
	}

}
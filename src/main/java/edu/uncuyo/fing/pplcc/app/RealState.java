package edu.uncuyo.fing.pplcc.app;

import java.util.Optional;

/** Can be developed
 */
class RealState extends Property {

	static public enum Development {
		NONE,
		HOUSE_1,
		HOUSE_2,
		HOUSE_3,
		HOUSE_4,
		HOTEL
	}

	private Development development;

	public RealState(Property.Group group, long value, long rent, String name, Optional<Cell> next) {
		super(group, value, rent, name, next);
		this.development = Development.NONE;
	}

	public long rent() {
		switch(this.development) {
			case HOUSE_1: return 5*rent;
			case HOUSE_2: return 15*rent;
			case HOUSE_3: return 45*rent;
			case HOUSE_4: return 80*rent;
		}

		return rent;
	}

	public long development_cost() {
		return this.value;
	}

}

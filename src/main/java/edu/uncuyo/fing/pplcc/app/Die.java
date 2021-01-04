package edu.uncuyo.fing.pplcc.app;

import java.util.Random;
import java.util.LinkedList;

/**
 * The Die class represents a dice.
 *
 * It allows the consistent generation of rolls for a given seed.
 *
 */
class Die implements ShowS {
	private int current_value;
	private Random rng;

	public Die() {
		rng = new Random();
		this.roll();
	}

	public Die(long seed) {
		rng = new Random(seed);
		this.roll();
	}

	public LinkedList<String> showS() {
		LinkedList<String> ret= new LinkedList<String>();
		ret.add("Die:");
		ret.add("\tcurrent_value: " + this.current_value );
		return ret;
	}
	/**
	 * Sets the current value in [1..6]
	 */
	public void roll() {
		this.current_value = rng.nextInt(5) + 1;
	}

	public int get_current_value() {
		return this.current_value;
	}
}

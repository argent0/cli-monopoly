package edu.uncuyo.fing.pplcc.app;

import java.util.LinkedList;

/**
 * The Roll class represents the result of the roll of die.
 *
 * It represents the peculiarities of the dice roll for monopoly.
 *
 */

class Roll implements Show {
	public final int result_a;
	public final int result_b;

	public Roll(Die die_a, Die die_b) {
		die_a.roll();
		die_b.roll();
		this.result_a = die_a.get_current_value();
		this.result_b = die_b.get_current_value();
	}

	public int sum() {
		return result_a + result_b;
	}

	public LinkedList<String> showS() {
		LinkedList<String> ret= new LinkedList<String>();
		ret.add("Roll:");
		ret.add("\tresult_a: " + this.result_a);
		ret.add("\tresult_b: " + this.result_b);
		return ret;
	}
	
	public Boolean is_double() {
		return result_a == result_b;
	}
}

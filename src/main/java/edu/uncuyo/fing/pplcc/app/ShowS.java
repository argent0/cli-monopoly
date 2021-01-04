package edu.uncuyo.fing.pplcc.app;

import java.util.LinkedList;

/**
 * The ShowS interface for representing objects as a list of strings
 *
 */
interface ShowS {
	LinkedList<String> showS();

	default LinkedList<String> indentedShow() {
		LinkedList<String> ret= new LinkedList<String>();
		for (String str : this.showS() ) {
			ret.add("\t" + str);
		}
		return ret;
	}
}

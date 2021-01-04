package edu.uncuyo.fing.pplcc.app;

/**
 * The Show interface for displaying objects on the screen.
 *
 */
interface Show extends ShowS {
	default void show() {
		for( String line : this.showS() ) {
			System.out.println(line);
		}
	}
}

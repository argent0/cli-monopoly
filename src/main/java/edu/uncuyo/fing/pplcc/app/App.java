package edu.uncuyo.fing.pplcc.app;

import edu.uncuyo.fing.pplcc.app.*;

/**
 * The main app handles the creation of games.
 *
 */
public class App {

	public static void main( String[] args ) throws Piece.NoRemainingPiecesException {

		Game game;

		game = new Game(4);

		game.run();
	}

}

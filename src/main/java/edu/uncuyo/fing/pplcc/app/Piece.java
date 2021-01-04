package edu.uncuyo.fing.pplcc.app;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.util.Iterator;
import java.util.function.Function;

class Piece implements ShowS {
	/** The shape of the piece
	 *
	 * There are at most 4 players so I only include four shapes.
	 */

	static public enum Shape {
		HAT,
		CAR,
		BOOT,
		IRON;
	};

	static public class NoRemainingPiecesException extends Exception { };

	public final Shape shape;
	private Cell cell;

	/** These are the remmaining shapes
	 *
	 * There should be only one piece per shape
	 */
	static private Iterator<Shape> available_shapes =
		new LinkedList<Shape>( Arrays.asList(Shape.HAT, Shape.CAR, Shape.BOOT, Shape.IRON)).iterator();

	public Piece(Home cell) throws NoRemainingPiecesException {
		if (available_shapes.hasNext()) {
			this.shape = available_shapes.next();
			this.cell = cell;
		} else {
			throw new NoRemainingPiecesException();
		}
	};

	/** Advance one step
	 */
	public void advance(Function<Cell, Cell> step) {
		this.cell = step.apply(cell);
	}

	/** Get the effects of landing on the current cell
	 */
	public List<Cell.LandEffect> on_land() {
		return this.cell.get_on_land_effects();
	}

	/** Get the effects of entering in the current cell
	 */
	public List<Cell.EnterEffect> on_enter() {
		return this.cell.get_on_enter_effects();
	}

	/** Go directly to the specified cell (usually jail)
	 */
	public void send_to(Cell cell) {
		this.cell = cell;
	}

	public LinkedList<String> showS() {
		LinkedList<String> ret= new LinkedList<String>();
		ret.add("Piece: ");
		ret.add("\tShape: " + this.shape.toString() );
		ret.addAll(this.cell.indentedShow());
		return ret;
	}

	public String cell_name() {
		return cell.name();
	}

}

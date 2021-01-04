package edu.uncuyo.fing.pplcc.app;

import java.util.LinkedList;
import java.util.function.Function;

/**
 * The Player class represents the player
 *
 */
class Player implements Show {

	private final String name;
	public final Piece piece;
	private long money;
	private long debt = 0;

	private final Jail jail;
	private boolean is_prisoner = false;

	private long completed_rounds = 0;

	private final LinkedList<Property> properties = new LinkedList<Property>();

	Player(String name, long money, Home home, Jail jail) throws Piece.NoRemainingPiecesException {
		this.name = name;
		this.money = money;
		this.piece = new Piece(home);
		this.jail = jail;
	}

	public String name() {
		return new String(this.name);
	}

	public String money_str() {
		return new String("" + this.money);
	}

	/**
	 * Give the player the dice and they will generate a roll.
	 */
	public Roll roll(Die die_a, Die die_b) {
		return new Roll(die_a, die_b);
	}

	/**
	 * Call when the player completes a round.
	 */
	public void complete_round() {
		this.completed_rounds += 1;
	}

	public void advance(Function<Cell, Cell> step) {
		this.piece.advance(step);
	}

	public void receive(long amount) {
		this.money += amount;
	}

	public boolean can_pay(long amount) {
		return money >= amount;
	}

	public void pay(long amount) {
		this.money -= amount;

		if (this.money < 0) {
			this.debt = -this.money;
			this.money = 0;
		}
	}

	public void imprison() {
		this.piece.send_to(jail);
		this.is_prisoner = true;
	}

	public void release() {
		this.is_prisoner = false;
	}

	public boolean in_jail() {
		return this.is_prisoner;
	};

	public long completed_rounds() {
		return completed_rounds;
	}

	public void add_property(Property property) {
		this.properties.add(property);
	}

	public long group_share(Property.Group group) {
		return properties.stream()
			.filter(p -> p.group == group)
			.mapToInt(p -> 1)
			.sum();
	}

	public LinkedList<String> showS() {
		LinkedList<String> ret= new LinkedList<String>();
		ret.add("Player: ");
		ret.add("\tName: " + this.name );
		ret.add("\tMoney: " + this.money );
		ret.add("\tRounds: " + this.completed_rounds);
		ret.addAll(this.piece.indentedShow());
		return ret;
	}
}

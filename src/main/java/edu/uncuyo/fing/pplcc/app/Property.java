package edu.uncuyo.fing.pplcc.app;

import java.util.Optional;
import java.util.List;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.lang.Runnable;

/** Can be acquired by the player
 */
class Property extends Cell implements ShowS, Show {

	static public enum Group {
		SPRUCE, //GrayBlue
		THISTLE, //GrayPurple
		PURPLE,
		ORANGE,
		RED,
		YELLOW,
		GREEN,
		BLUE,
		RAIL_ROAD,
		SERVICE
	}

	protected long rent;
	protected long value;
	public final Group group;

	private Optional<Player> owner = Optional.empty();

	public Property(Group group, long value, long rent, String name, Optional<Cell> next) {
		super(name, next);
		this.group = group;
		this.value = value;
		this.rent = rent;
	}

	public String name() {
		return new String(this.name);
	}

	public List<LandEffect> get_on_land_effects() {
		return List.of(Cell.LandEffect.player_enters_private_property(this));
	}

	public long mortgage_value() {
		return java.lang.Math.round((double)this.value / 2.0);
	}

	public long unmorgage_cost() {
		return java.lang.Math.round((double)this.value / 2 * 1.1);
	}

	public LinkedList<String> showS() {
		LinkedList<String> ret= super.showS();
		ret.add("\tRent:" + rent);
		ret.add("\tOwner: " + owner.toString());
		return ret;
	}

	public void ifOwnedOrElse(Consumer<Player> owned_action, Runnable unowned_action) {
		this.owner.ifPresentOrElse(owned_action, unowned_action);
	}

	public void buy(Player player) {
		this.owner = Optional.of(player);
		player.add_property(this);
	}

	public long value() {
		return this.value;
	}

	public long rent() {
		return this.rent;
	}

	@Override
	public String cell_str() {
		String owner = new String("Banco");
		if (this.owner.isPresent()) {
			owner = this.owner.get().name();
		}
		return new String(group.ordinal() + "\t" + String.format("%25s", name) + "\t" +
			String.format("%10s", owner));
	}

}

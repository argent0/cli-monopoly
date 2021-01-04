package edu.uncuyo.fing.pplcc.app;

import java.util.Optional;
import java.util.LinkedList;
import java.util.List;
import java.lang.Runnable;
import java.util.function.Consumer;
import java.util.function.Supplier;

class Cell implements ShowS {

	static public abstract class EnterEffect extends Effect {

		private EnterEffect(String name) {
			super(name);
		}

		public abstract void handle(
			Consumer<Integer> player_receives_money,
			Runnable player_completes_round);

		public static EnterEffect player_receives_money(Integer amount) {
			return new EnterEffect("Player receives money: " + amount.toString()) {
				@Override
				public void handle(
						Consumer<Integer> player_receives_money,
						Runnable player_completes_round) {
							player_receives_money.accept(amount);
						}
			};
		}

		public static EnterEffect player_completes_round() {
			return new EnterEffect("Player completes round") {
				@Override
				public void handle(
						Consumer<Integer> player_receives_money,
						Runnable player_completes_round) {
							player_completes_round.run();
						}
			};
		}
	}

	static public abstract class LandEffect extends Effect {

		private LandEffect(String name) {
			super(name);
		}

		public abstract void handle(
				Runnable player_is_imprisoned,
				Consumer<Cell> player_goes_to,
				Runnable player_picks_up_chance,
				Runnable player_picks_up_comunity,
				Consumer<Property> player_enters_private_property);

		public static LandEffect player_gets_imprisoned() {
			return new LandEffect("Player goes to jail") {
				@Override
				public void handle(
						Runnable player_gets_imprisoned,
						Consumer<Cell> player_goes_to,
						Runnable player_picks_up_chance,
						Runnable player_picks_up_comunity,
						Consumer<Property> player_enters_private_property) {
					player_gets_imprisoned.run();
						}
			};
		}

		public static LandEffect player_goes_to(Supplier<Cell> destination) {
			return new LandEffect("Player goes to jail") {
				@Override
				public void handle(
						Runnable player_gets_imprisoned,
						Consumer<Cell> player_goes_to,
						Runnable player_picks_up_chance,
						Runnable player_picks_up_comunity,
						Consumer<Property> player_enters_private_property) {
					player_goes_to.accept(destination.get());
						}
			};
		}

		public static LandEffect player_picks_up_chance() {
			return new LandEffect("Player picks up chance") {
				@Override
				public void handle(
						Runnable player_gets_imprisoned,
						Consumer<Cell> player_goes_to,
						Runnable player_picks_up_chance,
						Runnable player_picks_up_comunity,
						Consumer<Property> player_enters_private_property) {
					player_picks_up_chance.run();
						}
			};
		}

		public static LandEffect player_picks_up_comunity() {
			return new LandEffect("Player picks up comunity") {
				@Override
				public void handle(
						Runnable player_gets_imprisoned,
						Consumer<Cell> player_goes_to,
						Runnable player_picks_up_chance,
						Runnable player_picks_up_comunity,
						Consumer<Property> player_enters_private_property) {
					player_picks_up_comunity.run();
						}
			};
		}

		public static LandEffect player_enters_private_property(Property property) {
			return new LandEffect("Player enters privater property") {
				@Override
				public void handle(
						Runnable player_gets_imprisoned,
						Consumer<Cell> player_goes_to,
						Runnable player_picks_up_chance,
						Runnable player_picks_up_comunity,
						Consumer<Property> player_enters_private_property) {
					player_enters_private_property.accept(property);
						}
			};
		}
	}

	/**
	 * The next cell on the board.
	 * 
	 * Optional.empty() signals the end of the cycle.
	 */
	public final Optional<Cell> next;

	protected final String name;
	
	/**
	 * The effects to run when a piece enteres into the cell.
	 */
	private final List<EnterEffect> on_enter_effects;

	/**
	 * The effects to run when a piece lands on the cell.
	 */
	private final List<LandEffect> on_land_effects;

	public Cell(String name, Optional<Cell> next) {
		this.name = name;
		this.next = next;
		this.on_enter_effects = List.of();
		this.on_land_effects = List.of();
	}

	public Cell(String name, List<EnterEffect> on_enter_effects, Optional<Cell> next) {
		this.name = name;
		this.next = next;
		this.on_enter_effects = on_enter_effects;
		this.on_land_effects = List.of();
	}

	public Cell(String name, List<EnterEffect> on_enter_effects, List<LandEffect> on_land_effects, Optional<Cell> next ) {
		this.name = name;
		this.next = next;
		this.on_enter_effects = on_enter_effects;
		this.on_land_effects = on_land_effects;
	}

	public List<LandEffect> get_on_land_effects() {
		return List.copyOf(this.on_land_effects);
	}
	
	public List<EnterEffect> get_on_enter_effects() {
		return List.copyOf(this.on_enter_effects);
	}

	public LinkedList<String> showS() {
		LinkedList<String> ret= new LinkedList<String>();
		ret.add("Cell: " + this.name);
		ret.add("\tOnEnter:");
		for ( EnterEffect effect : this.on_enter_effects) {
			ret.addAll(effect.indentedShow());
		}	
		ret.add("\tOnLand:");
		for ( Effect effect : this.on_land_effects) {
			ret.addAll(effect.indentedShow());
		}	
		return ret;
	}

	public String name() {
		return new String(name);
	}

	public String cell_str() {
		return new String(" \t" + String.format("%25s", name) + "\t" + String.format("%10s", ""));
	}


}

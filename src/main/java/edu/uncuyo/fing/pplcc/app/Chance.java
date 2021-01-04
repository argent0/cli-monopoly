package edu.uncuyo.fing.pplcc.app;

import java.util.Optional;
import java.util.List;

class Chance extends Cell {
	public Chance(String name, Optional<Cell> next) {
		super("Casualidad " + name
				, List.of()
				, List.of( Cell.LandEffect.player_picks_up_chance() )
				, next);
	}
}

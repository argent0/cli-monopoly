package edu.uncuyo.fing.pplcc.app;

import java.util.Optional;
import java.util.LinkedList;
import java.util.List;

class Home extends Cell {
	public Home(Cell next) {
		super("Casa",
				// On enter effects
				List.of(
					Cell.EnterEffect.player_receives_money(200),
					Cell.EnterEffect.player_completes_round())
				, Optional.of(next));
	}
}

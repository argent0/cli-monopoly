package edu.uncuyo.fing.pplcc.app;

import java.util.Optional;
import java.util.List;

class ComunityChest extends Cell {
	public ComunityChest(String name, Optional<Cell> next) {
		super("Arca Comunal " + name
				, List.of()
				, List.of( Cell.LandEffect.player_picks_up_comunity() )
				, next);
	}
}

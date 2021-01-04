package edu.uncuyo.fing.pplcc.app;

import java.util.Optional;
import java.util.LinkedList;
import java.util.List;

class Jail extends Cell {
	public Jail(Optional<Cell> next) {
		super("Carcel", next);
	}
}

package edu.uncuyo.fing.pplcc.app;

import java.util.LinkedList;
import java.util.List;

abstract class Effect implements ShowS {
	protected final String name;

	protected Effect(String name) {
		this.name = name;
	}

	public final LinkedList<String> showS() {
		return new LinkedList<String>(List.of("EnterEffect: " + name));
	}
}

package edu.uncuyo.fing.pplcc.app;

import java.util.Optional;
import java.util.LinkedList;
import java.util.List;
import java.util.Deque;
import java.util.Collections;

class Board implements ShowS {

	private final Home home;
	private final Jail jail;
	private final Cell free_parking;
	private final Cell go_to_jail;

	private LinkedList<Player> players = new LinkedList<Player>();

	public Board(int n_players) throws Piece.NoRemainingPiecesException {

		int base_rent = 2;
		int rail_road_rent = 25;
		int company_rent = 25;
		int company_value = 150;

		this.go_to_jail = new Cell("Marche Preso"
			, List.of()
			, List.of(Cell.LandEffect.player_gets_imprisoned()) , Optional.of(
			// Ciudades de la costa
			new RealState(Property.Group.GREEN, 300, 36, "Mar del Plata", Optional.of(
			new RealState(Property.Group.GREEN, 300, 38, "Bahía Blanca", Optional.of(
			new ComunityChest("C", Optional.of(
			new RealState(Property.Group.GREEN, 300, 40, "Videma", Optional.of(
			new Property(Property.Group.RAIL_ROAD, company_value, rail_road_rent, "Ferro Carril D", Optional.of(
			new Chance("C", Optional.of(
			// Barrios de Capital
			new RealState(Property.Group.BLUE, 340, 42, "Recoleta", Optional.of(
			new Property(Property.Group.SERVICE, company_value, company_rent, "Compañía de Taxis", Optional.of(
			new RealState(Property.Group.BLUE, 340, 44, "Devoto", Optional.empty())))))))))))))))))));

		this.free_parking = new Cell("Estacionamiento Gratis", Optional.of(
			// Patagonia
			new RealState(Property.Group.RED, 220, 24, "Tierra del Fuego", Optional.of(
			new Chance("B", Optional.of(
			new RealState(Property.Group.RED, 220, 26, "Santa Cruz", Optional.of(
			new RealState(Property.Group.RED, 220, 28, "Chubut", Optional.of(
			new Property(Property.Group.RAIL_ROAD, company_value, rail_road_rent, "Ferro Carril C", Optional.of(
			// Otras provincias
			new RealState(Property.Group.YELLOW, 260, 30, "Rio Negro", Optional.of(
			new RealState(Property.Group.YELLOW, 260, 32, "Córdoba", Optional.of(
			new Property(Property.Group.SERVICE, company_value, company_rent, "Compañía de Teléfonos", Optional.of(
			new RealState(Property.Group.YELLOW, 260, 34, "Chaco", Optional.of(
			this.go_to_jail))))))))))))))))))));

		this.jail = new Jail(Optional.of(
			// Litoral/La Pampa
			new RealState(Property.Group.PURPLE, 140, 12, "Buenos Aires", Optional.of(
			new Property(Property.Group.SERVICE, company_value, company_rent, "Compañía Eléctrica", Optional.of(
			new RealState(Property.Group.PURPLE, 140, 14, "La Pampa", Optional.of(
			new RealState(Property.Group.PURPLE, 140, 16, "Santa Fe", Optional.of(
			new Property(Property.Group.RAIL_ROAD, company_value, rail_road_rent, "Ferro Carril B", Optional.of(
			// Mesopotamia
			new RealState(Property.Group.ORANGE, 180, 18, "Corrientes", Optional.of(
			new ComunityChest("B", Optional.of(
			new RealState(Property.Group.ORANGE, 180, 20, "Misiones", Optional.of(
			new RealState(Property.Group.ORANGE, 180, 22, "Entre Rios", Optional.of(
			this.free_parking))))))))))))))))))));

		this.home = new Home(
			// Noroeste
			new RealState(Property.Group.SPRUCE, 60, 2, "Salta", Optional.of(
			new ComunityChest("A", Optional.of(
			new RealState(Property.Group.SPRUCE, 60, 4, "Jujuy", Optional.of(
			new Property(Property.Group.SERVICE, company_value, company_rent, "Compañía de Agua", Optional.of(
			new Property(Property.Group.RAIL_ROAD, company_value, rail_road_rent, "Ferro Carril A", Optional.of(
			// Cuyo
			new RealState(Property.Group.THISTLE, 100, 6, "Mendoza", Optional.of(
			new Chance("A", Optional.of(
			new RealState(Property.Group.THISTLE, 100, 8, "San Luis", Optional.of(
			new RealState(Property.Group.THISTLE, 100, 10, "San Juan", Optional.of(
			this.jail)))))))))))))))))));

		for(int idx = 0; idx < n_players; idx++) {
			this.players.addFirst(new Player("Joe " + idx, 1500, home, jail ));
		}

		// Random starting order
		Collections.shuffle(players);
	}

	private Cell jail_cell() {
		return jail;
	}

	/** The current player
	 */

	public Player current() {
		return this.players.removeFirst();
	}

	/** Enque player at the end
	 */
	void enqueue_last(Player player) {
		this.players.addLast(player);
	}

	/** Enque player at the begining
	 */
	void enqueue_first(Player player) {
		this.players.addFirst(player);
	}

	/**
	 * Given a cell returns the text one.
	 *
	 * This makes the cells cycle.
	 */
	public Cell next_cell(Cell cell) {
		return cell.next.orElse(home);
	}

	public LinkedList<String> showS() {
		LinkedList<String> ret= new LinkedList<String>();

		ret.add("Board:");

		for (Player player : this.players) {
			ret.addAll(player.indentedShow());
		}

		Optional<Cell> cursor = Optional.of(home);

		while( cursor.isPresent() ) {

			ret.addAll(
				cursor.map( cell -> cell.indentedShow() ).orElse(new LinkedList<String>()));

			cursor = cursor.get().next;
		}

		return ret;
	}

	public void show_board() {
		Optional<Cell> cursor = Optional.of(home);

		System.out.println("Grupo \t" + String.format("%25s", "Nombre") + "\t" +
			String.format("%10s", "Dueño") + "\tPresentes\n");


		while( cursor.isPresent() ) {

			System.out.print(cursor.get().cell_str() + "\t");

			final String cell_name = cursor.get().name();

			this.players.stream()
				.filter(p -> p.piece.cell_name().equals(cell_name))
				.forEach(p -> System.out.print(p.name() + "," ));

			System.out.println("");

			cursor = cursor.get().next;
		}
	}
}

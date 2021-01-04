package edu.uncuyo.fing.pplcc.app;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The Game class handles the rules of the game.
 *
 * This is like a game master.
 *
 */

class Game implements Show {

	private int n_players;
	private final Die die_a = new Die();
	private final Die die_b = new Die();
	private final Board board;
	private final long jail_fine = 50;

	private final CardDeck chance_deck = new CardDeck();
	private final CardDeck comunity_deck = new CardDeck();

	public Game(int n_players) throws Piece.NoRemainingPiecesException {
		board = new Board(n_players);
	}

	public void run() {

		long completed_rounds = 0;

		while(completed_rounds < 11) {

			System.out.println("\n------------------\n");

			board.show_board();

			System.out.println("");

			// Grab the current player.
			Player current_player = this.board.current();

			if (current_player.completed_rounds() > completed_rounds) {
				completed_rounds = current_player.completed_rounds();
			}

			show_player(current_player);

			if (current_player.in_jail()) {
				if (current_player.can_pay(jail_fine)) {
					System.out.println("El jugador paga la fianza.");
					current_player.pay(jail_fine);
					current_player.release();
				}
			}

			if (!current_player.in_jail()) {

				// Generate a roll for the player.
				Roll roll = current_player.roll(die_a, die_b);
				show_roll(roll);

				// Advance the player to its destinatino
				for(int step = 0; step < roll.sum(); step++) {
					advance_player_step(current_player);
					// Apply the effects of entering the cell (mostly 200$
					// salary)
					for( Cell.EnterEffect effect : current_player.piece.on_enter() ) {
						effect.handle(
							//player receives money
							amount -> current_player.receive(amount),
							//player completes round
							() -> current_player.complete_round()
						);
					}
				}

				// Apply the effects of the cell where it landed
				for( Cell.LandEffect effect : current_player.piece.on_land() ) {
					effect.handle(
						// player is imprisoned
						() -> {
							System.out.println("El jugador va preso");
							current_player.imprison();
						},
						// player goes to
						Cell -> current_player.imprison(),
						// player picks up chance
						() -> {
							System.out.println("Casualidad");
							pick_up_card(current_player, chance_deck);
						},
						// player picks up comunty
						() -> {
							System.out.println("Arca Comunal");
							pick_up_card(current_player, comunity_deck);
						},
						// player enters private property
						property -> {
							System.out.println("El Jugador entra en propiedad privada: " + property.name() );

							property.ifOwnedOrElse(
								owner -> {
									System.out.println("Propiedad perteneciente a: " + owner.name());
									System.out.print("Pertence al grupo: ");
									show_group(property.group);

									if (current_player.can_pay(property.rent())) {

										System.out.println("El jugador paga una renta de: " + property.rent() );

										current_player.pay(property.rent());

										owner.receive(property.rent());
									}
								},
								() -> {
									System.out.println("Es propiedad del banco.");
									if (current_player.completed_rounds() >= 1) {
										System.out.println("Desea comprarla?");

										if (current_player.can_pay(property.value())) {
											current_player.pay(property.value());

											property.buy(current_player);

											System.out.println("El Jugador compra la propiedad");
										}
									} else {
										System.out.println("El Jugador aun no ha completado una ronda");
									}
								}
							);

						}
					);
				}

				// TODO: Check if player has debt

				if (roll.is_double() && !current_player.in_jail()) {
					this.board.enqueue_first(current_player);
				} else {
					this.board.enqueue_last(current_player);
				}

			} //end if in_jail

		} // end while
	}

	/** Pick up a card and apply its effects
	 */
	private void pick_up_card(Player current_player, CardDeck deck) {
		Card drawn_card = deck.pick();
		drawn_card.show();
		drawn_card.handle(
				// go to jail
				() -> current_player.imprison(),
				// get out of jail
				() -> System.out.println("GOJ"),
				// pay up
				amount -> current_player.pay(amount),
				// receive
				amount -> current_player.receive(amount),
				// go to
				cell -> System.out.println("Go to")
				);
		deck.add_card(drawn_card);
	}

	private void advance_player_step(Player player) {
		player.advance(cell -> board.next_cell(cell));
	}

	public LinkedList<String> showS() {
		LinkedList<String> ret= new LinkedList<String>();
		ret.add("Game: ");
		ret.addAll(die_a.indentedShow());
		ret.addAll(die_b.indentedShow());
		ret.addAll(board.indentedShow());
		return ret;
	}

	private void show_player(Player player) {

		final ArrayList<String> names = new ArrayList<String>(List.of(
			"Sombrero",
			"Carro",
			"Bota",
			"Plancha"));

		System.out.println("Jugador: " + player.name()
				+ " Pieza: " + names.toArray()[player.piece.shape.ordinal()]
				+ ", Dinero: " + player.money_str() +
			" Rondas: " + player.completed_rounds());
		System.out.println("Ubicación: " + player.piece.cell_name());
	}

	private void show_roll(Roll roll) {
		System.out.print("Los dados muestran: " + roll.result_a + " y " + roll.result_b);
		if (roll.is_double()) {
			System.out.println(" Doble! ");
		} else {
			System.out.println("");
		}
	}

	private void show_group(Property.Group group) {
		final ArrayList<String> names = new ArrayList<String>(List.of(
			"gris", //GrayBlue
			"violeta", //GrayPurple
			"purpura",
			"naranja",
			"rojo",
			"amarillo",
			"verde",
			"azul",
			"ferro carril",
			"servicios"
			));

		System.out.println(names.toArray()[group.ordinal()]);
	}

}

package edu.uncuyo.fing.pplcc.app;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Queue;

/** Represents a car deck
 */
class CardDeck {

	private LinkedList<Card> stack = new LinkedList<Card>(List.of(
			Card.go_to_jail(),
			Card.get_out_of_jail(),
			Card.pay_money(100),
			Card.pay_money(200),
			Card.pay_money(400),
			Card.receive_money(100),
			Card.receive_money(200),
			Card.receive_money(400)
		));

	private LinkedList<Card> used_cards = new LinkedList<Card>();

	public CardDeck() {
		Collections.shuffle(stack);
	};

	/** Remove the top card reshuffling if necessary
	 */

	public Card pick() {
		if (stack.isEmpty()) {
			stack.addAll(used_cards);
			Collections.shuffle(stack);
			used_cards = new LinkedList<Card>();
		}

		return stack.remove();

	}

	/** Returns a card to the deck
	 */
	public void add_card(Card card) {
		used_cards.add(card);
	};
}

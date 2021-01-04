package edu.uncuyo.fing.pplcc.app;

import java.util.function.Consumer;
import java.lang.Runnable;
import java.util.LinkedList;
import java.util.List;

abstract class Card implements ShowS, Show {

	private Card() {}

	public abstract void handle(
		Runnable go_to_jail,
		Runnable get_out_of_jail,
		Consumer<Integer> pay_money,
		Consumer<Integer> receive_money,
		Consumer<Cell> advance_to);

	public abstract LinkedList<String> showS();

	public static Card go_to_jail() {
		return new Card() {
			@Override
			public void handle(
					Runnable go_to_jail,
					Runnable get_out_of_jail,
					Consumer<Integer> pay_money,
					Consumer<Integer> receive_money,
					Consumer<Cell> advance_to) {
				go_to_jail.run();
					}

			@Override
			public LinkedList<String> showS() {
				return new LinkedList<String>(List.of("Card: go to jail"));
			}
		};
	}

	public static Card get_out_of_jail() {
		return new Card() {
			@Override
			public void handle(
					Runnable go_to_jail,
					Runnable get_out_of_jail,
					Consumer<Integer> pay_money,
					Consumer<Integer> receive_money,
					Consumer<Cell> advance_to) {
				get_out_of_jail.run();
					}

			@Override
			public LinkedList<String> showS() {
				return new LinkedList<String>(List.of("Card: get out of jail"));
			}
		};
	}

	public static Card pay_money(Integer amount) {
		return new Card() {
			@Override
			public void handle(
					Runnable go_to_jail,
					Runnable get_out_of_jail,
					Consumer<Integer> pay_money,
					Consumer<Integer> receive_money,
					Consumer<Cell> advance_to) {
				pay_money.accept(amount);
					}

			@Override
			public LinkedList<String> showS() {
				return new LinkedList<String>(List.of("Card: pay " + amount.toString() ));
			}
		};
	}

	public static Card receive_money(Integer amount) {
		return new Card() {
			@Override
			public void handle(
					Runnable go_to_jail,
					Runnable get_out_of_jail,
					Consumer<Integer> pay_money,
					Consumer<Integer> receive_money,
					Consumer<Cell> advance_to) {
				receive_money.accept(amount);
					}

			@Override
			public LinkedList<String> showS() {
				return new LinkedList<String>(List.of("Card: receive " + amount.toString() ));
			}
		};
	}

	public static Card advance_to(Cell destination) {
		return new Card() {
			@Override
			public void handle(
					Runnable go_to_jail,
					Runnable get_out_of_jail,
					Consumer<Integer> pay_money,
					Consumer<Integer> receive_money,
					Consumer<Cell> advance_to) {
				advance_to.accept(destination);
					}

			@Override
			public LinkedList<String> showS() {
				return new LinkedList<String>(List.of("Card: advance to"));
			}
		};
	}


}

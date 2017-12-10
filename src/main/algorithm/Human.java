package main.algorithm;

import main.container.Information;
import main.checker.Validator;
import main.container.Card;
import main.container.CardHolder;
import main.enums.Number;
import main.enums.Suit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Human implements Algorithm {
	private CardHolder strToCards(String input) {
		List<String> str_list = Arrays.asList(input.split(" "));
		CardHolder cards = new CardHolder();

		for (String str : str_list) {
			int num = Integer.parseInt(str.substring(1, str.length()));
			char suit = str.charAt(0);
			cards.add(new Card(Suit.valueOf(suit), Number.getInstance(num)));
		}
		return cards;
	}

	public CardHolder play(CardHolder hand, Information info) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("\n" + info + "[your hands]\n" + hand + "\n\n");

		String str;
		CardHolder cardHolder = new CardHolder();

		while (true) {
			System.out.print("enter the cards you want to play (ex.D5 D6 D7, pass, etc.)\n > ");

			try {
				str = br.readLine();

				switch (str) {
					case "pass":
						break;

					default:
						cardHolder = strToCards(str);
						break;
				}

				if (Validator.check(hand, cardHolder, info)) {
					System.out.print("\n");
					return cardHolder;
				} else {
					System.out.println("please enter the cards correctly");
				}

			} catch (Exception e) {
				System.out.println("please enter the cards correctly");
			}
		}
	}
}

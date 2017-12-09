package algorithm;

import elements.Information;
import elements.card.Card;
import elements.card.CardHolder;
import elements.card.Number;
import elements.card.Suit;

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
		System.out.print(info + "[your hands]\n" + hand + "\n\n");

		String str;

		while (true) {
			System.out.print("enter the cards you want to play (ex.D5 D6 D7, pass, etc.)\n > ");

			try {
				str = br.readLine();

				switch (str) {
					case "pass":
						return new CardHolder();

					default:
						try {
							return strToCards(str);

						} catch (Exception e) {
							System.out.println("please enter the cards correctly");
							break;
						}
				}
			} catch (Exception e) {
				System.out.println(e);
			}

			System.out.print("\n");
		}
	}
}

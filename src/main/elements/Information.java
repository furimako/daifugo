package main.elements;

import main.elements.card.Card;
import main.elements.card.CardHolder;
import main.elements.player.Player;
import main.elements.player.StateOfPlayer;

import java.util.List;
import java.util.Map;

public class Information extends Field {
	private Map<Player, Integer> numOfCards;

	public Information(Map<Player, Integer> numOfCards, CardHolder cardsInField, CardHolder cardsPlayed, boolean revolution, boolean bind) {
		super(cardsInField, cardsPlayed, revolution, bind);
		this.numOfCards = numOfCards;
	}

	public Map<Player, Integer> numOfCards() {
		return numOfCards;
	}

	public CardHolder cardsInField() {
		return cardsInField;
	}

	public CardHolder cardsPlayed() {
		return cardsPlayed;
	}

	public boolean revolution() {
		return revolution;
	}

	public boolean bind() {
		return bind;
	}

	@Override
	public String toString() {
		String str = "[num of cards]\n";

		for (Map.Entry<Player, Integer> entry : numOfCards.entrySet()) {
			str += entry.getKey() + " have " + entry.getValue() + " cards";
			if (entry.getKey().getState() == StateOfPlayer.ACTIVE) {
				str += " (active)";
			}
			str += "\n";
		}

		str += "\n[field]\n";
		str += "Cards in field: " + cardsInField;
		str += "\n";
		str += "bind: " + ((bind) ? "YES" : "NO");
		str += "\n";
		str += "revolution: " + ((revolution) ? "YES" : "NO");
		str += "\n\n";

		return str;
	}
}
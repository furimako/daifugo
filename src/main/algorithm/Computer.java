package main.algorithm;

import main.elements.Information;
import main.elements.card.CardHolder;
import main.elements.card.Number;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Computer implements Algorithm {
	private List<CardHolder> choices(CardHolder hand, Information info) {
		List<CardHolder> choices = new ArrayList<>();
		int numOfCards = info.cardsInField().numOfCards();

		if (numOfCards == 0) {
			choices.addAll(hand.noneSequenceList(Number.MIN_POWER, Number.MAX_POWER));
			choices.addAll(hand.sequenceList(Number.MIN_POWER, Number.MAX_POWER));
			return choices;
		}

		int fieldPower = info.cardsInField().highCard().power();

		if (!info.revolution()) {
			choices.addAll(hand.noneSequenceList(fieldPower + 1, Number.MAX_POWER, numOfCards));
			choices.addAll(hand.sequenceList(fieldPower + 1, Number.MAX_POWER));
		} else {
			choices.addAll(hand.noneSequenceList(Number.MIN_POWER, fieldPower - 1, numOfCards));
			choices.addAll(hand.sequenceList(Number.MIN_POWER, fieldPower - 1));
		}

		if (info.bind()) {
			List<CardHolder> choicesInBind = new ArrayList<>();
			for (CardHolder cardHolder : choices) {
				if (info.cardsInField().hasSameSuits(cardHolder)) {
					choicesInBind.add(cardHolder);
				}
			}
			return choicesInBind;
		}
		return choices;
	}

	private int evaluate(CardHolder cardHolder, boolean rev) {
		if (cardHolder.numOfCards() == 0) {
			return -10000;
		}

		// (numOfCards = 1): 3, 4, ..., 13, 1, 2
		//   :
		// (numOfCards = 4): 3, 4, ..., 13, 1, 2
		List<List<Integer>> evaluationTable = Arrays.asList(
				Arrays.asList(52, 51, 50, 49, 48, 7, 47, 46, 45, 31, 30, 29, 3)
				, Arrays.asList(44, 43, 42, 41, 40, 6, 34, 33, 32, 25, 24, 23, 2)
				, Arrays.asList(39, 38, 37, 36, 35, 5, 28, 27, 26, 22, 21, 20, 1)
				, Arrays.asList(8, 9, 10, 11, 12, 4, 13, 14, 15, 16, 17, 18, 19)
		);
		List<List<Integer>> evaluationTableRev = Arrays.asList(
				Arrays.asList(3, 26, 27, 44, 45, 7, 46, 47, 48, 49, 50, 51, 52)
				, Arrays.asList(2, 22, 23, 28, 29, 6, 37, 38, 39, 40, 41, 42, 43)
				, Arrays.asList(1, 20, 21, 24, 25, 5, 30, 31, 32, 33, 34, 35, 36)
				, Arrays.asList(19, 18, 17, 16, 15, 4, 14, 13, 12, 11, 10, 9, 8)
		);

		int value = 0;
		for (Number number : Number.values()) {
			int numOfCards = cardHolder.numOfCards(number);
			if (numOfCards >= 1) {
				value += (rev) ? evaluationTableRev.get(numOfCards - 1).get(number.power()) : evaluationTable.get(numOfCards - 1).get(number.power());
			}
		}
		return value;
	}

	public CardHolder play(CardHolder hand, Information info) {
		List<CardHolder> choices = choices(hand, info);
		CardHolder bestCards = new CardHolder();
		int value = 10001;

		for (CardHolder cardHolder : choices) {
			CardHolder handClone = hand.clone();
			handClone.subtract(cardHolder);
			boolean revolution = (cardHolder.numOfCards() >= 4) != info.revolution();
			int evaluation = evaluate(handClone, revolution);

			if (value > evaluation) {
				value = evaluation;
				bestCards = cardHolder;
			}
		}
		return bestCards;
	}
}
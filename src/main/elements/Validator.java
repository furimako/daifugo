package main.elements;

import main.elements.card.CardHolder;

public class Validator {
	public static boolean check(CardHolder cardHolder, Information info) {
		/* in case of pass */
		if (cardHolder.numOfCards() == 0) {
			if (info.cardsInField.numOfCards() != 0) {
				return true;
			}
			System.out.println("you cannot pass in your turn");
			return false;
		}

		/* in case of NOT pass */
		// hand check
		if (!cardHolder.isNoneSequence() && !cardHolder.isSequence()) {
			System.out.println("it's not a correct choice");
			return false;
		}

		//in case of NO card in field
		if (info.cardsInField.numOfCards() == 0) {
			return true;
		}

		//in case of unsortedCards in field
		if (info.bind) {
			if (!cardHolder.hasSameSuits(info.cardsInField)) {
				System.out.println("the field is bound!!!");
				return false;
			}
		}

		if (cardHolder.numOfCards() != info.cardsInField.numOfCards()) {
			return false;
		}

		if (cardHolder.isSequence() != info.cardsInField.isSequence()) {
			return false;
		}

		if (!info.revolution) {
			return cardHolder.highCard().power() > info.cardsInField.highCard().power();
		} else {
			return cardHolder.highCard().power() < info.cardsInField.highCard().power();
		}
	}
}
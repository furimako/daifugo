package checker;

import container.CardHolder;
import container.Information;

public class Validator {
	public static boolean check(CardHolder hand, CardHolder playingCards, Information info) {
		/* in case of pass */
		if (playingCards.numOfCards() == 0) {
			if (info.cardsInField().numOfCards() != 0) {
				return true;
			}
			System.out.println("you cannot pass in your turn");
			return false;
		}

		/* in case of NOT pass */
		// hand check
		if (!hand.contains(playingCards)) {
			System.out.println("you don't have the cards");
			return false;
		}

		if (!playingCards.isNoneSequence() && !playingCards.isSequence()) {
			System.out.println("it's not a correct choice");
			return false;
		}

		//in case of NO card in field
		if (info.cardsInField().numOfCards() == 0) {
			return true;
		}

		//in case of cards in field
		if (info.bind()) {
			if (!playingCards.hasSameSuits(info.cardsInField())) {
				System.out.println("the field is bound");
				return false;
			}
		}

		if (playingCards.numOfCards() != info.cardsInField().numOfCards()) {
			return false;
		}

		if (playingCards.isSequence() != info.cardsInField().isSequence()) {
			return false;
		}

		if (!info.revolution()) {
			return playingCards.highCard().power() > info.cardsInField().highCard().power();
		} else {
			return playingCards.highCard().power() < info.cardsInField().highCard().power();
		}
	}
}
package checker;

import container.Card;
import container.CardHolder;
import container.Information;
import enums.Number;
import enums.Suit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
	private Information info;
	private CardHolder hand;
	private CardHolder playingCards;

	@BeforeEach
	void setUp() {
		CardHolder cardsInField = new CardHolder();
		cardsInField.add(new Card(Suit.CLOVER, Number.SEVEN));
		cardsInField.add(new Card(Suit.DIAMOND, Number.SEVEN));
		CardHolder cardsPlayed = new CardHolder();
		boolean revolution = false;
		boolean bind = false;
		info = new Information(null, cardsInField, cardsPlayed, revolution, bind);

		hand = new CardHolder();
		hand.add(new Card(Suit.SPADE, Number.THREE));
		hand.add(new Card(Suit.DIAMOND, Number.THREE));
		hand.add(new Card(Suit.DIAMOND, Number.FOUR));
		hand.add(new Card(Suit.DIAMOND, Number.FIVE));
		hand.add(new Card(Suit.SPADE, Number.EIGHT));
		hand.add(new Card(Suit.SPADE, Number.NINE));
		hand.add(new Card(Suit.CLOVER, Number.NINE));
		hand.add(new Card(Suit.HEART, Number.NINE));
		hand.add(new Card(Suit.DIAMOND, Number.NINE));
		hand.add(new Card(Suit.SPADE, Number.TEN));
		hand.add(new Card(Suit.SPADE, Number.QUEEN));
		hand.add(new Card(Suit.SPADE, Number.KING));
		hand.add(new Card(Suit.SPADE, Number.ACE));
		hand.add(new Card(Suit.SPADE, Number.TWO));


		playingCards = new CardHolder();
	}

	@Test
	void passOK() {
		assertTrue(Validator.check(hand, playingCards, info));
	}

	@Test
	void passNG() {
		info.setCardsInField(new CardHolder());
		assertFalse(Validator.check(hand, playingCards, info));
	}

	@Test
	void noneSeqOK() {
		playingCards.add(new Card(Suit.SPADE, Number.NINE));
		playingCards.add(new Card(Suit.CLOVER, Number.NINE));
		assertTrue(Validator.check(hand, playingCards, info));
	}

	@Test
	void noneSeqWithInvalidHand() {
		playingCards.add(new Card(Suit.SPADE, Number.TEN));
		playingCards.add(new Card(Suit.CLOVER, Number.TEN));
		assertFalse(Validator.check(hand, playingCards, info));
	}

	@Test
	void noneSeqWithInvalidNumOfCards() {
		playingCards.add(new Card(Suit.SPADE, Number.NINE));
		playingCards.add(new Card(Suit.CLOVER, Number.NINE));
		playingCards.add(new Card(Suit.HEART, Number.NINE));
		assertFalse(Validator.check(hand, playingCards, info));
	}

	@Test
	void noneSeqWithInvalidPower() {
		playingCards.add(new Card(Suit.SPADE, Number.THREE));
		playingCards.add(new Card(Suit.DIAMOND, Number.THREE));
		assertFalse(Validator.check(hand, playingCards, info));
	}

	@Test
	void noneSeqWithInvalidType() {
		info.cardsInField().add(new Card(Suit.SPADE, Number.SEVEN));
		playingCards.add(new Card(Suit.SPADE, Number.EIGHT));
		playingCards.add(new Card(Suit.SPADE, Number.NINE));
		playingCards.add(new Card(Suit.SPADE, Number.TEN));
		assertFalse(Validator.check(hand, playingCards, info));
	}

	@Test
	void seqOK() {
		CardHolder cardsInField = new CardHolder();
		cardsInField.add(new Card(Suit.CLOVER, Number.FIVE));
		cardsInField.add(new Card(Suit.CLOVER, Number.SIX));
		cardsInField.add(new Card(Suit.CLOVER, Number.SEVEN));
		info.cardsInField().clear();
		info.cardsInField().add(cardsInField);
		playingCards.add(new Card(Suit.SPADE, Number.EIGHT));
		playingCards.add(new Card(Suit.SPADE, Number.NINE));
		playingCards.add(new Card(Suit.SPADE, Number.TEN));
		assertTrue(Validator.check(hand, playingCards, info));
	}

	@Test
	void seqWithInvalidHand() {
		CardHolder cardsInField = new CardHolder();
		cardsInField.add(new Card(Suit.CLOVER, Number.FIVE));
		cardsInField.add(new Card(Suit.CLOVER, Number.SIX));
		cardsInField.add(new Card(Suit.CLOVER, Number.SEVEN));
		info.cardsInField().clear();
		info.cardsInField().add(cardsInField);
		playingCards.add(new Card(Suit.SPADE, Number.NINE));
		playingCards.add(new Card(Suit.SPADE, Number.TEN));
		playingCards.add(new Card(Suit.SPADE, Number.JACK));
		assertFalse(Validator.check(hand, playingCards, info));
	}

	@Test
	void seqWithInvalidNumOfCards() {
		CardHolder cardsInField = new CardHolder();
		cardsInField.add(new Card(Suit.CLOVER, Number.FIVE));
		cardsInField.add(new Card(Suit.CLOVER, Number.SIX));
		cardsInField.add(new Card(Suit.CLOVER, Number.SEVEN));
		info.cardsInField().clear();
		info.cardsInField().add(cardsInField);
		playingCards.add(new Card(Suit.SPADE, Number.QUEEN));
		playingCards.add(new Card(Suit.SPADE, Number.KING));
		playingCards.add(new Card(Suit.SPADE, Number.ACE));
		playingCards.add(new Card(Suit.SPADE, Number.TWO));
		assertFalse(Validator.check(hand, playingCards, info));
	}

	@Test
	void seqWithInvalidPower() {
		CardHolder cardsInField = new CardHolder();
		cardsInField.add(new Card(Suit.CLOVER, Number.FIVE));
		cardsInField.add(new Card(Suit.CLOVER, Number.SIX));
		cardsInField.add(new Card(Suit.CLOVER, Number.SEVEN));
		info.cardsInField().clear();
		info.cardsInField().add(cardsInField);
		playingCards.add(new Card(Suit.DIAMOND, Number.THREE));
		playingCards.add(new Card(Suit.DIAMOND, Number.FOUR));
		playingCards.add(new Card(Suit.DIAMOND, Number.FIVE));
		assertFalse(Validator.check(hand, playingCards, info));

	}

	@Test
	void seqWithInvalidType() {
		CardHolder cardsInField = new CardHolder();
		cardsInField.add(new Card(Suit.CLOVER, Number.FIVE));
		cardsInField.add(new Card(Suit.CLOVER, Number.SIX));
		cardsInField.add(new Card(Suit.CLOVER, Number.SEVEN));
		info.cardsInField().clear();
		info.cardsInField().add(cardsInField);
		playingCards.add(new Card(Suit.SPADE, Number.NINE));
		playingCards.add(new Card(Suit.CLOVER, Number.NINE));
		playingCards.add(new Card(Suit.HEART, Number.NINE));
		assertFalse(Validator.check(hand, playingCards, info));
	}

	@Test
	void bindOK() {
		info.setBind(true);
		playingCards.add(new Card(Suit.DIAMOND, Number.NINE));
		playingCards.add(new Card(Suit.CLOVER, Number.NINE));
		assertTrue(Validator.check(hand, playingCards, info));
	}

	@Test
	void bindNG() {
		info.setBind(true);
		playingCards.add(new Card(Suit.DIAMOND, Number.NINE));
		playingCards.add(new Card(Suit.HEART, Number.NINE));
		assertFalse(Validator.check(hand, playingCards, info));
	}
}
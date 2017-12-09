package main.elements.card;

import java.util.*;

public class CardHolder {

	private List<Card> cards;

	public CardHolder() {
		cards = new ArrayList<>();
	}

	public List<Card> unsortedCards() {
		return cards;
	}

	private List<Card> cards() {
		Collections.sort(cards);
		return cards;
	}

	public Card highCard() {
		if (cards.size() == 0) {
			throw new IllegalStateException();
		}
		return cards().get(cards.size() - 1);
	}

	private Card lowCard() {
		if (cards.size() == 0) {
			throw new IllegalStateException();
		}
		return cards().get(0);
	}

	private Set<Suit> suits() {
		Set<Suit> suits = new TreeSet<>();
		for (Card card : cards) {
			suits.add(card.suit());
		}
		return suits;
	}

	public int numOfCards() {
		return cards.size();
	}

	public int numOfCards(Number number) {
		int count = 0;

		for (Card card : cards) {
			if (card.number() == number) {
				count++;
			}
		}
		return count;
	}

	public boolean isNoneSequence() {
		if (numOfCards() == 0) {
			return false;
		}

		Card card_tmp = cards.get(0);

		for (Card card : cards) {
			if (card_tmp.number() != card.number()) {
				return false;
			}
		}
		return true;
	}

	public boolean isSequence() {
		if (suits().size() != 1) {
			return false;
		}
		if (numOfCards() < 3) {
			return false;
		}
		for (Number number : Number.values()) {
			if (numOfCards(number) >= 2) {
				return false;
			}
		}
		if (numOfCards() != highCard().number().power() - lowCard().number().power() + 1) {
			return false;
		}
		return true;
	}

	private boolean hasSuit(Suit suit) {
		for (Card card : cards) {
			if (card.suit() == suit) {
				return true;
			}
		}
		return false;
	}

	public boolean hasSameSuits(CardHolder cardHolder) {
		for (Suit suit : Suit.values()) {
			if (this.hasSuit(suit) != cardHolder.hasSuit(suit)) {
				return false;
			}
		}
		return true;
	}

	public void add(Card card) {
		cards.add(card);
	}

	public void add(CardHolder cardHolder) {
		if (cardHolder != null) {
			cards.addAll(cardHolder.cards());
		}
	}

	public void subtract(CardHolder cardHolder) {
		if (!cards.containsAll(cardHolder.cards())) {
			throw new IllegalArgumentException();
		}

		if (cardHolder != null) {
			cards.removeAll(cardHolder.cards());
		}
	}

	private void swap(int i1, int i2) {
		Card card1 = cards.get(i1).clone();
		Card card2 = cards.get(i2).clone();
		cards.set(i1, card2);
		cards.set(i2, card1);
	}

	public void clear() {
		cards.clear();
	}

	public void shuffle() {
		int rand;
		for (int i = 0; i < cards.size(); i++) {
			rand = (int) (Math.random() * cards.size());
			swap(i, rand);
		}

	}

	public List<CardHolder> noneSequenceList(int minPower, int maxPower) {
		Map<Number, CardHolder> noneSequenceMap = new HashMap<>();
		CardHolder cardHolder;
		for (Card card : cards) {
			if (card.power() >= minPower && card.power() <= maxPower) {
				cardHolder = new CardHolder();
				cardHolder.add(card);
				cardHolder.add(noneSequenceMap.get(card.number()));
				noneSequenceMap.put(card.number(), cardHolder);
			}
		}
		return new ArrayList<>(noneSequenceMap.values());
	}

	public List<CardHolder> noneSequenceList(int minPower, int maxPower, int numOfCards) {
		List<CardHolder> noneSequenceList = new ArrayList<>();
		for (Number number : Number.values()) {
			if (number.power() >= minPower && number.power() <= maxPower) {
				if (numOfCards(number) == numOfCards) {
					noneSequenceList.addAll(noneSequenceList(number.power(), number.power()));
				}
			}
		}
		return noneSequenceList;
	}

	public List<CardHolder> sequenceList(int minPower, int maxPower) {
		List<CardHolder> sequenceList = new ArrayList<>();
		CardHolder cardHolder = new CardHolder();
		for (Card card : cards) {
			if (card.number() == Number.THREE || card.number() == Number.FOUR) {
				continue;
			}
			if (card.power() >= minPower && card.power() <= maxPower) {
				Card card1 = card.cloneDec();
				Card card2 = card1.cloneDec();

				if (cards.contains(card1) && cards.contains(card2)) {
					cardHolder.add(card);
					cardHolder.add(card1);
					cardHolder.add(card2);
				}
			}
		}
		return sequenceList;
	}

	@Override
	public CardHolder clone() {
		CardHolder cardHolder = new CardHolder();
		cardHolder.cards.addAll(cards);
		return cardHolder;
	}

	@Override
	public String toString() {
		String str = "";
		boolean first = true;
		for (Card card : cards()) {
			if (first) {
				str = card.toString();
				first = false;
			} else {
				str += ", " + card;
			}
		}
		if (cards.size() == 0) {
			str = "(no card)";
		}
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		CardHolder cardHolderObj = (CardHolder) obj;
		if (cards.size() != cardHolderObj.cards().size()) {
			return false;
		}

		List<Card> cards = cards();
		List<Card> cardsObj = cardHolderObj.cards();
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i) != cardsObj.get(i)) {
				return false;
			}
		}
		return true;
	}
}
package elements.card;

public class Card implements Comparable<Card> {
	private final Suit suit;
	private final Number number;

	public Card(Suit suit, Number number) {
		this.suit = suit;
		this.number = number;
	}

	@Override
	public Card clone(){
		return new Card(suit, number);
	}

	public Card cloneDec(){
		if(number == Number.THREE){
			throw new IllegalStateException();
		}
		return new Card(suit, Number.getInstanceFromPower(number.power()-1));
	}

	public Suit suit() {
		return suit;
	}


	public Number number() {
		return number;
	}

	public int power(){
		return number.power();
	}

	@Override
	public String toString() {
		return suit.toString() + number.toString();
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		Card cardObj = (Card) obj;
		if(suit != cardObj.suit()){
			return false;
		}
		if(number != cardObj.number()){
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(Card card) {
		return (this.number.power() != card.number().power()) ? this.number.power() - card.number().power() : this.suit.power() - card.suit().power();
	}
}
package enums;

public enum Suit {
	SPADE('S', 3),
	HEART('H', 2),
	DIAMOND('D', 1),
	CLOVER('C', 0);

	private final char name;
	private final int power;

	Suit(char name, int power) {
		this.name = name;
		this.power = power;
	}

	public int power() {
		return power;
	}

	public static Suit valueOf(char c){
		for (Suit suit:Suit.values()){
			if(c==suit.name){
				return suit;
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return String.valueOf(name);
	}
}

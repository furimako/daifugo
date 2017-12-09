package elements.card;

public enum Number {
	THREE(3, 0),
	FOUR(4, 1),
	FIVE(5, 2),
	SIX(6, 3),
	SEVEN(7, 4),
	EIGHT(8, 5),
	NINE(9, 6),
	TEN(10, 7),
	JACK(11, 8),
	QUEEN(12, 9),
	KING(13, 10),
	ACE(1, 11),
	TWO(2, 12);

	public static final int MAX_POWER = 12;
	public static final int MIN_POWER = 0;
	private final int number;
	private final int power;

	Number(int number, int power) {
		this.number = number;
		this.power = power;
	}

	public int power() {
		return power;
	}

	public static Number getInstance(int number) {
		for (Number num : Number.values()) {
			if (number == num.number) {
				return num;
			}
		}
		throw new IllegalArgumentException();
	}

	public static Number getInstanceFromPower(int power) {
		for (Number num : Number.values()) {
			if (power == num.power) {
				return num;
			}
		}
		throw new IllegalArgumentException();
	}

	@Override
	public String toString() {
		return String.valueOf(number);
	}
}

package game;

public class Cards {
	private int[][] table;
	
	/* constructor */
	public Cards() {
		table = new int[5][13];
	}
	
	/* setter */
	public void set(int i, int j, int num) {
		table[i][j] = num;
	}
	
	public void set(int[][] table) {
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=3; i++) {
				if (table[i][j] == 1) set(i, j, 1);
			}
		}
	}
	
	/* clear: clear all tables */
	public void clear() {
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=4; i++) {
				set(i, j, 0);
			}
		}
	}
	
	/* subtractCards: subtract parameter from Cards */
	public void subtractCards(int[][] table) {
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=3; i++) {
				if (table[i][j]==1) set(i, j, 0);
			}
		}
	}
	
	/* getter */
	public int[][] get() {
		return table;
	}
	
	public int get(int i, int j) {
		return table[i][j];
	}
	
	/* suit: get suits that Cards have */
	public int suit() {
		int ssss = 0;
		
		for (int i=0; i<=3; i++) {
			for (int j=0; j<=12; j++) {
				if (table[i][j] == 1) {
					switch (i) {
						case 0:
							ssss += 1000;
							break;
							
						case 1:
							ssss += 100;
							break;
							
						case 2:
							ssss += 10;
							break;
							
						case 3:
							ssss += 1;
							break;
							
						default:
							System.out.println("error Cards.suit");
							break;
					}
					break;
				}
			}
		}
		return ssss;
	}
	
	/* numOfCards: how many cards you have (in case of "seq" return "minus value") */
	public int numOfCards() {
		int sum = 0;
		int count = 0;
		
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=3; i++) {
				if (table[i][j] == 1) sum++;
			}
		}
		
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=3; i++) {
				if (table[i][j] == 1) {
					count++;
					break;
				}
			}
		}
		
		if (count >= 2) return -1 * sum;
		return sum;
	}
	
	public int numOfCards(int strength) {
		int sum = 0;
		
		for (int i=0; i<=3; i++) {
			if (table[i][strength] == 1) sum++;
		}
		
		return sum;
	}
	
	/* power: convert to power ("wrong cards" make 13) */
	public int power() {
		int power = 13;
		int count = 0;
		int suit = -1;
		
		//check each value of table (0, 1)
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=3; i++) {
				if ( table[i][j] < 0 ) return 13;
				if ( table[i][j] > 1 ) return 13;
			}
		}
		
		//in case of pass
		if (numOfCards() == 0) return -1;
		
		//in case of not seq
		if ( numOfCards() > 0 ) {
			for (int j=0; j<=12; j++) {
				for (int i=0; i<=3; i++) {
					if ( table[i][j] == 1 ) {
						count++;
						power = j;
						break;
					}
				}
			}
			
			if (count != 1) return 13;
			return power;
		}
		
		//in case of seq
		if (numOfCards() >= -2) return 13;
		
		for (int i=0; i<=3; i++) {
			for (int j=12; j>=0; j--) {
				if ( table[i][j] == 1 ) {
					count++;
					power = j;
					suit = i;
					break;
				}
			}
		}
		
		if (count != 1) return 13;
		
		for (int i=1; i<Math.abs( numOfCards() ); i++) {
			if (table[suit][ power-i ] != 1) return 13;
		}
		
		return power;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=3; i++) {
				if ( table[i][j] == 1 ) {
					if (!first) sb.append(", ");
					first = false;
					
					switch (j) {
						case 11:
							sb.append(1);
							break;
							
						case 12:
							sb.append(2);
							break;
							
						default:
							sb.append(j+3);
							break;
					}
					
					switch (i) {
						case 0:
							sb.append("s");
							break;
							
						case 1:
							sb.append("h");
							break;
							
						case 2:
							sb.append("d");
							break;
							
						case 3:
							sb.append("c");
							break;
							
						default:
							System.out.println("error Cards.toString");
							break;
					}
				}
			}
		}
		if (first) sb.append("(no card)");
		return sb.toString();
	}
}
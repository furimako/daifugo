package player;

import game.Cards;
import game.Field;

public class Evaluation implements Player {
	/* play method */
	public int[][] play(int[][] cardsTable, int[][] fieldTable) {
		Field field = new Field();
		field.set(fieldTable);
		int[][][] play = choices(cardsTable, fieldTable);
		int bestChoice = -1;
		int value = 10001;
		boolean nextRev;
		
		for (int i=0; i<play.length; i++) {
			if ( numOfCards(play[i])!=4 ) nextRev = field.rev();
			else nextRev = !field.rev();
			
			if (value > evaluate( subtract(cardsTable, play[i]), nextRev ) ) {
				value = evaluate( subtract(cardsTable, play[i]), nextRev );
				bestChoice = i;
			}
		}
		return play[bestChoice];
	}
	
	/* evaluate: evaluate the state */
	public int evaluate(int[][] table, boolean rev) {
		int value = 0;
		boolean violation = true;
		
		//is finished ?
		for (int i=0; i<=12; i++) {
			if (numOfCards(table, i) != 0) break;
			if (i==12) return -10000;
		}
		
		//is extra violation?
		for (int i=1; i<=4; i++) {
			if (numOfCards(table, i) != 0) {
				violation = false;
				break;
			}
		}
		if (violation) {
			for (int i=6; i<=11; i++) {
				if (numOfCards(table, i) != 0) violation = false;
			}
		}
		if (violation && (!rev) && numOfCards(table, 0)>=1) violation = false;
		if (violation && rev && numOfCards(table, 12)>=1) violation = false;
		if (violation) return 10000;
		
		//other situation
		int[][] eTable = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0}
			,{52,51,50,49,48,7,47,46,45,31,30,29,3}
			,{44,43,42,41,40,6,34,33,32,25,24,23,2}
			,{39,38,37,36,35,5,28,27,26,22,21,20,1}
			,{8,9,10,11,12,4,13,14,15,16,17,18,19}
		};
		
		int[][] eRevTable = {
			{0,0,0,0,0,0,0,0,0,0,0,0,0}
			,{3,26,27,44,45,7,46,47,48,49,50,51,52}
			,{2,22,23,28,29,6,37,38,39,40,41,42,43}
			,{1,20,21,24,25,5,30,31,32,33,34,35,36}
			,{19,18,17,16,15,4,14,13,12,11,10,9,8}
		};
		
		for (int i=0; i<=12; i++) {
			if (!rev) value += eTable[ numOfCards(table, i) ][i];
			
			//rev bonus here
			else value += eRevTable[ numOfCards(table, i) ][i] - 10;
		}
		
		return value;
	}
	
	/* numOfCards: how many cards you have (in case of "seq" return "minus value") */
	public int numOfCards(int[][] table) {
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
	
	public int numOfCards(int[][] table, int strength) {
		int sum = 0;
		
		for (int i=0; i<=3; i++) {
			if (table[i][strength] == 1) sum++;
		}
		return sum;
	}
	
	/* subtract: return table1 minus table2 */
	public int[][] subtract(int[][] table1, int[][] table2) {
		int[][] ans = new int[5][13];
		
		for (int i=0; i<=3; i++) {
			for (int j=0; j<=12; j++) {
				ans[i][j] = table1[i][j] - table2[i][j];
			}
		}
		return ans;
	}
	
	/* choices: return all possible choices */
	public int[][][] choices(int[][] cardsTable, int[][] fieldTable) {
		Cards cards = new Cards();
		Field field = new Field();
		cards.set(cardsTable);
		field.set(fieldTable);
		Cards fieldCards = new Cards();
		fieldCards.set(fieldTable);
		int turnPlayer = fieldTable[4][4];
		int power = fieldCards.power();
		int ssss = fieldCards.suit();
		int[] s = new int[4];
		s[0] = ssss/1000;
		s[1] = (ssss % 1000) / 100;
		s[2] = (ssss % 100) / 10;
		s[3] = ssss % 10;
		int count = 0;
		int countBuffer = 0;
		int num;
		
		if (fieldCards.numOfCards() == 0) {
			//count the number of choices
			for (int i=0; i<=12; i++) {
				if ( cards.numOfCards(i) >= 1 ) count++;
			}
			
			for (int i=0; i<=3; i++) {
				for (int j=0; j<=12; j++) {
					if (cards.get(i, j)==1) countBuffer++;
					if (cards.get(i, j)==0 || j==12) {
						if (countBuffer >= 3) count += (countBuffer-2) * (countBuffer-1) / 2;
						countBuffer = 0;
					}
				}
			}
			
			//make choices
			int[][][] choices = new int[count][5][13];
			countBuffer = count;
			count = -1;
			
			for (int j=0; j<=12; j++) {
				if ( cards.numOfCards(j) >= 1 ) {
					count++;
					for (int i=0; i<=3; i++) {
						choices[count][i][j] = cards.get(i, j);
					}
				}
			}
			
			for (int k=3; k<=13; k++) { 
				for (int i=0; i<=3; i++) {
					for (int j=0; j<=13-k; j++) {
						num = 0;
						for (int l=1; l<=k; l++) {
							num += cards.get(i, j+l-1);
						}
						
						if (num == k) {
							count++;
							for (int l=1; l<=k; l++) {
								choices[count][i][j+l-1] = 1;
							}
						}
					}
				}
			}
			
			if (countBuffer != count+1) System.out.println("error Data.choices");
			return choices;
		}
		
		if (field.rev() && power == 0) return new int[1][5][13];
		if ( ( !field.rev() ) && power == 12) return new int[1][5][13];
		
		count = 0;

		if (fieldCards.numOfCards() >= 1) {
			//count the number of choices
			do {
				if ( !field.rev() ) {
					power++;
					if ( cards.numOfCards(power) == fieldCards.numOfCards() ) {
						if ( !field.bind() ) count++;
						else {
							for (int i=0; i<=3; i++) {
								if ( cards.get(i, power) < s[i] ) break;
								if (i == 3) count++;
							}
						}
					}
				} else {
					power--;
					if ( cards.numOfCards(power) == fieldCards.numOfCards() ) {
						if ( !field.bind() ) count++;
						else {
							for (int i=0; i<=3; i++) {
								if ( cards.get(i, power) < s[i] ) break;
								if (i == 3) count++;
							}
						}
					}
				}
				
			} while(1<=power && power<=11);
			
			if (count == 0) return new int[1][5][13];
			
			count++;
			int[][][] choices = new int[count][5][13];
			countBuffer = count;
			count = 0;
			power = fieldCards.power();
			
			//make choices
			do {
				if ( !field.rev() ) {
					power++;
					if ( cards.numOfCards(power) == fieldCards.numOfCards() ) {
						if ( !field.bind() ) {
							count++;
							for (int i=0; i<=3; i++) {
								choices[count][i][power] = cards.get(i, power);
							}
						} else {
							for (int i=0; i<=3; i++) {
								if ( cards.get(i, power) < s[i] ) break;
								if (i == 3) {
									count++;
									for (int j=0; j<=3; j++) {
										choices[count][j][power] = cards.get(j, power);
									}
								}
							}
						}
					}
				} else {
					power--;
					if ( cards.numOfCards(power) == fieldCards.numOfCards() ) {
						if ( !field.bind() ) {
							count++;
							for (int i=0; i<=3; i++) {
								choices[count][i][power] = cards.get(i, power);
							}
						} else {
							for (int i=0; i<=3; i++) {
								if ( cards.get(i, power) < s[i] ) break;
								if (i == 3) {
									count++;
									for (int j=0; j<=3; j++) {
										choices[count][j][power] = cards.get(j, power);
									}
								}
							}
						}
					}
				}
				
			} while(1<=power && power<=11);
			
			if (countBuffer != count+1) System.out.println("error Data.choices");
			return choices;
		}
		
		return new int[1][5][13];
	}
	
}
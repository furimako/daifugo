package game;

public class Field {
	private int[][] table;
	
	/* constructor */
	public Field() {
		table = new int[5][13];
	}
	
	/* setter */
	public void set(int i, int j, int num) {
		table[i][j] = num;
	}
	
	public void set(int[][] table) {
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=4; i++) {
				set( i, j, table[i][j] );
			}
		}
	}
	
	/* setCards: setter */
	public void setCards(int[][] table) {
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=3; i++) {
				if (this.table[i][j] == 1) set(i, j, 2);
				if (table[i][j] == 1) set( i, j, 1);
			}
		}
	}
	
	public void setCards() {
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=3; i++) {
				if (table[i][j] == 1) set(i, j, 2);
			}
		}
	}
	
	/* setBind: setter */
	public void setBind(int bind) {
		set(4, 3, bind);
	}
	
	/* clear: clear all tables */
	public void clear() {
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=4; i++) {
				set(i, j, 0);
			}
		}
	}
	
	/* reverseRev: reverse revolution state */
	public void reverseRev() {
		if (table[4][2] == 0) set(4, 2, 1);
		else if (table[4][2] == 1) set(4, 2, 0);
		
		System.out.println("rev now");
	}
	
	/* setTurnPlayer: set next turn player */
	public void setTurnPlayer() {
		int player = getTurnPlayer();
		
		if ( (table[4][5]==0 || table[4][9]==0) && (table[4][6]==0 || table[4][10]==0) && (table[4][7]==0 || table[4][11]==0) && (table[4][8]==0 || table[4][12]==0) ) {
			for (int i=1; i<=4; i++) {
				player = (player+1) % 4;
				
				if ( table[4][player+9]!=0 ) {
					set(4, 4, player);
					break;
				}
			}
		} else {
			for (int i=1; i<=4; i++) {
				player = (player+1) % 4;
				
				if ( table[4][player+5]>=1 && table[4][player+9]!=0 ) {
					set(4, 4, player);
					break;
				}
			}
		}
	}
	
	/* setState: setter */
	public void setState(int turnPlayer, int num) {
		set(4, turnPlayer+5, num);
	}
	
	/* decNumOfCards: subtract parameter from NumOfCards */
	public void decNumOfCards(int turnPlayer, int num) {
		set(4, turnPlayer+9, table[4][turnPlayer+9]-num);
	}
	
	/* getter */
	public int[][] get() {
		return table;
	}
	
	public int get(int i, int j) {
		return table[i][j];
	}
	
	/* getTurnPlayer: getter */
	public int getTurnPlayer() {
		return table[4][4];
	}
	
	/* rev: getter */
	public boolean rev() {
		if (table[4][2] == 1) return true;
		return false;
	}
	
	/* bind: getter */
	public boolean bind() {
		if (table[4][3] == 1) return true;
		return false;
	}
	
	/* startGame: method used when the game start */
	public void startGame() {
		clear();
		int turnPlayer = (int)( 4 * Math.random() );
		set(4, 4, turnPlayer);
		set(4, 5, 2);
		set(4, 6, 2);
		set(4, 7, 2);
		set(4, 8, 2);
		set(4, 9, 13);
		set(4, 10, 13);
		set(4, 11, 13);
		set(4, 12, 13);
	}
	
	/* startTurn: method used when the turn is over */
	public void startTurn() {
		setCards();
		set(4, 3, 0);
		set(4, 5, 2);
		set(4, 6, 2);
		set(4, 7, 2);
		set(4, 8, 2);
		if ( table[4][9]==0 ) set(4, 5, 0);
		if ( table[4][10]==0 ) set(4, 6, 0);
		if ( table[4][11]==0 ) set(4, 7, 0);
		if ( table[4][12]==0 ) set(4, 8, 0);
	}
	
	/* isTurnEnd: judge whether all passes */
	public boolean isTurnEnd() {
		int count = 0;
		
		for (int i=5; i<=8; i++) {
			if ( table[4][i]==0 || table[4][i+4]==0 ) count++;
			if (table[4][i] == 2) return false;
		}
		if (count >= 3) return true;
		return false;
	}
	
	/* isGameEnd: judge whether the game is over */
	public boolean isGameEnd() {
		int count = 0;
		
		for (int i=9; i<=12; i++) {
			if ( table[4][i] == 0 ) count++;
		}
		
		if (count == 3) return true;
		return false;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("--- field ---");
		sb.append( "\n" );
		
		for (int i=0; i<=3; i++) {
			sb.append("P" + (i+1) + " have " + table[4][i+9] + " cards");
			if ( table[4][i+5] >= 1) sb.append( " (active)" );
			sb.append( "\n" );
		}
		
		sb.append( "\n" );
		sb.append("rev: ");
		sb.append( ( rev() ) ? "YES" : "NO" );
		sb.append( "\n" );
		
		sb.append("bind: ");
		sb.append( ( bind() ) ? "YES" : "NO" );
		sb.append( "\n" );
		
		Cards cards = new Cards();
		cards.set(table);
		sb.append("Cards in field: " + cards);
		sb.append( "\n" );
		
		return sb.toString();
	}
}
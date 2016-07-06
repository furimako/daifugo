package game;

class Data {
	private Cards[] cards;
	private Field field;
	private int rank;
	
	/* constructor */
	public Data() {
		cards = new Cards[4];
		cards[0] = new Cards();
		cards[1] = new Cards();
		cards[2] = new Cards();
		cards[3] = new Cards();
		field = new Field();
		rank = 0;
	}
	
	/* addRank: add rank when someone finished */
	public void addRank(int playerNum) {
		rank *= 10;
		rank += playerNum;
	}
	
	/* getter */
	public Cards getCards(int playerNum) {
		return cards[playerNum];
	}
	
	public Field getField() {
		return field;
	}
	
	/* startGame: deal Cards and set Field */
	public void startGame() {
		field.startGame();
		
		cards[0].clear();		
		cards[1].clear();		
		cards[2].clear();		
		cards[3].clear();
		
		int[] oneCard = new int[52];
		int oneCardBuffer;
		int ran;
		
		for (int i=0; i<=51; i++) {
			oneCard[i] = (i % 4);
		}
		
		for (int i=0; i<=51; i++) {
			ran = (int)(Math.random() * 52);
			oneCardBuffer = oneCard[i];
			oneCard[i] = oneCard[ran];
			oneCard[ran] = oneCardBuffer;
		}
		
		for (int i=0; i<=51; i++) {
			cards[ oneCard[i] ].set(i%4, i/4, 1);
		}
	}
	
	/* play: set Cards and Field with the Cards played */
	public void play(int turnPlayer, int[][] table) {
		Cards previousCards = new Cards();
		previousCards.set( field.get() );
		
		int ssss = previousCards.suit();
		
		Cards playedCards = new Cards();
		playedCards.set(table);
		
		System.out.println("P" + (turnPlayer + 1) + " played " + playedCards);
		
		//in case of not pass
		if ( playedCards.numOfCards() != 0 ) {
			//in case of 8
			if (playedCards.numOfCards()>0 && playedCards.power()==5 ) {
				cards[turnPlayer].subtractCards( playedCards.get() );
				if ( playedCards.numOfCards() >= 4 ) field.reverseRev();
				field.decNumOfCards( turnPlayer, playedCards.numOfCards() );
				
				if (field.get(4, turnPlayer+9)==0 ) {
					System.out.println("you can't finish with 8");
					System.exit(0);
				}
				
				field.startTurn();
				System.out.println("8 GIRI");
				
			} else {
				if (field.get(4, turnPlayer+9)==0 && ( ( playedCards.power()==12 && !field.rev() ) || ( playedCards.power()==12 && field.rev() ) ) ) {
					System.out.println("you are Daihinmin!!!");
				}
				
				//update cards
				cards[turnPlayer].subtractCards( playedCards.get() );
				
				//update cards of field
				field.setCards( playedCards.get() );
				
				//update rev
				if ( playedCards.numOfCards() >= 4 ) field.reverseRev();
				
				//update bind
				if ( playedCards.suit() == ssss ) field.setBind(1);
				
				//update turnPlayer
				field.setTurnPlayer();
				
				//update state
				field.setState(turnPlayer, 1);
			
				//update numOfCards
				field.decNumOfCards( turnPlayer, Math.abs( playedCards.numOfCards() ) );
				if (field.get(4, turnPlayer+9)==0 ) {
					addRank(turnPlayer);
					System.out.println("P" + (turnPlayer+1) + " is finished!!!!!!");
				}
			}
		//in case of pass
		} else {
			//update state
			field.setState(turnPlayer, 0);
			
			//update turnPlayer
			field.setTurnPlayer();
			
			//reset field if the turn end
			if ( field.isTurnEnd() ) field.startTurn();
		}
	}
	
	/* check: judge whether the played Cards are correct */
	public boolean check(Cards playedCards) {
		Cards fieldCards = new Cards();
		fieldCards.set( field.get() );
		int cardsPower = playedCards.power();
		int fieldPower = fieldCards.power();
		
		if (cardsPower == 13) return false;
		if (fieldPower == 13) System.out.println("error Data.check");
		
		//whether you have the cards
		for (int j=0; j<=12; j++) {
			for (int i=0; i<=3; i++) {
				if ( cards[field.getTurnPlayer()].get(i, j) < playedCards.get(i, j) ) return false;
			}
		}
		
		//in case of no card in field
		if ( fieldPower == -1 ) {
			if (cardsPower == -1) return false;
			return true;
		}
		
		//in case of cards in field
		if (cardsPower == -1) return true;
		if ( fieldCards.numOfCards() != playedCards.numOfCards() ) return false;
		
		//whether cards are stronger than field
		if ( !field.rev() ) {
			//in case of not rev
			if ( cardsPower <= fieldPower ) return false;
		} else {
			//in case of rev
			if ( cardsPower >= fieldPower ) return false;
		}
		
		//in case of bind
		if ( field.bind() )  {
			if ( playedCards.suit() != fieldCards.suit() ) return false;
		}
		return true;
	}
	
	/* showRank: show the ranks of all players */
	public void showRank() {
		addRank(6 - (rank/100) - ( (rank%100) / 10) - (rank%10) );
		StringBuffer sb = new StringBuffer("\n");
		
		for (int i=0; i<=3; i++) {
			switch (i) {
				case 0 :
					sb.append("1st");
					break;
					
				case 1 :
					sb.append("2nd");
					break;
					
				case 2 :
					sb.append("3rd");
					break;
					
				case 3 :
					sb.append("4th");
					break;
					
				default :
					System.out.println("error Data.showRank");
					break;
			}
			sb.append(" -> P" + ( (rank%10000) / 1000 + 1 ) + "\n");
			addRank(0);
		}
		System.out.println(sb);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer( "--- players' cards ---" );
		sb.append( "\n" );
		
		for (int i=0; i<=3; i++) {
			sb.append( "p" + i + "'s cards: " + getCards(i) );
			sb.append( "\n" );
		}
		sb.append(field);
		return sb.toString();
	}
}
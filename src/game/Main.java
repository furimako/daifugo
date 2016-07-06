package game;

import player.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean flag = false;
		boolean showMode = false;
		
		while (!flag) {
			System.out.print("show mode? 1:no 2: yes > ");
			
			try {
				switch ( br.readLine() ) {
					case "1" :
						showMode = false;
						flag = true;
						break;
						
					case "2" :
						showMode = true;
						flag = true;
						break;
						
					default :
						System.out.println("please enter again");
						break;
				}
			} catch ( IOException e ) {
				System.out.println("error Operation.whoPlays");
			}
		}
		
		Player[] p = new Player[4];
		p[0] = new Human();
		p[1] = new Evaluation();
		p[2] = new Evaluation();
		p[3] = new Evaluation();
		
		Data data = new Data();
		int turnPlayer;
		Cards cardsBuffer = new Cards();
		Field fieldBuffer = new Field();
		Cards cardsPlayed = new Cards();
		
		//initialize data
		data.startGame();
		
		//start new turn
		while (true) {
			turnPlayer = data.getField().getTurnPlayer();
			
			if (showMode && turnPlayer == 0) {
				System.out.print("\n");
				for (int i=0; i<=3; i++) {
					System.out.println( "P" + (i+1) + "'s hands: " + data.getCards(i) );
				}
			}
			
			while (true) {
				cardsBuffer.clear();
				cardsBuffer.set( data.getCards(turnPlayer).get() );
				fieldBuffer.clear();
				fieldBuffer.set( data.getField().get() );
				cardsPlayed.clear();
				cardsPlayed.set( p[turnPlayer].play( cardsBuffer.get(), fieldBuffer.get() ) );
				
				if ( data.check(cardsPlayed) ) break;
				System.out.println("please enter again");
			}
			
			data.play( turnPlayer, cardsPlayed.get() );
			
			if ( data.getField().isGameEnd() ) {
				data.showRank();
				break;
			}
		}
	}
}
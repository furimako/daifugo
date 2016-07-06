package player;

import game.Cards;
import game.Field;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Human implements Player {
	/* play method */
	public int[][] play(int[][] cardsTable, int[][] fieldTable) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		Cards cards = new Cards();
		Field field = new Field();
		cards.set(cardsTable);
		field.set(fieldTable);
		
		System.out.println("\n" + field + "your hands: " + cards);
		
		while (true) {
			System.out.print( "\n" + "enter the cards you want to play (ex.5d 6d 7d, pass, end, etc.)\n > " );
			
			try {
				str = br.readLine();
				System.out.print("\n");
				
				switch (str) {
					case "end":
						System.exit(0);
						
					case "pass":
						return new int[5][13];
						
					default:
						try {
							return strToCards(str);
							
						} catch (Exception e) {
							System.out.println("enter again");
							break;
						}
				}
			} catch (Exception e) {
				System.out.println("error Human.play: " + e);
			}
		}
	}
	
	/* strToCards: convert String to Cards */
	public int[][] strToCards(String str) {
		int[][] cards = new int[5][13];
		int num = -1;
		int suit = -1;
		String[] strArray = str.split(" ");
		
		for (int i=0; i<strArray.length; i++) {
			num = Integer.parseInt( strArray[i].substring(0, strArray[i].length()-1) );
			
			switch (num) {
				case 1:
					num = 11;
					break;
					
				case 2:
					num = 12;
					break;
					
				default:
					num -= 3;
					break;
			}
			
			switch ( strArray[i].charAt(strArray[i].length()-1) ) {
				case 's':
					suit = 0;
					break;
				
				case 'h':
					suit = 1;
					break;
				
				case 'd':
					suit = 2;
					break;
				
				case 'c':
					suit = 3;
					break;
				
				default:
					System.out.println("error Human.strToCards");
					System.exit(0);
					break;
			}
			cards[suit][num] = 1;
		}
		return cards;
	}
}
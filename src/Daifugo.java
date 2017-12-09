
import elements.Field;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class Daifugo {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean break_flag = false;
		boolean showMode = false;

		while (!break_flag) {
			System.out.print("\nshow mode? 1:no 2: yes > ");

			try {
				switch (br.readLine()) {
					case "1":
						showMode = false;
						break_flag = true;
						break;

					case "2":
						showMode = true;
						break_flag = true;
						break;

					default:
						System.out.println("please enter again");
						break;
				}
			} catch (IOException e) {
				System.out.println("error Operation.whoPlays");
			}
		}

		int num_of_players;
		while (true) {
			System.out.print("how many players(2 ~ 8) you need? > ");

			try {
				num_of_players = Integer.parseInt(br.readLine());
				if (num_of_players >= 2 && num_of_players <= 8) {
					break;
				}
				System.out.println("please enter the number (2 ~ 8)");

			} catch (Exception e) {
				System.out.println("please enter the number (2 ~ 8)");
			}
		}

		System.out.print("\n- LET THE DAIFUGO GAME BEGINS -\n");
		Field field = new Field(num_of_players, showMode);

		// start the game
		while (true) {
			field.play();
			if (field.isGameEnd()) {
				field.showRank();
				break;
			}
		}
	}
}
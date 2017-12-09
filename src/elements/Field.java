package elements;

import elements.card.Card;
import elements.card.CardHolder;
import elements.card.Number;
import elements.card.Suit;
import elements.player.Player;
import elements.player.PlayerType;
import elements.player.StateOfPlayer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Field {
	private List<Player> players;
	private List<Player> winners;

	protected CardHolder cardsInField;
	protected CardHolder cardsPlayed;
	protected boolean revolution;
	protected boolean bind;

	private final boolean showMode;

	Field(CardHolder cardsInField, CardHolder cardsPlayed, boolean revolution, boolean bind) {
		this.cardsInField = cardsInField;
		this.cardsPlayed = cardsPlayed;
		this.revolution = revolution;
		this.bind = bind;
		this.showMode = false;
	}

	public Field(int numOfPlayers, boolean showMode) {
		players = new ArrayList<>();
		winners = new ArrayList<>();
		players.add(new Player(PlayerType.HUMAN, "YOU"));

		for (int i = 0; i < numOfPlayers - 1; i++) {
			players.add(new Player(PlayerType.MACHINE, "COMPUTER" + (i + 1)));
		}

		players.get((int) (numOfPlayers * Math.random())).setState(StateOfPlayer.PLAYING);

		cardsInField = new CardHolder();
		cardsPlayed = new CardHolder();
		revolution = false;
		bind = false;

		// Deal sortedCards to players
		CardHolder deck = new CardHolder();
		for (Number number : Number.values()) {
			for (Suit suit : Suit.values()) {
				deck.add(new Card(suit, number));
			}
		}

		deck.shuffle();

		int swicher = -1;
		for (Card card : deck.unsortedCards()) {
			swicher = (swicher + 1) % numOfPlayers;
			players.get(swicher).hand().add(card);
		}

		this.showMode = showMode;
	}

	private Player playingPlayer() {
		for (Player player : players) {
			if (player.getState() == StateOfPlayer.PLAYING) {
				return player;
			}
		}
		throw new IllegalStateException();
	}

	private Player nextPlayer() {
		int index = -1;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getState() == StateOfPlayer.PLAYING) {
				index = i;
				break;
			}
		}

		for (int i = index + 1; i < players.size(); i++) {
			if (players.get(i).getState() == StateOfPlayer.ACTIVE) {
				return players.get(i);
			}
		}

		for (int i = 0; i < index; i++) {
			if (players.get(i).getState() == StateOfPlayer.ACTIVE) {
				return players.get(i);
			}
		}
		throw new IllegalStateException();
	}

	private Map<Player, Integer> playersInfo() {
		Map<Player, Integer> playersInfo = new LinkedHashMap<>();
		for (Player player : players) {
			playersInfo.put(player.playerInfo(), player.numOfCards());
		}
		return playersInfo;
	}

	private void switchRevolution() {
		revolution = !revolution;
	}

	private void startNewTrick() {
		bind = false;
		cardsPlayed.add(cardsInField);
		cardsInField.clear();


		for (Player player : players) {
			if (player.getState() == StateOfPlayer.PASSED) {
				player.setState(StateOfPlayer.ACTIVE);
			}
		}
	}

	/* isTurnEnd: judge whether all passes */
	private boolean isTrickEnd() {
		int count = 0;

		for (Player player : players) {
			switch (player.getState()) {
				case PLAYING:
					count++;
					break;
				case ACTIVE:
					count++;
					break;
				case PASSED:
					break;
				case FINISHED:
					break;
				default:
					break;
			}
		}
		if (count == 1) return true;
		return false;
	}

	public void play() {
		Player player = playingPlayer();
		if (showMode && player.playerType() == PlayerType.HUMAN) {
			System.out.println(this);
		}

		Information info;
		CardHolder playedCards;

		while (true) {
			info = new Information(playersInfo(), cardsInField, cardsPlayed, revolution, bind);
			playedCards = player.play(info);
			if (Validator.check(player.hand(), playedCards, info)) {
				break;
			}
			System.out.println("please enter again");
		}

		// Update Field
		CardHolder previousCards = cardsInField.clone();
		player.hand().subtract(playedCards);
		System.out.println(player + " played " + playedCards);

		if (playedCards.numOfCards() == 0) {
			//in case of pass
			nextPlayer().setState(StateOfPlayer.PLAYING);
			player.setState(StateOfPlayer.PASSED);

		} else {
			//in case of NOT pass
			cardsPlayed.add(cardsInField);
			cardsInField.clear();
			cardsInField.add(playedCards);

			if (playedCards.numOfCards() >= 4) {
				switchRevolution();
				System.out.println("Revoluiton!!");
			}

			if (playedCards.numOfCards(Number.EIGHT) >= 1) {
				//in case of 8
				System.out.println("Yagiri!");

				if (player.numOfCards() == 0) {
					nextPlayer().setState(StateOfPlayer.PLAYING);
					player.setState(StateOfPlayer.FINISHED);
					winners.add(player);
					System.out.println(player + " is finished!!!!!!");
				}

				for (Player player_ : players) {
					if (player_.getState() == StateOfPlayer.ACTIVE) {
						player_.setState(StateOfPlayer.PASSED);
					}
				}
			} else {
				// in case of NOT 8

				if (playedCards.hasSameSuits(previousCards)) {
					bind = true;
				}
				nextPlayer().setState(StateOfPlayer.PLAYING);

				if (player.numOfCards() == 0) {
					player.setState(StateOfPlayer.FINISHED);
					winners.add(player);
					System.out.println(player + " is finished!!!!!!");
				} else {
					player.setState(StateOfPlayer.ACTIVE);
				}
			}
		}
		if (isTrickEnd()) {
			startNewTrick();
		}
	}

	public boolean isGameEnd() {
		int count = 0;
		for (Player player : players) {
			if (player.getState() != StateOfPlayer.FINISHED) {
				count++;
			}
		}
		if (count == 1) {
			return true;
		}
		return false;
	}

	public void showRank() {
		for(Player player:players){
			if(player.getState() != StateOfPlayer.FINISHED){
				winners.add(player);
			}
		}

		String str = "";
		int rank = 0;
		for (Player player : winners) {
			rank++;
			switch (rank) {
				case 1:
					str += "1st";
					break;
				case 2:
					str += "2nd";
					break;
				case 3:
					str += "3rd";
					break;
				default:
					str += rank + "th";
					break;
			}
			str += " -> " + player + "\n";
		}
		System.out.println(str);
	}

	@Override
	public String toString() {
		String str = "\n----- show mode -----\n";

		for (Player player : players) {
			str += player + ": " + player.hand() + "\n";
		}
		str += "---------------------\n";
		return str;
	}
}
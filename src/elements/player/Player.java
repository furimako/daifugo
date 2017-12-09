package elements.player;

import algorithm.Algorithm;
import algorithm.Computer;
import algorithm.Human;
import elements.Information;
import elements.card.CardHolder;

public class Player {
	private final PlayerType playerType;
	private final String name;
	private final Algorithm algorithm;
	private CardHolder hand;
	private StateOfPlayer state;

	public Player(PlayerType playerType, String name) {
		this.playerType = playerType;

		switch (playerType) {
			case HUMAN:
				algorithm = new Human();
				break;
			case MACHINE:
				algorithm = new Computer();
				break;
			default:
				throw new IllegalArgumentException();
		}

		this.name = name;
		hand = new CardHolder();
		state = StateOfPlayer.ACTIVE;
	}

	private Player(String name) {
		this.name = name;
		playerType = null;
		algorithm = null;
	}

	public Player playerInfo() {
		Player playerInfo = new Player(name);
		playerInfo.state = state;
		return playerInfo;
	}

	public PlayerType playerType() {
		return playerType;
	}

	public CardHolder hand() {
		return hand;
	}

	public int numOfCards() {
		return hand.numOfCards();
	}

	public StateOfPlayer getState() {
		return state;
	}

	public void setState(StateOfPlayer state) {
		this.state = state;
	}

	public CardHolder play(Information info) {
		return algorithm.play(hand, info);
	}

	@Override
	public String toString() {
		return name;
	}
}
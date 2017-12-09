package main.algorithm;

import main.elements.Information;
import main.elements.card.CardHolder;

public interface Algorithm {
	CardHolder play(CardHolder hand, Information info);
}
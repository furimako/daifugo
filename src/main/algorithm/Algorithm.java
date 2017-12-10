package main.algorithm;

import main.container.Information;
import main.container.CardHolder;

public interface Algorithm {
	CardHolder play(CardHolder hand, Information info);
}
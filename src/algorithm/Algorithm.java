package algorithm;

import elements.Information;
import elements.card.CardHolder;

public interface Algorithm {
	CardHolder play(CardHolder hand, Information info);
}
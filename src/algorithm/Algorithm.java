package algorithm;

import container.Information;
import container.CardHolder;

public interface Algorithm {
	CardHolder play(CardHolder hand, Information info);
}
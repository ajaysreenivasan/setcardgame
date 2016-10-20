package setcardgame;

import java.util.Collection;
import java.util.List;

public class Player {
	private int mPlayerID;
	private int mScore;

	private List<Collection<Card>> mCollectedSets;

	public int getID() {
		return mPlayerID;
	}

	public int getScore() {
		return mScore;
	}

	public List<Collection<Card>> getCollectedSet() {
		return mCollectedSets;
	}
}

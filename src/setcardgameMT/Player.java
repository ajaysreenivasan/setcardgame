package setcardgameMT;

import java.util.Collection;
import java.util.List;

public class Player extends Thread {
	private int mPlayerID;
	private int mScore;

	private Game mGame;

	private List<Collection<Card>> mCollectedSets;

	public Player(int playerID, Game game) {
		mPlayerID = playerID;
		mGame = game;
	}

	public synchronized void run() {
		while (!mGame.isGameOver()) {
			System.out.println("I am player: " + mPlayerID);
		}
	}

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

package setcardgameMT;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
	private Table mTable;
	private List<Player> mPlayerList;

	private static int mPlayerCount;
	private static int mDeckSize;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter number of players: ");
		mPlayerCount = scanner.nextInt();

		System.out.println("Enter number of cards in deck, (-1 for standard deck): ");
		mDeckSize = scanner.nextInt();

		Game newGame = new Game();
		newGame.play();

		scanner.close();
	}

	public Game() {
		mPlayerList = new ArrayList<>();

		for (int i = 0; i < mPlayerCount; i++) {
			Player newPlayer = new Player(i, this);
			mPlayerList.add(newPlayer);
		}

		if (mDeckSize == -1) {
			mTable = new Table(mPlayerList);
		} else {
			mTable = new Table(mPlayerList, mDeckSize);
		}
	}

	public void play() {
		for (Player player : mPlayerList) {
			player.run();
		}
		// int turnCounter = 1;
		//
		// while (!mTable.isGameOver()) {
		// System.out.println("Turn: " + turnCounter);
		// turnCounter += 1;
		//
		// mTable.findAllSetsOnTable();
		// }
		//
		// mTable.printTable();
		//
		// mTable.printFoundSets();
	}

	public boolean isGameOver() {
		return mTable.isGameOver();
	}
}

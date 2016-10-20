package setcardgameMT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Table {
	private Deck mGameDeck;

	private boolean mSetsAvailable;

	private List<Player> mPlayerList;
	private List<Card> mCardsOnTable;
	private List<Set<Card>> mSetList;

	// used to check whether the conditions for
	// creating a set are met
	private HashMap<Card.Colour, HashSet<Integer>> mColourTable;
	private HashMap<Card.Shape, HashSet<Integer>> mShapeTable;
	private HashMap<Card.Shading, HashSet<Integer>> mShadingTable;
	private HashMap<Card.Number, HashSet<Integer>> mNumberTable;

	private final static int INIT_DRAW_SIZE = 12;
	private final static int NEXT_DRAW_SIZE = 3;

	// constructor if decksize is not specified
	public Table(List<Player> playerList) {
		mPlayerList = playerList;
		mCardsOnTable = new ArrayList<>();
		mSetList = new ArrayList<>();
		mSetsAvailable = true;

		mGameDeck = new Deck();

		initializeSetChecker();
		initializeTable();
	}

	// constructor if decksize is specified
	public Table(List<Player> playerList, int deckSize) {
		mPlayerList = playerList;
		mCardsOnTable = new ArrayList<>();
		mSetList = new ArrayList<>();
		mSetsAvailable = true;

		mGameDeck = new Deck(deckSize);

		initializeSetChecker();
		initializeTable();
	}

	// draws 12 cards from the deck onto the table
	private void initializeTable() {
		for (int i = 0; i < INIT_DRAW_SIZE; i++) {
			Card drawnCard = mGameDeck.drawCard();

			if (drawnCard != null) {
				mCardsOnTable.add(drawnCard);
			}
		}
	}

	private void initializeSetChecker() {
		mColourTable = new HashMap<>();
		for (Card.Colour colour : Card.Colour.values()) {
			HashSet<Integer> cardIDSet = new HashSet<>();
			mColourTable.put(colour, cardIDSet);
		}

		mShapeTable = new HashMap<>();
		for (Card.Shape shape : Card.Shape.values()) {
			HashSet<Integer> cardIDSet = new HashSet<>();
			mShapeTable.put(shape, cardIDSet);
		}

		mShadingTable = new HashMap<>();
		for (Card.Shading shading : Card.Shading.values()) {
			HashSet<Integer> cardIDSet = new HashSet<>();
			mShadingTable.put(shading, cardIDSet);
		}

		mNumberTable = new HashMap<>();
		for (Card.Number number : Card.Number.values()) {
			HashSet<Integer> cardIDSet = new HashSet<>();
			mNumberTable.put(number, cardIDSet);
		}
	}

	// submit three cards from the table to
	// check whether they make a set
	// if they do make a set, remove them from the table and
	// draw three additional cards
	public boolean submitSet(int cardIndex_first, int cardIndex_second, int cardIndex_third) {
		Card firstCard = mCardsOnTable.get(cardIndex_first);
		Card secondCard = mCardsOnTable.get(cardIndex_second);
		Card thirdCard = mCardsOnTable.get(cardIndex_third);

		int cardID_first = firstCard.getID();
		int cardID_second = secondCard.getID();
		int cardID_third = thirdCard.getID();

		if (isASet(firstCard, secondCard, thirdCard)) {
			removeSetFromTable(cardID_first, cardID_second, cardID_third);
			addCardsToTable();
			return true;
		} else {
			return false;
		}
	}

	// uses a table of sets to count the number of
	// cards with a given value for a parameter
	public boolean isASet(Card firstCard, Card secondCard, Card thirdCard) {
		resetChecker();

		mColourTable.get(firstCard.getColour()).add(firstCard.getID());
		mColourTable.get(secondCard.getColour()).add(secondCard.getID());
		mColourTable.get(thirdCard.getColour()).add(thirdCard.getID());

		mShapeTable.get(firstCard.getShape()).add(firstCard.getID());
		mShapeTable.get(secondCard.getShape()).add(secondCard.getID());
		mShapeTable.get(thirdCard.getShape()).add(thirdCard.getID());

		mShadingTable.get(firstCard.getShading()).add(firstCard.getID());
		mShadingTable.get(secondCard.getShading()).add(secondCard.getID());
		mShadingTable.get(thirdCard.getShading()).add(thirdCard.getID());

		mNumberTable.get(firstCard.getNumber()).add(firstCard.getID());
		mNumberTable.get(secondCard.getNumber()).add(secondCard.getID());
		mNumberTable.get(thirdCard.getNumber()).add(thirdCard.getID());

		for (Card.Colour colour : mColourTable.keySet()) {
			int colourCount = mColourTable.get(colour).size();
			if (colourCount != 0 && colourCount != 1 && colourCount != mColourTable.keySet().size()) {
				return false;
			}
		}

		for (Card.Shape shape : mShapeTable.keySet()) {
			int shapeCount = mShapeTable.get(shape).size();
			if (shapeCount != 0 && shapeCount != 1 && shapeCount != mShapeTable.keySet().size()) {
				return false;
			}
		}

		for (Card.Shading shading : mShadingTable.keySet()) {
			int shadingCount = mShadingTable.get(shading).size();
			if (shadingCount != 0 && shadingCount != 1 && shadingCount != mShadingTable.keySet().size()) {
				return false;
			}
		}

		for (Card.Number number : mNumberTable.keySet()) {
			int numberCount = mNumberTable.get(number).size();
			if (numberCount != 0 && numberCount != 1 && numberCount != mNumberTable.keySet().size()) {
				return false;
			}
		}

		return true;
	}

	private void resetChecker() {
		for (Card.Colour colour : mColourTable.keySet()) {
			mColourTable.get(colour).clear();
		}

		for (Card.Shape shape : mShapeTable.keySet()) {
			mShapeTable.get(shape).clear();
		}

		for (Card.Shading shading : mShadingTable.keySet()) {
			mShadingTable.get(shading).clear();
		}

		for (Card.Number number : mNumberTable.keySet()) {
			mNumberTable.get(number).clear();
		}
	}

	// removes the found set of cards from the cards
	// currently on the table
	private void removeSetFromTable(int cardID_first, int cardID_second, int cardID_third) {
		Set<Card> foundSet = new HashSet<>();
		Iterator<Card> cardsOnTableIterator = mCardsOnTable.listIterator();

		while (cardsOnTableIterator.hasNext()) {
			Card currentCard = cardsOnTableIterator.next();
			int currentCardID = currentCard.getID();

			if (currentCardID == cardID_first || currentCardID == cardID_second || currentCardID == cardID_third) {
				foundSet.add(currentCard);

				cardsOnTableIterator.remove();
			}
		}

		mSetList.add(foundSet);
	}

	private void addCardsToTable() {
		for (int i = 0; i < NEXT_DRAW_SIZE; i++) {
			Card newCard = mGameDeck.drawCard();
			if (newCard != null) {
				mCardsOnTable.add(newCard);
			}
		}
	}

	// iterates through all possible combinations
	// of cards on the table to find sets
	// restarts from the start of the list of cards
	// on the table when a set is found
	public void findAllSetsOnTable() {
		mSetsAvailable = true;

		boolean resetIndices = false;

		while (mSetsAvailable && mCardsOnTable.size() >= 3) {
			for (int i = 0; i < mCardsOnTable.size() - 2 && !resetIndices; i++) {
				for (int j = i + 1; j < mCardsOnTable.size() - 1 && !resetIndices; j++) {
					for (int k = j + 1; k < mCardsOnTable.size() && !resetIndices; k++) {
						if (submitSet(i, j, k)) {
							resetIndices = true;
						}
					}
				}
			}

			if (!resetIndices) {
				mSetsAvailable = false;
			}

			resetIndices = false;
		}
	}

	public boolean isSetAvailableOnTable() {
		boolean setFound = false;

		while (!setFound && mGameDeck.hasCardsRemaining()) {
			if (mCardsOnTable.size() >= 3) {
				for (int i = 0; i < mCardsOnTable.size() - 2 && !setFound; i++) {
					Card firstCard = mCardsOnTable.get(i);

					for (int j = i + 1; j < mCardsOnTable.size() - 1 && !setFound; j++) {
						Card secondCard = mCardsOnTable.get(j);

						for (int k = j + 1; k < mCardsOnTable.size() && !setFound; k++) {
							Card thirdCard = mCardsOnTable.get(k);

							if (isASet(firstCard, secondCard, thirdCard)) {
								setFound = true;
							}
						}
					}
				}
			}

			addCardsToTable();
		}

		if (setFound) {
			mSetsAvailable = true;
			return true;
		} else {
			mSetsAvailable = false;
			return false;
		}
	}

	// returns true when there are no cards left in the deck
	// and there are no sets remaining on the table
	public boolean isGameOver() {
		if (!isSetAvailableOnTable() && !mGameDeck.hasCardsRemaining())
			return true;
		else
			return false;
	}

	public Deck getDeck() {
		return mGameDeck;
	}

	public List<Player> getPlayerList() {
		return mPlayerList;
	}

	public List<Card> getCardsOnTable() {
		return mCardsOnTable;
	}

	public void printTable() {
		System.out.println("======================");
		System.out.println("Cards on Table: ");
		System.out.println("----------------------");

		for (int i = 0; i < mCardsOnTable.size(); i++) {
			System.out.println("==== Card #: " + i);
			System.out.println(mCardsOnTable.get(i));
		}

		System.out.println("======================");
	}

	public void printFoundSets() {
		System.out.println("======================");
		System.out.println("Sets Found: ");
		System.out.println("----------------------");

		for (Set<Card> foundSet : mSetList) {
			printSet(foundSet);
		}

		System.out.println("======================");
	}

	private void printSet(Set<Card> cardSet) {
		System.out.println("---- SET: ");
		for (Card card : cardSet) {
			System.out.println(card);
		}
		System.out.println("");
	}
}

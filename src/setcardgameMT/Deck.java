package setcardgameMT;

import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

// contains a collection of cards
// cards are drawn from this deck
// onto the table
public class Deck {
	int mDeckSize;
	Stack<Card> mCardStack;

	// constructor if the deck size is not specified
	public Deck() {
		mCardStack = new Stack<>();
		initializeStandardDeck();

		mDeckSize = mCardStack.size();

		shuffleDeck();
	}

	// if a size is specified
	// generate a random set of cards
	public Deck(int deckSize) {
		mCardStack = new Stack<>();

		mDeckSize = deckSize;

		initializeRandomDeck();
	}

	// initialize a deck with one of each card
	// ie. permutation of all the characteristics
	private void initializeStandardDeck() {
		int colourCount = Card.Colour.values().length;
		int shapeCount = Card.Shape.values().length;
		int shadingCount = Card.Shading.values().length;
		int numberCount = Card.Number.values().length;

		int cardID = 0;
		for (int i = 0; i < colourCount; i++) {
			Card.Colour cardColour = Card.Colour.values()[i];

			for (int j = 0; j < shapeCount; j++) {
				Card.Shape cardShape = Card.Shape.values()[j];

				for (int k = 0; k < shadingCount; k++) {
					Card.Shading cardShading = Card.Shading.values()[k];

					for (int l = 0; l < numberCount; l++) {
						Card.Number cardNumber = Card.Number.values()[l];

						Card newCard = new Card(cardID, cardColour, cardShape, cardShading, cardNumber);
						mCardStack.push(newCard);

						cardID += 1;
					}
				}
			}
		}
	}

	// randomizes the cards found in the deck
	// mainly used if the deck generated is "standard"
	// (ie. contains one card with
	// every possible permutation of parameters)
	private void shuffleDeck() {
		Card[] shuffledStack = new Card[mDeckSize];
		for (int i = 0; i < shuffledStack.length; i++) {
			shuffledStack[i] = null;
		}

		Iterator<Card> stackIterator = mCardStack.listIterator();

		Random randomizer = new Random();
		int newIndex = randomizer.nextInt(mDeckSize);
		while (stackIterator.hasNext()) {
			Card currentCard = stackIterator.next();

			while (shuffledStack[newIndex] != null) {
				newIndex = randomizer.nextInt(mDeckSize);
			}

			shuffledStack[newIndex] = currentCard;
		}

		mCardStack.clear();
		for (Card card : shuffledStack) {
			mCardStack.push(card);
		}
	}

	// randomize the cards in the deck
	private void initializeRandomDeck() {
		for (int i = 0; i < mDeckSize; i++) {
			Card newCard = new Card(i);
			mCardStack.push(newCard);
		}
	}

	// return the number of cards
	// remaining in the deck
	public int getDeckSize() {
		return mDeckSize;
	}

	public boolean hasCardsRemaining() {
		if (mCardStack.size() > 0) {
			return true;
		}

		return false;
	}

	// remove and return a card from
	// the top of the stack
	public Card drawCard() {
		Card drawnCard = null;

		if (hasCardsRemaining()) {
			drawnCard = mCardStack.pop();
		}

		return drawnCard;
	}
}

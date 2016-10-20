package setcardgame;

import java.util.Random;

// represents individual cards
// along with their characteristics
public class Card {
	enum Colour {
		RED, GREEN, PURPLE
	};

	enum Shape {
		DIAMOND, SQUIGGLE, OVAL
	};

	enum Shading {
		SOLID, EMPTY, STRIPED
	};

	enum Number {
		ONE, TWO, THREE
	};

	private int mID;
	private Colour mColour;
	private Shape mShape;
	private Shading mShading;
	private Number mNumber;

	public Card(int id) {
		mID = id;
		randomizeParameters();
	}

	public Card(int id, Colour colour, Shape shape, Shading shading, Number number) {
		mID = id;
		mColour = colour;
		mShape = shape;
		mShading = shading;
		mNumber = number;
	}

	private void randomizeParameters() {
		Random randomizer = new Random();
		mColour = Colour.values()[randomizer.nextInt(Colour.values().length)];
		mShape = Shape.values()[randomizer.nextInt(Shape.values().length)];
		mShading = Shading.values()[randomizer.nextInt(Shading.values().length)];
		mNumber = Number.values()[randomizer.nextInt(Number.values().length)];
	}

	public int getID() {
		return mID;
	}

	public Colour getColour() {
		return mColour;
	}

	public Shape getShape() {
		return mShape;
	}

	public Shading getShading() {
		return mShading;
	}

	public Number getNumber() {
		return mNumber;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID: ").append(mID).append("\n");
		sb.append("COLOUR: ").append(mColour).append("\n");
		sb.append("SHAPE: ").append(mShape).append("\n");
		sb.append("SHADING: ").append(mShading).append("\n");
		sb.append("NUMBER: ").append(mNumber).append("\n");

		return sb.toString();
	}
}

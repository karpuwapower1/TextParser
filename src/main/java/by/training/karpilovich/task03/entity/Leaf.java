package by.training.karpilovich.task03.entity;

import java.util.ArrayList;

import by.training.karpilovich.task03.entity.ChainParser.ParserType;

public class Leaf implements Component {

	private String symbol;
	private ParserType type;

	public Leaf(ParserType type, String symbol) {
		this.type = type;
		this.symbol = symbol;
	}

	public ParserType getType() {
		return type;
	}

	public String getSymbol() {
		return symbol;
	}

	public String get() {
		return symbol;
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public ArrayList<Component> getComponent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Component getChild(int index) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sort(ParserType type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void add(Component parse) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ChainParser getParser() {
		throw new UnsupportedOperationException();
	}

}

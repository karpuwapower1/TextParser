package by.training.karpilovich.task03.entity.composite;

import java.util.ArrayList;
import java.util.Comparator;

import by.training.karpilovich.task03.entity.ParserType;
import by.training.karpilovich.task03.util.parser.ChainParser;

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
	public int getComponentCount() {
		return 0;
	}

	@Override
	public ArrayList<Component> getComponent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sort(ParserType textPart, Comparator<Component> comparator) {
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

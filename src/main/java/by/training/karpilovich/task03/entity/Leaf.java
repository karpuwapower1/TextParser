package by.training.karpilovich.task03.entity;

import java.util.ArrayList;

import by.training.karpilovich.task03.entity.ChainParser.ParserType;

public class Leaf implements Component {
	
	private String symbol;
	
	public Leaf(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}

	@Override
	public void parse(String text) {
		throw new UnsupportedOperationException();
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
		return;
	}
	

}

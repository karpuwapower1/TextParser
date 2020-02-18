package by.training.karpilovich.task03.entity;

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
	

}

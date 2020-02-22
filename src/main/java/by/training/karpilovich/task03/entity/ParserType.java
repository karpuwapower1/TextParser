package by.training.karpilovich.task03.entity;

public enum ParserType {

	TEXT("[.|\\r|\\n|\\r\\n]*"), 
	PARAGRAPH("[^\\r\\n]+(\\r|\\n|\\r\\n)+"), 
	PHRASE("[^\\.;!\\?]+[\\.;!\\?]+"),
	LEXEME("[\\s][^\\s]+|[^\\s]+[\\s]"), 
	WORD("[.]*([^\\s]|[\\.;!\\?])"), 
	POLISH_NOTATION("  "),
	SYMBOL(".");

	private String regex;

	private ParserType(String regex) {
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}

}

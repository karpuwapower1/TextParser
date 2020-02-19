package by.training.karpilovich.task03.entity;

public enum TextPart {
	
	PARAGRAPH("[^\\r\\n]+(\\r|\\n|\\r\\n)+"), 
	PHRASE("[^\\.;!\\?]+[\\.;!\\?]+"), 
	LEXEME("[\\s][^\\s]+|[^\\s]+[\\s]"), 
	WORD("[\\w]*([^\\s]|[\\\\.;!\\\\?])"), 
	SYMBOL(".");
	
	private String regex;
	
	private TextPart(String regex) {
		this.regex = regex;
	}
	
	public String getRegex() {
		return regex;
	}
	
	public int getLength() {
		return TextPart.values().length;
	}
	
	public int getNumber() {
		return this.ordinal();
	}
}

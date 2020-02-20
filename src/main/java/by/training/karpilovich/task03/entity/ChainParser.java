package by.training.karpilovich.task03.entity;

public class ChainParser {

	private ChainParser next;
	private ParserType parser;

	public ChainParser(ParserType parser) {
		this.parser = parser;
	}

	public ParserType getParser() {
		return parser;
	}

	public void setParser(ParserType parser) {
		this.parser = parser;
	}

	public void setNext(ChainParser next) {
		this.next = next;
	}

	public ChainParser getNext() {
		return next;
	}

	public boolean hasNext() {
		return next != null;
	}

	public enum ParserType {

		PARAGRAPH("[^\\r\\n]+(\\r|\\n|\\r\\n)+"), PHRASE("[^\\.;!\\?]+[\\.;!\\?]+"),
		LEXEME("[\\s][^\\s]+|[^\\s]+[\\s]"), WORD("[\\w]*([^\\s]|[\\.;!\\?])"), SYMBOL(".");

		private String regex;

		private ParserType(String regex) {
			this.regex = regex;
		}

		public String getRegex() {
			return regex;
		}
	}

}

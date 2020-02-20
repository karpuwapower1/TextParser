package by.training.karpilovich.task03.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChainParser {
	
	Logger logger = LogManager.getLogger(ChainParser.class);

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

	public Component parse(String text) {
		Component component = new Composite(parser);
		Pattern pattern = Pattern.compile(parser.getRegex());
		Matcher matcher = pattern.matcher(text);
		if (next == null || text.length() == 1) {
			logger.debug(text + " " + parser.toString());
			component = new Leaf(text);
		} else {
			component = new Composite(parser);
			int start = 0;
			int end = 0;
			while (matcher.find()) {
				if (start != matcher.start()) {
					component.add(new Leaf(text.substring(start, matcher.start())));
				}
				logger.debug("FOUND " +  text + " " + parser.toString());
				component.add(next.parse(text.substring(matcher.start(), matcher.end())));
				start = end = matcher.end();
			}
			if (end != text.length()) {
				component.add(new Leaf(text.substring(end, text.length())));
			}
		}
		return component;
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

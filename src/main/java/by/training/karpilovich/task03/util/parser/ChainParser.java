package by.training.karpilovich.task03.util.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.task03.entity.ParserType;
import by.training.karpilovich.task03.entity.composite.Component;
import by.training.karpilovich.task03.entity.composite.Composite;
import by.training.karpilovich.task03.entity.composite.Leaf;
import by.training.karpilovich.task03.exception.ParserException;

public class ChainParser {

	private static final Logger LOGGER = LogManager.getLogger(ChainParser.class);

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

	public Component parse(String text) throws ParserException {
		if (text == null) {
			throw new ParserException("text is null");
		}
		Component component = new Composite(this);
		Pattern pattern = Pattern.compile(parser.getRegex());
		Matcher matcher = pattern.matcher(text);
		if (next == null) {
			while (matcher.find()) {
				LOGGER.debug(parser+ " " + text.substring(matcher.start(), matcher.end()));
				component.add(new Leaf(parser, text.substring(matcher.start(), matcher.end())));
			}
		} else {
			int start = 0;
			int end = 0;
			while (matcher.find()) {
				if (start != matcher.start()) {
					LOGGER.debug(next.getParser() +" " + text.substring(end, text.length()));
					component.add(next.parse(text.substring(start, matcher.start())));
				}
				LOGGER.debug(next.getParser() + " " + text.substring(end, text.length()));
				component.add(next.parse(text.substring(matcher.start(), matcher.end())));
				start = end = matcher.end();
			}
			if (end != text.length()) {
				LOGGER.debug(next.getParser() + " " + text.substring(end, text.length()));
				component.add(next.parse(text.substring(end, text.length())));
			}
		}
		return component;
	}

}

package by.training.karpilovich.task03.util.parser;

import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.task03.entity.ParserType;
import by.training.karpilovich.task03.entity.composite.Component;
import by.training.karpilovich.task03.entity.composite.Composite;
import by.training.karpilovich.task03.entity.composite.Leaf;
import by.training.karpilovich.task03.exception.IllegalMathematicExpressionException;

public class ChainPolishNotationParser extends ChainParser {

	Logger logger = LogManager.getLogger(ChainPolishNotationParser.class);
	private static String NUMBER_PATTERN = "[\\d]*";

	public ChainPolishNotationParser() {
		super(ParserType.POLISH_NOTATION);
	}

	public Component parse(String text) {
		ParserType parser = super.getParser();
		ChainParser next = super.getNext();
		Component component = new Composite(this);
		Pattern pattern = Pattern.compile(parser.getRegex());
		Matcher matcher = pattern.matcher(text);
		if (next == null) {
			while (matcher.find()) {
				component.add(new Leaf(parser, text.substring(matcher.start(), matcher.end())));
				logger.debug(text.substring(matcher.start(), matcher.end()) + " " + parser.toString());
			}
		} else {
			int start = 0;
			int end = 0;
			while (matcher.find()) {
				if (start != matcher.start()) {
					component.add(next.parse(text.substring(start, matcher.start())));
				}
				logger.debug(text.substring(matcher.start(), matcher.end()) + " " + parser.toString());
				component.add(next.parse(text.substring(matcher.start(), matcher.end())));

				start = end = matcher.end();
			}
			if (end != text.length()) {
				component.add(next.parse(text.substring(end, text.length())));
			}
		}
		return component;
	}

	public Queue<String> makePolishReverseNotation(String text) throws IllegalMathematicExpressionException {
		Queue<String> out = new LinkedList<String>();
		LinkedList<String> operation = new LinkedList<String>();
		Pattern pattern = Pattern.compile(NUMBER_PATTERN);
		Matcher matcher = pattern.matcher(text);
		String operator;
		int index = 0;
		while (index < text.length()) {
			logger.debug(text.charAt(index));
			if (matcher.find(index) && matcher.start() == index) {
				out.add(text.substring(matcher.start(), matcher.end()));
				index = index + matcher.end() - matcher.start();
				continue;
			}
			operator = String.valueOf(text.charAt(0));
			index += 1;
			if (operator.equals(OperationPriority.CLOSE_BRACKET.operation)) {
				while ((operator = operation.poll()) != null
						&& !operator.equals(OperationPriority.OPEN_BRACKET.operation)) {
					out.add(operator);
				}
				continue;
			}
			int priority = determineOperatorPriority(operator);
			int lastOperatorPriority = determineOperatorPriority(operation.peek());
			if (operation.peek().equals(OperationPriority.OPEN_BRACKET.operation) || priority > lastOperatorPriority) {
				operation.addFirst(operator);
			} else {
				do {
					out.add(operation.pop());
				} while (priority <= determineOperatorPriority(operation.peek()));
				operation.addFirst(operator);
			}
		}
		return out;
	}

	private int determineOperatorPriority(String operator) throws IllegalMathematicExpressionException {
		for (OperationPriority value : OperationPriority.values()) {
			if (value.getOperation().equals(operator))
				return value.getPriority();
		}
		throw new IllegalMathematicExpressionException();
	}

	private void isExpressionValid() {

	}

	enum OperationPriority {
		
		OPEN_BRACKET("(", 16), 
		CLOSE_BRACKET(")", 16), 
		UNARY_BITWISE_NOT("~", 14),

		LEFT_SHIFT("<<", 10), 
		RIGHT_SHIFT(">>", 10), 
		RIGHT_SHIFT_WITH_NO_EXTENSION(">>>", 10), 
		BITWISE_AND("&", 7),
		BITWISE_XOR("^", 6), 
		BITWISE_OR("|", 5);

		private String operation;
		private int priority;

		private OperationPriority(String operation, int priority) {
			this.operation = operation;
			this.priority = priority;
		}

		public String getOperation() {
			return operation;
		}

		public int getPriority() {
			return priority;
		}

	}

}

class TestPPP {
	
	private static final Logger logger = LogManager.getLogger(ChainPolishNotationParser.class);
	
	public static void main(String[] args) throws Exception {
		
		 ChainPolishNotationParser parser = new ChainPolishNotationParser();
		 String test = "5|(1&2&(3|(4&(6^5|6&47)|3)|2)|1)";
		 Queue<String> queue = parser.makePolishReverseNotation(test);
		 for (String str : queue) {
			 logger.debug(str);
		 }
	}
}

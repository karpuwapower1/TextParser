package by.training.karpilovich.task03.util.parser;

import java.util.ArrayDeque;
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
	private static final String NUMBER_REGEX = "[0-9]+";
	private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);

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

//	public Queue<String> makePolishReverseNotation(String text) throws IllegalMathematicExpressionException {
//		Queue<String> out = new LinkedList<String>();
//		LinkedList<String> operation = new LinkedList<String>();
//		String operator;
//		int index;
//		while (text.length() > 0) {
//			index = 0;
//			operator = "";
//			while (Character.isDigit(text.charAt(index))) {
//				operator += text.charAt(index++);
//			}
//			if (index != 0) {
//				out.add(operator);
//				text = text.substring(index);
//				continue;
//			}
//			operator = String.valueOf(text.charAt(index));
//			text = text.substring(index + 1);
//			if (operation.isEmpty()) {
//				operation.add(operator);
//				continue;
//			}
//			if (operator.equals(OperationPriority.CLOSE_BRACKET.operation)) {
//				while (!(operator = operation.poll()).equals(OperationPriority.OPEN_BRACKET.operation)) {
//					out.add(operator);
//				}
//				continue;
//			}
//			int priority = determineOperatorPriority(operator);
//			int lastOperatorPriority = determineOperatorPriority(operation.peek());
//			if (operation.peek().equals(OperationPriority.OPEN_BRACKET.operation) || priority > lastOperatorPriority) {
//				operation.addFirst(operator);
//			} else {
//				do {
//					out.add(operation.pop());
//				} while (priority <= determineOperatorPriority(operation.peek())
//						&& !operation.peek().equals(OperationPriority.OPEN_BRACKET.operation));
//				operation.addFirst(operator);
//			}
//		}
//		out.addAll(operation);
//		return out;
//	}
	
	private boolean isFirstSymbolDigit(String text) {
		Matcher matcher = NUMBER_PATTERN.matcher(text);
		return matcher.start() == 0;
	}
	
	private String takeFirstsymbolAsDigit(String text) {
		Matcher matcher = NUMBER_PATTERN.matcher(text);
		return text.substring(matcher.start() - matcher.end());
	}
	
	private String getFirstSymbolAsoperator(String text) {
		
	}
	
	
	public Queue<String> makePolishReverseNotation(String text) throws IllegalMathematicExpressionException {
		Queue<String> out = new LinkedList<String>();
		LinkedList<String> operation = new LinkedList<String>();
		
		String operator;
		while (text.length() > 0) {
			
			if (isFirstSymbolDigit(text)) {
				operator = takeFirstsymbolAsDigit(text);
				out.add(operator);
				text= text.substring(operator.length());
				logger.debug("digit + " + digit);
				continue;
			} else if (matcher.find()) {
				operator = text.substring(0, matcher.start());
				text = text.substring(matcher.start());
				logger.debug("operation before digit " + text.substring(matcher.start(), matcher.end()));
			} else {
				operator = String.valueOf(text.charAt(0));
				text = text.substring(1);
			}
			if (operation.isEmpty()) {
				operation.add(operator);
				continue;
			}
			if (operator.equals(OperationPriority.CLOSE_BRACKET.operation)) {
				while (!(operator = operation.poll()).equals(OperationPriority.OPEN_BRACKET.operation)) {
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
				} while (priority <= determineOperatorPriority(operation.peek())
						&& !operation.peek().equals(OperationPriority.OPEN_BRACKET.operation));
				operation.addFirst(operator);
			}
		}
		out.addAll(operation);
		return out;
	}

	public int countPolishNotation(Queue<String> notation) {
		ArrayDeque<Integer> values = new ArrayDeque<>();
		while (!notation.isEmpty()) {
			try {
				while (true) {
					values.addFirst(Integer.parseInt(notation.peek()));
					notation.poll();
				}
			} catch (NumberFormatException e) {
				// do nothing because operation
			}
			String operation = notation.poll();
			if (operation.equals(OperationPriority.UNARY_BITWISE_NOT.getOperation())) {
				values.addFirst(count(values.poll(), 0, operation));
			} else {
				values.addFirst(count(values.poll(), values.poll(), operation));
			}
		}
		return values.poll();
	}

	private int count(int value1, int value2, String operation) {
		int result = 0;
		if (operation.equals(OperationPriority.UNARY_BITWISE_NOT.getOperation())) {
			result = ~value1;
		} else if (operation.equals(OperationPriority.LEFT_SHIFT.getOperation())) {
			result = value1 << value2;
		} else if (operation.equals(OperationPriority.RIGHT_SHIFT.getOperation())) {
			result = value1 >> value2;
		} else if (operation.equals(OperationPriority.RIGHT_SHIFT_WITH_NO_EXTENSION.getOperation())) {
			result = value1 >>> value2;
		} else if (operation.equals(OperationPriority.BITWISE_AND.getOperation())) {
			result = value1 & value2;
		} else if (operation.equals(OperationPriority.BITWISE_XOR.getOperation())) {
			result = value1 ^ value2;
		} else if (operation.equals(OperationPriority.BITWISE_OR.getOperation())) {
			result = value1 | value2;
		}
		return result;
	}

	/*
	 * RIGHT_SHIFT_WITH_NO_EXTENSION(">>>", 10), BITWISE_AND("&", 7),
	 * BITWISE_XOR("^", 6), BITWISE_OR("|", 5);
	 */

	private int determineOperatorPriority(String operator) throws IllegalMathematicExpressionException {
		for (OperationPriority value : OperationPriority.values()) {
			if (value.getOperation().equals(operator))
				return value.getPriority();
		}

		logger.warn("IllegalMathematicExpressionException  operator= " + operator);
		throw new IllegalMathematicExpressionException();
	}

	private void isExpressionValid() {

	}

	enum OperationPriority {

		OPEN_BRACKET("(", 16), CLOSE_BRACKET(")", 16), UNARY_BITWISE_NOT("~", 14), LEFT_SHIFT("<<", 10),
		RIGHT_SHIFT(">>", 10), RIGHT_SHIFT_WITH_NO_EXTENSION(">>>", 10), BITWISE_AND("&", 7), BITWISE_XOR("^", 6),
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
		String test = "(~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78";
		Queue<String> queue = parser.makePolishReverseNotation(test);
		String polish = "";
		for (String str : queue) {
			polish += str;
		}
		logger.debug(polish);
		logger.debug(parser.countPolishNotation(queue));
		logger.debug((~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78 );
		System.out.println((~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78 );
	}
}

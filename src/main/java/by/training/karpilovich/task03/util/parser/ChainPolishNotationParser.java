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

	private static final Logger LOGGER = LogManager.getLogger(ChainPolishNotationParser.class);
	private static final String NUMBER_REGEX = "[\\d]+";
	private static final Pattern NUMBER_PATTERN = Pattern.compile(NUMBER_REGEX);
	private static final String GREATER = ">";
	private static final String LESS = "<";

	public ChainPolishNotationParser() {
		super(ParserType.POLISH_NOTATION);
	}
	
	/*
	 * Finds an arithmetic expressions in the text, counts their values 
	 * and changes them to that result. Result text is passing into next 
	 *  parser or an leaf is created, if next parser is null;
	 */

	public Component parse(String text) {
		ParserType parser = super.getParser();
		ChainParser next = super.getNext();
		Component component = new Composite(this);
		Pattern pattern = Pattern.compile(parser.getRegex());
		Matcher matcher = pattern.matcher(text);
		int index = 0;
		while (matcher.find(index)) {
			try {
				Queue<String> queue = makePolishReverseNotation(text.substring(matcher.start(), matcher.end()));
				String result = String.valueOf(countPolishNotation(queue));
				text = text.substring(0, matcher.start()) + result + text.substring(matcher.end());
				index = matcher.start() + result.length();
				matcher = pattern.matcher(text);
			} catch (IllegalMathematicExpressionException e) {
				// expression isn't valid, so index moves in the matcher's end and finding continuing
				index = matcher.end();
			}
		}
		if (next == null) {
			component.add(new Leaf(parser, text));
		} else {
			component.add(next.parse(text));
		}
		return component;
	}

	private Queue<String> makePolishReverseNotation(String text) throws IllegalMathematicExpressionException {
		Queue<String> out = new LinkedList<String>();
		LinkedList<String> operation = new LinkedList<String>();
		String operator;
		while (text.length() > 0) {
			if (isFirstSymbolDigit(text)) {
				operator = takeDigit(text);
				out.add(operator);
				text = text.substring(operator.length());
				continue;
			}
			operator = takeOperator(text);
			text = text.substring(operator.length());
			if (operation.isEmpty()) {
				operation.add(operator);
				continue;
			}
			if (operator.equals(OperationAndPriority.CLOSE_BRACKET.operation)) {
				while (!(operator = operation.poll()).equals(OperationAndPriority.OPEN_BRACKET.operation)) {
					out.add(operator);
				}
				continue;
			}
			int priority = determineOperatorPriority(operator);
			int lastOperatorPriority = determineOperatorPriority(operation.peek());
			if (operation.peek().equals(OperationAndPriority.OPEN_BRACKET.operation)
					|| priority > lastOperatorPriority) {
				operation.addFirst(operator);
			} else {
				do {
					out.add(operation.pop());
				} while (!operation.isEmpty() && priority <= determineOperatorPriority(operation.peek())
						&& !operation.peek().equals(OperationAndPriority.OPEN_BRACKET.operation));
				operation.addFirst(operator);
			}
		}
		out.addAll(operation);
		return out;
	}

	private int countPolishNotation(Queue<String> notation) {
		ArrayDeque<Integer> values = new ArrayDeque<>();
		while (!notation.isEmpty()) {
			try {
				while (true) {
					values.addFirst(Integer.parseInt(notation.peek()));
					notation.poll();
				}
			} catch (NumberFormatException e) {
				// do nothing because value is an operation
			}
			String operation = notation.poll();
			if (operation.equals(OperationAndPriority.UNARY_BITWISE_NOT.getOperation())) {
				values.addFirst(count(values.poll(), 0, operation));
			} else {
				values.addFirst(count(values.poll(), values.poll(), operation));
			}
		}
		return values.poll();
	}

	private boolean isFirstSymbolDigit(String text) {
		Matcher matcher = NUMBER_PATTERN.matcher(text);
		return (matcher.find() && matcher.start() == 0);
	}

	private String takeDigit(String text) {
		Matcher matcher = NUMBER_PATTERN.matcher(text);
		matcher.find();
		return text.substring(matcher.start(), matcher.end());
	}

	private String takeOperator(String text) {
		int index = 0;
		String operator = String.valueOf(text.charAt(index++));
		while (text.length() > index && (operator.equals(GREATER) || operator.equals(LESS))) {
			operator += String.valueOf(text.charAt(index++)); 
		}
		return operator;
	}

	private int count(int value1, int value2, String operation) {
		int result = 0;
		if (operation.equals(OperationAndPriority.UNARY_BITWISE_NOT.getOperation())) {
			result = ~value1;
		} else if (operation.equals(OperationAndPriority.LEFT_SHIFT.getOperation())) {
			result = value1 << value2;
		} else if (operation.equals(OperationAndPriority.RIGHT_SHIFT.getOperation())) {
			result = value1 >> value2;
		} else if (operation.equals(OperationAndPriority.RIGHT_SHIFT_WITH_NO_EXTENSION.getOperation())) {
			result = value1 >>> value2;
		} else if (operation.equals(OperationAndPriority.BITWISE_AND.getOperation())) {
			result = value1 & value2;
		} else if (operation.equals(OperationAndPriority.BITWISE_XOR.getOperation())) {
			result = value1 ^ value2;
		} else if (operation.equals(OperationAndPriority.BITWISE_OR.getOperation())) {
			result = value1 | value2;
		}
		return result;
	}

	private int determineOperatorPriority(String operator) throws IllegalMathematicExpressionException {
		for (OperationAndPriority value : OperationAndPriority.values()) {
			if (value.getOperation().equals(operator))
				return value.getPriority();
		}
		logger.warn("IllegalMathematicExpressionException  operator= " + operator);
		throw new IllegalMathematicExpressionException();
	}

	enum OperationAndPriority {

		OPEN_BRACKET("(", 16), CLOSE_BRACKET(")", 16), UNARY_BITWISE_NOT("~", 14), LEFT_SHIFT("<<", 10),
		RIGHT_SHIFT(">>", 10), RIGHT_SHIFT_WITH_NO_EXTENSION(">>>", 10), BITWISE_AND("&", 7), BITWISE_XOR("^", 6),
		BITWISE_OR("|", 5);

		private String operation;
		private int priority;

		private OperationAndPriority(String operation, int priority) {
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

//class TestPPP {
//
//	private static final Logger logger = LogManager.getLogger(ChainPolishNotationParser.class);
//
//	public static void main(String[] args) throws Exception {
//
//		ChainPolishNotationParser parser = new ChainPolishNotationParser();
//		String test = "~6&9|(3&4)";
//		logger.debug(~6&9|(3&4) );
//		Queue<String> queue = parser.makePolishReverseNotation(test);
//		String polish = "";
//		for (String str : queue) {
//			polish += str;
//		}
//		logger.debug(polish);
//		logger.debug(parser.countPolishNotation(queue));
//		logger.debug(~6&9|(3&4) );
//
//
////		String text = "It has survived - not only (five) centuries, but also the leap into 13<<2 electronic type setting, remaining 3>>5 essentially ~6&9|(3&4) unchanged. It was popularised in the 5|(1&2&(3|(4&(6^5|6&47)|3)|2)|1) with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\r\n"
////				+ "	It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using (~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78 Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using (Content here), content here', making it look like readable English.\r\n"
////				+ "	It is a  (4^5|1&2<<(2|5>>2&71))|1200 established -9 fact that a 6+ reader will be of a page when looking at its layout.\r\n"
////				+ "	Bye.\r\n";
////
////		Pattern pattern = Pattern.compile("[[\\d]*[%&()*+-/<=>^\\|~&&[^,.]]+[\\d]]{2,}");
////		Matcher matcher = pattern.matcher(text);
////		while (matcher.find()) {
////			logger.debug(text.substring(matcher.start(), matcher.end()));
////		}
//	}
//}

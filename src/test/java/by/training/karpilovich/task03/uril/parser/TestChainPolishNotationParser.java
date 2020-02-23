package by.training.karpilovich.task03.uril.parser;

import org.junit.Assert;
import org.junit.Test;

import by.training.karpilovich.task03.entity.composite.Component;
import by.training.karpilovich.task03.util.parser.ChainPolishNotationParser;

public class TestChainPolishNotationParser {
	
	@Test
	public void testParse() {
		String expression = "(4^5|1&2<<(2|5>>2&71))|1200";
		Component component = new ChainPolishNotationParser().parse(expression);
		String expected = String.valueOf((4^5|1&2<<(2|5>>2&71))|1200);
		String actual = component.getText();
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testParseIllegalExpression() {
		String expression = "(4^5|1&2<<(2|5>>2&71))))|1200";
		Component component = new ChainPolishNotationParser().parse(expression);
		String expected = expression;
		String actual = component.getText();
		Assert.assertEquals(expected, actual);
	}


}

/*
 * 
 * public Component parse(String text) {
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
				// expression isn't valid, so index moves in the matcher's end and searching continue
				LOGGER.warn(text.substring(matcher.start(), matcher.end()) + "isn't valid expression");
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
			//parses digit as long as operator isn't found and adds it into out stack
			if (isFirstSymbolDigit(text)) {
				operator = takeDigit(text);
				out.add(operator);
				text = text.substring(operator.length());
				continue;
			}
			operator = takeOperator(text);
			text = text.substring(operator.length());
			//is operation stack is empty, operation is pushed into it
			if (operation.isEmpty()) {
				operation.add(operator);
				continue;
			}
			//if operator is ')' all operators before '(' are pushed into out stack;
			if (operator.equals(OperationAndPriority.CLOSE_BRACKET.operation)) {
				while (!(operator = operation.poll()).equals(OperationAndPriority.OPEN_BRACKET.operation)) {
					out.add(operator);
				}
				continue;
			}
			int priority = determineOperatorPriority(operator);
			int lastOperatorPriority = determineOperatorPriority(operation.peek());
			
			// pop operators from operation stack while priority less than operator's priority
			// and adds them into out stack
			// adds operator into operations anyway
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

	private enum OperationAndPriority {

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
 */

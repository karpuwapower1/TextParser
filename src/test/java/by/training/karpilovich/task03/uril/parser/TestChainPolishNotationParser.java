package by.training.karpilovich.task03.uril.parser;

import org.junit.Assert;
import org.junit.Test;

import by.training.karpilovich.task03.entity.composite.Component;
import by.training.karpilovich.task03.exception.ParserException;
import by.training.karpilovich.task03.util.parser.ChainPolishNotationParser;

public class TestChainPolishNotationParser {

	@Test
	public void testParse() throws ParserException {
		String expression = "(4^5|1&2<<(2|5>>2&71))|1200";
		Component component = new ChainPolishNotationParser().parse(expression);
		String expected = String.valueOf((4 ^ 5 | 1 & 2 << (2 | 5 >> 2 & 71)) | 1200);
		String actual = component.getText();
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testParseIllegalExpression() throws ParserException {
		String expression = "(4^5|1&2<<(2|5>>2&71))))|1200";
		Component component = new ChainPolishNotationParser().parse(expression);
		String expected = expression;
		String actual = component.getText();
		Assert.assertEquals(expected, actual);
	}

	@Test(expected = ParserException.class)
	public void testParseNullText() throws ParserException {
		String expression = null;
		Component component = new ChainPolishNotationParser().parse(expression);
		String expected = expression;
		String actual = component.getText();
		Assert.assertEquals(expected, actual);
	}
}

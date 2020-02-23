package by.training.karpilovich.task03.uril.parser;

import org.junit.Assert;
import org.junit.Test;

import by.training.karpilovich.task03.entity.ParserType;
import by.training.karpilovich.task03.entity.composite.Component;
import by.training.karpilovich.task03.exception.ParserException;
import by.training.karpilovich.task03.util.parser.ChainParser;

public class TestChainParser {

	@Test(expected = ParserException.class)
	public void testParseNullText() throws ParserException {
		String text = null;
		ChainParser paragraph = new ChainParser(ParserType.PARAGRAPH);
		Component component = paragraph.parse(text);
		Assert.assertEquals(2, component.getComponentCount());
	}

	@Test
	public void testParseParagraph() throws ParserException {
		String text = "	It has survived - not only (five) centuries. \r\n"
				+ "	It is a long established fact that a reader will. \r\n";
		ChainParser paragraph = new ChainParser(ParserType.PARAGRAPH);
		Component component = paragraph.parse(text);
		Assert.assertEquals(2, component.getComponent().size());
	}

	@Test
	public void testParsePhrase() throws ParserException {
		String t = "It has survived.";
		ChainParser phrase = new ChainParser(ParserType.SYMBOL);
		Component component = phrase.parse(t);
		Assert.assertEquals(t.length(), component.getComponent().size());
	}

	@Test
	public void testParseWord() throws ParserException {
		String text = "	It has survived - not only (five) centuries."
				+ "	It is a long established fact that a reader will.";
		ChainParser word = new ChainParser(ParserType.PHRASE);
		Component component = word.parse(text);
		Assert.assertEquals(2, component.getComponent().size());
	}

	@Test
	public void testParseLexeme() throws ParserException {
		String t = " It has survived.";
		ChainParser lexeme = new ChainParser(ParserType.LEXEME);
		Component component = lexeme.parse(t);
		Assert.assertEquals(3, component.getComponent().size());
	}

	@Test
	public void testParseSymbol() throws ParserException {
		String t = "It has survived.";
		ChainParser symbol = new ChainParser(ParserType.SYMBOL);
		Component component = symbol.parse(t);
		Assert.assertEquals(t.length(), component.getComponent().size());
	}

}

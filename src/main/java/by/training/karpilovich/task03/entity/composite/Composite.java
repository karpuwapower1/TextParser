package by.training.karpilovich.task03.entity.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.task03.entity.ParserType;
import by.training.karpilovich.task03.util.comparator.ComponentByTypeComparator;
import by.training.karpilovich.task03.util.parser.ChainParser;
import by.training.karpilovich.task03.util.parser.ChainPolishNotationParser;

public class Composite implements Component {

	private static final Logger LOGGER = LogManager.getLogger(Composite.class);

	private ArrayList<Component> components = new ArrayList<>();
	private ChainParser parser;

	public Composite(ChainParser parser) {
		this.parser = parser;
	}

	@Override
	public void add(Component component) {
		components.add(component);
	}

	@Override
	public int getComponentCount() {
		return components.size();
	}

	@Override
	public ArrayList<Component> getComponent() {
		return components;
	}

	@Override
	public ChainParser getParser() {
		return parser;
	}

	@Override
	public String get() {
		String str = "";
		for (Component component : components) {
			str += component.get();
		}
		return str;
	}

	@Override
	public void sort(ParserType textPart, Comparator<Component> comparator) {
		if (this.parser.getNext().getParser() == textPart) {
			Collections.sort(components, comparator);
			return;
		}
		for (Component component : components) {
			try {
				component.sort(textPart, comparator);
			} catch (UnsupportedOperationException e) {
// 				do nothing because it's a leaf
			}
		}
	}
}

class Testt {
	private static final Logger LOGGER = LogManager.getLogger(Testt.class);

	public static void main(String[] args) {
		String textt = "It has survived - not only (five) centuries, but also the leap into 13<<2 electronic type setting, remaining 3>>5 essentially ~6&9|(3&4) unchanged. "
				+ "It was popularised in the 5|(1&2&(3|(4&(6^5|6&47)|3)|2)|1) with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\r\n"
				+ "	It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. "
				+ "The point of using (~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78 Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using (Content here), content here', making it look like readable English.\r\n"
				+ "	It is a  (4^5|1&2<<(2|5>>2&71))|1200 established fact that a 6 reader will be of a page when looking at its layout.\r\n"
				+ "	Bye.\r\n";
		
		
		/*
		 * It has survived - not only (five) centuries, but also the leap into 16384 electronic type setting, remaining 0 essentially 9 unchanged. 
		 * It was popularised in the 5 with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.	
		 * It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. 
		 * The point of using 78 Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using (Content here), content here', making it look like readable English.	
		 * It is a  1201 established fact that a 6 reader will be of a page when looking at its layout.	
		 * Bye.
		 */
		ChainParser text = new ChainParser(ParserType.TEXT);
		ChainParser paragraph = new ChainParser(ParserType.PARAGRAPH);
		ChainParser phrase = new ChainParser(ParserType.PHRASE);
		ChainParser pol = new ChainPolishNotationParser();
		ChainParser word = new ChainParser(ParserType.WORD);
		ChainParser symbol = new ChainParser(ParserType.SYMBOL);

		text.setNext(paragraph);
		paragraph.setNext(phrase);
		phrase.setNext(pol);
		pol.setNext(word);
		word.setNext(symbol);

		Component component = paragraph.parse(textt);

		LOGGER.debug(component.get());
		Comparator<Component> comparator = new ComponentByTypeComparator(ParserType.SYMBOL);
		component.sort(ParserType.PHRASE, comparator);
		LOGGER.debug(component.get());
	}
}

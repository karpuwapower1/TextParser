package by.training.karpilovich.task03.entity.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.task03.entity.ParserType;
import by.training.karpilovich.task03.util.comparator.ComponentByTypeComparator;
import by.training.karpilovich.task03.util.parser.ChainParser;

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
	public int getCount() {
		return components.size();
	}

	@Override
	public ArrayList<Component> getComponent() {
		return components;
	}

	@Override
	public Component getChild(int index) {
		return components.get(index);
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
			LOGGER.debug("sort " + this.parser.getParser() + get());
			LOGGER.debug("size " + getCount());
			Collections.sort(components, comparator);
			LOGGER.debug("AFTER sort " +  get());
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
		String textt = new String(
						  " first 3 3. second 2. third.\r\n"
						+ "	forth.\r\n"
						+ "	fifthhh tr tr t trr. sixth trtrtrt.\r\n ");
		ChainParser text = new ChainParser(ParserType.TEXT);
		ChainParser paragraph = new ChainParser(ParserType.PARAGRAPH);
		ChainParser phrase = new ChainParser(ParserType.PHRASE);
		ChainParser word = new ChainParser(ParserType.WORD);
		ChainParser symbol = new ChainParser(ParserType.SYMBOL);

		text.setNext(paragraph);
		paragraph.setNext(phrase);
		phrase.setNext(word);
		word.setNext(symbol);

		Component component = paragraph.parse(textt);

		LOGGER.debug(component.get());
		Comparator<Component> comparator = new ComponentByTypeComparator(ParserType.WORD);
		component.sort(ParserType.PHRASE, comparator);

		LOGGER.debug("SORT\n\n\n\n" + component.get());
	}
}

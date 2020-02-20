package by.training.karpilovich.task03.entity;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.task03.entity.ChainParser.ParserType;

public class Composite implements Component {

	private static final Logger LOGGER = LogManager.getLogger(Composite.class);

	private ArrayList<Component> components = new ArrayList<>();
	private ParserType type;

	public Composite(ParserType type) {
		this.type = type;
	}

	public void add(Component component) {
		components.add(component);
	}

	public String get() {
		String str = "";
		for (Component component : components) {
			str += component.get();
		}
		return str;
	}

	public int getCount() {
		return components.size();
	}

	public ArrayList<Component> getComponent() {
		return components;
	}

	public Component getChild(int index) {
		return components.get(index);
	}

	@Override
	public void sort(ParserType type) {
		
	}

//	@Override
//	public void sort(ParserType type) {
//		if (!parser.hasNext()) {
////			LOGGER.debug("parser ordinal = " + parser.ordinal() + " " + parser.toString() + " this..." + this.parser.ordinal() 
////			+ "   " + this.parser.toString());
//			return;
//		}
//		if (this.parser.getParser() == type) {
//			Collections.sort(components, new ComponentByTypeComparator());
//			return;
//		}
//		for (Component component : components) {
//			component.sort(type);
//		}
//	}

}

class Testt {
	private static final Logger LOGGER = LogManager.getLogger(Testt.class);

	public static void main(String[] args) {
		String text = new String(
				"It has survived - not only (five) centuries, but also the leap into 13<<2 electronic type setting, remaining 3>>5 essentially ~6&9|(3&4) unchanged. It was popularised in the 5|(1&2&(3|(4&(6^5|6&47)|3)|2)|1) with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.\r\n"
						+ "	It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using (~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78 Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using (Content here), content here', making it look like readable English.\r\n"
						+ "	It is a (4^5|1&2<<(2|5>>2&71))|1200 established fact that a reader will be of a page when looking at its layout.\r\n"
						+ "	Bye.\r\n ");
		ChainParser paragraph = new ChainParser(ParserType.PARAGRAPH);
		ChainParser phrase = new ChainParser(ParserType.PHRASE);
		ChainParser lexeme = new ChainParser(ParserType.LEXEME);
		ChainParser word = new ChainParser(ParserType.WORD);
		ChainParser symbol = new ChainParser(ParserType.SYMBOL);

		paragraph.setNext(phrase);
		phrase.setNext(lexeme);
		lexeme.setNext(word);
		word.setNext(symbol);
		
		Component component = paragraph.parse(text);

		LOGGER.debug(component.get());
	}
}

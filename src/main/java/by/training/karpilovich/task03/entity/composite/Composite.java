package by.training.karpilovich.task03.entity.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.task03.entity.ParserType;
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
	public String getText() {
		String str = "";
		for (Component component : components) {
			str += component.getText();
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
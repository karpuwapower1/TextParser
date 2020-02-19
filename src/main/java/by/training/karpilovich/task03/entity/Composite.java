package by.training.karpilovich.task03.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.task03.service.ComponentByTypeComparator;

public class Composite implements Component {

	private static final Logger LOGGER = LogManager.getLogger(Composite.class);

	private ArrayList<Component> components = new ArrayList<>();
	private TextPart part;
	Pattern pattern;

	public Composite(TextPart part) {
		this.part = part;
		pattern = Pattern.compile(part.getRegex());
	}

	@Override
	public void parse(String text) {
		Matcher matcher = pattern.matcher(text);
		int end = text.length();
		int start = 0;
		if (part.ordinal() == TextPart.values().length - 1 || text.length() == 1) {
			while (matcher.find()) {
				addLeafIfMatchingNotStartsAtFirstIndex(text, start, matcher.start());
				components.add(new Leaf(text.substring(matcher.start(), matcher.end())));
				LOGGER.debug(
						text.substring(matcher.start(), matcher.end()) + " " + part.toString() + " " + part.getRegex());
				end = matcher.end();
				start = matcher.end();
			}
		} else {
			TextPart nextPart = TextPart.values()[part.ordinal() + 1];
			Component component = new Composite(nextPart);
			while (matcher.find()) {
				addLeafIfMatchingNotStartsAtFirstIndex(text, start, matcher.start());
				LOGGER.debug(
						text.substring(matcher.start(), matcher.end()) + " " + part.toString() + " " + part.getRegex());
				component.parse(text.substring(matcher.start(), matcher.end()));
				start = matcher.end();
				end = matcher.end();
			}
			components.add(component);
		}
		addLeafIfMatchigNotEndAtLastIndex(text, end);
	}

	private void addLeafIfMatchingNotStartsAtFirstIndex(String text, int start, int matcherStart) {
		if (start != matcherStart) {
			components.add(new Leaf(text.substring(start, matcherStart)));
			LOGGER.debug(text.substring(start, matcherStart) + " " + part.toString() + " leaf");
		}
	}

	private void addLeafIfMatchigNotEndAtLastIndex(String text, int end) {
		if (end != text.length()) {
			components.add(new Leaf(text.substring(end)));
			LOGGER.debug(text.substring(end) + " " + part.toString() + " leaf");
		}
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

	public TextPart getPart() {
		return part;
	}

	public ArrayList<Component> getComponent() {
		return components;
	}

	public Component getChild(int index) {
		return components.get(index);
	}

	@Override
	public void sort(TextPart part) {
		if (part.ordinal() < this.part.ordinal()) {
//			LOGGER.debug("part ordinal = " + part.ordinal() + " " + part.toString() + " this..." + this.part.ordinal() 
//			+ "   " + this.part.toString());
			return;
		}
		if (this.part.ordinal() == part.ordinal()) {
			Collections.sort(components, new ComponentByTypeComparator());
			return;
		}
		for (Component component : components) {
			LOGGER.debug(component.getPart());
			component.sort(part);
		}
	}

}

class Testt {
	private static final Logger LOGGER = LogManager.getLogger(Testt.class);

	public static void main(String[] args) {
		String text = new String(
				" London is a capital of GB.\n Yeap, I know, my english is perfect.\n  My name is Alex. I am from Minsk? bla-bla-bla-.\n ");
		Composite composite = new Composite(TextPart.PARAGRAPH);
		composite.parse(text);
		LOGGER.debug(composite.get());
		composite.sort(TextPart.WORD);
		LOGGER.debug(composite.get());
	}
}

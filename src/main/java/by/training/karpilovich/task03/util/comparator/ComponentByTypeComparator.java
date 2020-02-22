package by.training.karpilovich.task03.util.comparator;

import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.task03.entity.ParserType;
import by.training.karpilovich.task03.entity.composite.Component;

public class ComponentByTypeComparator implements Comparator<Component> {
	
	private static final Logger LOGGER = LogManager.getLogger(ComponentByTypeComparator.class);
	
	private ParserType type;
	
	public ComponentByTypeComparator(ParserType type) {
		this.type = type;
	}

	@Override
	public int compare(Component o1, Component o2) {
		return countComponentWithType(o1, type) - countComponentWithType(o2, type);
	}
	
	private int countComponentWithType(Component components, ParserType type) {
		int typeCount = 0;
		for (Component component : components.getComponent()) {
			if (component.getParser().getParser() == type) {
				typeCount++;
			}
			try {
				typeCount += countComponentWithType(component, type);
			} catch (UnsupportedOperationException e) {
				// do nothing because it's a leaf
			}
		}
		return typeCount;
	}
}

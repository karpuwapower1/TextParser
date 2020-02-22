package by.training.karpilovich.task03.service;

import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.task03.entity.ChainParser.ParserType;
import by.training.karpilovich.task03.entity.Component;

public class ComponentByTypeComparator implements Comparator<Component> {
	
	Logger log = LogManager.getLogger(ComponentByTypeComparator.class);
	
	private ParserType type;
	
	public ComponentByTypeComparator(ParserType type) {
		this.type = type;
	}

	@Override
	public int compare(Component o1, Component o2) {
		log.debug(o1.get() + " " + o2.get());
		log.debug(o1.getCount() + " " + o2.getCount());
		return o1.getCount() -  o2.getCount();
//		return countComponentWithType(o1, type) - countComponentWithType(o2, type);
	}
	
//	private int countComponentWithType(Component components, ParserType type) {
//		int typeCount = 0;
//		for (Component component : components.getComponent()) {
//			if (component.getParser().getParser() == type) {
//				typeCount++;
//			}
//			try {
//				typeCount += countComponentWithType(component, type);
//			} catch (UnsupportedOperationException e) {
//				
//			}
//		}
//		
//		log.debug("typecount " + typeCount);
//		return typeCount;
//	}
}

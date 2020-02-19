package by.training.karpilovich.task03.service;

import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.task03.entity.Component;

public class ComponentByTypeComparator implements Comparator<Component> {
	
	Logger log = LogManager.getLogger(ComponentByTypeComparator.class);

	@Override
	public int compare(Component o1, Component o2) {
		log.debug(o1.get() + " " + o2.get());
		log.debug(o1.getCount() + " " + o2.getCount());
		return o2.getCount() - o1.getCount();
	}
}

//package by.training.karpilovich.task03.service;
//
//import java.util.Collections;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import by.training.karpilovich.task03.entity.Component;
//import by.training.karpilovich.task03.entity.Composite;
//import by.training.karpilovich.task03.entity.TextPart;
//
//public class SortingService {
//	
//	Logger log = LogManager.getLogger(SortingService.class);
//	
//	public void sort(Composite composite, TextPart type) {
//		for (Component component : composite.getComponent()) {
//			log.debug("Component type " + component.getPart());
//			if (component.getPart() == type) {
//				Collections.sort(component.getComponent(), new ComponentByTypeComparator());
//				log.debug("sort");
//			}
//		}
//	}
//}

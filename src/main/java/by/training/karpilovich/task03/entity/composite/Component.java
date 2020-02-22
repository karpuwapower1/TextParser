package by.training.karpilovich.task03.entity.composite;

import java.util.Comparator;
import java.util.List;

import by.training.karpilovich.task03.entity.ParserType;
import by.training.karpilovich.task03.util.parser.ChainParser;

public interface Component {
	String get();

	int getComponentCount();

	List<Component> getComponent();

	void add(Component parse);

	ChainParser getParser();
	
	void sort(ParserType textPart, Comparator<Component> comparator);

}
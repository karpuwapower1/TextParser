package by.training.karpilovich.task03.entity;

import java.util.List;

import by.training.karpilovich.task03.entity.ChainParser.ParserType;

public interface Component {
	String get();
	int getCount();
	List<Component> getComponent();
	Component getChild(int index);
	void sort(ParserType type);
	void add(Component parse);
	ChainParser getParser();

}
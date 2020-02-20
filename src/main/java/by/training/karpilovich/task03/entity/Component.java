package by.training.karpilovich.task03.entity;

import java.util.ArrayList;

import by.training.karpilovich.task03.entity.ChainParser.ParserType;

public interface Component {
	String get();
	int getCount();
	ArrayList<Component> getComponent();
	Component getChild(int index);
	void sort(ParserType type);
	void add(Component parse);

}

package by.training.karpilovich.task03.entity;

import java.util.ArrayList;

import by.training.karpilovich.task03.entity.ChainParser.ParserType;

public interface Component {
	void parse(String text);
	String get();
	int getCount();
	ArrayList<Component> getComponent();
	Component getChild(int index);
	void sort(ParserType type);

}

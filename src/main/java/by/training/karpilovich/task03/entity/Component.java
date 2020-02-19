package by.training.karpilovich.task03.entity;

import java.util.ArrayList;

public interface Component {
	void parse(String text);
	String get();
	TextPart getPart();
	int getCount();
	ArrayList<Component> getComponent();
	Component getChild(int index);
	void sort(TextPart part);

}

package by.training.karpilovich.task03.reader;

import by.training.karpilovich.task03.exception.ReaderException;

public interface Reader {

	String readText() throws ReaderException;

}

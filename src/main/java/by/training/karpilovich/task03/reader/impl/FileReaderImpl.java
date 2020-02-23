package by.training.karpilovich.task03.reader.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.karpilovich.task03.exception.ReaderException;
import by.training.karpilovich.task03.reader.Reader;

public class FileReaderImpl implements Reader {

	private static final Logger LOGGER = LogManager.getLogger(FileReaderImpl.class);

	private File file;

	public FileReaderImpl(File file) {
		this.file = file;
	}

	@Override
	public String readText() throws ReaderException {
		String text = new String();
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = reader.readLine()) != null) {
				text += line;
			}
		} catch (IOException e) {
			LOGGER.error("Exception occures while text is reading");
			throw new ReaderException(e);
		}
		return text;
	}

}

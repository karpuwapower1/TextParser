package by.training.karpilovich.task03.exception;

public class ParserException extends Exception {

	public ParserException() {
		super();
	}

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParserException(String message) {
		super(message);
	}

	public ParserException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = 1L;

}

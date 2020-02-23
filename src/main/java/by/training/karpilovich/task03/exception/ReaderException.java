package by.training.karpilovich.task03.exception;

public class ReaderException extends Exception {

	private static final long serialVersionUID = 1L;

	public ReaderException() {
		super();
	}

	public ReaderException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReaderException(String message) {
		super(message);
	}

	public ReaderException(Throwable cause) {
		super(cause);
	}

}

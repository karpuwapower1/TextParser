package by.training.karpilovich.task03.exception;

public class IllegalMathematicExpressionException extends Exception {

	private static final long serialVersionUID = 1L;

	public IllegalMathematicExpressionException() {
		super();
	}

	public IllegalMathematicExpressionException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalMathematicExpressionException(String message) {
		super(message);
	}

	public IllegalMathematicExpressionException(Throwable cause) {
		super(cause);
	}

}

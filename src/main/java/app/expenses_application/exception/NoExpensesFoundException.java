package app.expenses_application.exception;

public class NoExpensesFoundException extends Exception {

	public NoExpensesFoundException(final String message) {
		super(message);
	}
}

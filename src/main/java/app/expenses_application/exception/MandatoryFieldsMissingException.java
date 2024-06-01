package app.expenses_application.exception;

public class MandatoryFieldsMissingException extends Exception{
	public MandatoryFieldsMissingException(final String message) {
		super(message);
	}
}

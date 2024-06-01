package app.expenses_application.exception;

public class NoCategoriesFoundException extends Exception {
    public NoCategoriesFoundException(final String message) {
        super(message);
    }
}

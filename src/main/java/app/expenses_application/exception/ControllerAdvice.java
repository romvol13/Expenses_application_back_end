package app.expenses_application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller advice class for handling exceptions globally in the application.
 */

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(NoExpensesFoundException.class)
	public ResponseEntity<?> handleNoExpensesFoundException(NoExpensesFoundException exception) {
		return ResponseEntity.status(HttpStatus.OK).body(exception.getMessage());
	}

	@ExceptionHandler(MandatoryFieldsMissingException.class)
	public ResponseEntity<?> handleMandatoryFieldsMissingException(MandatoryFieldsMissingException exception) {
		return ResponseEntity.status(HttpStatus.OK).body(exception.getMessage());
	}

	@ExceptionHandler(NoPersonFoundException.class)
	public ResponseEntity<?> handleNoPersonFoundException(NoPersonFoundException exception) {
		return ResponseEntity.status(HttpStatus.OK).body(exception.getMessage());
	}

	@ExceptionHandler(NoCategoriesFoundException.class)
	public ResponseEntity<String> handleNoCategoriesFoundException(NoCategoriesFoundException ex) {
		return ResponseEntity.status(HttpStatus.OK).body(ex.getMessage());
	}
}

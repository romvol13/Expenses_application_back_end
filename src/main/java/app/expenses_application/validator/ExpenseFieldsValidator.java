package app.expenses_application.validator;

import app.expenses_application.model.Expense;
import org.springframework.stereotype.Service;

/**
 * Service class for validating expense fields.
 */
@Service
public class ExpenseFieldsValidator {

	/**
	 * Validates the fields of an expense.
	 *
	 * @param expense the expense to validate.
	 * @return true if all required fields are present and valid, false otherwise.
	 */
	public boolean validateExpenseFields(Expense expense) {
		return expense.getCategory() != null && !(expense.getPrice() <= 0) && expense.getDate() != null;
	}
}

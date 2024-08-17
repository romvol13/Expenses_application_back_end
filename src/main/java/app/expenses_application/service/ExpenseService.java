package app.expenses_application.service;

import app.expenses_application.exception.MandatoryFieldsMissingException;
import app.expenses_application.exception.NoCategoriesFoundException;
import app.expenses_application.exception.NoExpensesFoundException;
import app.expenses_application.exception.NoPersonFoundException;
import app.expenses_application.model.Category;
import app.expenses_application.model.Expense;
import app.expenses_application.model.Person;
import app.expenses_application.repository.ExpenseRepository;
import app.expenses_application.repository.PersonRepository;
import app.expenses_application.validator.ExpenseFieldsValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing expenses.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {
	private final ExpenseRepository expenseRepository;
	private final ExpenseFieldsValidator expenseFieldsValidator;
	private final PersonRepository personRepository;

	/**
	 * Adds a new expense.
	 *
	 * @param expense the expense to be added.
	 *
	 * @throws MandatoryFieldsMissingException if mandatory fields are missing in the expense.
	 */
	public void add(final Expense expense) throws MandatoryFieldsMissingException {
		if (expense == null) {
			log.error("Expense is null.");
			throw new NullPointerException("Expense is null.");
		}
		if (!expenseFieldsValidator.validateExpenseFields(expense)) {
			log.error("All fields must be filled and price must be more than 0.");
			throw new MandatoryFieldsMissingException("All fields must be filled and price must be more than 0.");
		}
		expenseRepository.save(expense);
	}

	/**
	 * Deletes an expense by its ID.
	 *
	 * @param id the ID of the expense to be deleted.
	 *
	 * @throws NoExpensesFoundException if no expense is found with the given ID.
	 */
	public void deleteById(final long id) throws NoExpensesFoundException {
		log.info("Looking for expense with {} id in the DB...", id);
		var expense = expenseRepository.findById(id);
		checkIfExpensesExists(expense.isEmpty(), "No expense found.");
		expenseRepository.deleteById(id);
	}

	/**
	 * Retrieves all expenses for a specific person.
	 *
	 * @param personId the ID of the person whose expenses are to be retrieved.
	 *
	 * @return a list of expenses for the specified person.
	 *
	 * @throws NoExpensesFoundException if no expenses are found for the specified person.
	 */
	public List<Expense> getAll(Long personId) throws NoExpensesFoundException, NoPersonFoundException {
		log.info("Looking for expenses for person ID {} in the DB...", personId);
		checkIfPersonIdExists(personId);
		var expenses = expenseRepository.findByPersonId(personId);
		checkIfExpenseExists(expenses);
		return expenses;
	}

	/**
	 * Retrieves all categories of expenses.
	 *
	 * @return a list of all expense categories.
	 */
	public List<String> getAllCategories() throws NoCategoriesFoundException {
		List<String> categories = Arrays.stream(Category.values())
				.map(Enum::name)
				.collect(Collectors.toList());
		if (categories.isEmpty()) {
			throw new NoCategoriesFoundException("No categories found.");
		}
		return categories;
	}

	/**
	 * Calculates the total expenses for the current month for a given person.
	 *
	 * @param personId the ID of the person.
	 *
	 * @return the total expenses for the current month.
	 *
	 * @throws NoExpensesFoundException if no expenses are found for the current month.
	 */
	public double getCurrentMonthTotalExpenses(final Long personId) throws NoExpensesFoundException {
		LocalDate startOfMonth = YearMonth.now().atDay(1);
		LocalDate endOfMonth = YearMonth.now().atEndOfMonth();
		log.info("Logged person id is: {}", personId);
		List<Expense> expenses = expenseRepository.findAllByPersonIdAndDateBetween(personId, startOfMonth, endOfMonth);
		checkIfExpensesExists(expenses.isEmpty(), "No expenses found.");
		return expenses.stream()
				.mapToDouble(Expense::getPrice)
				.sum();
	}

	/**
	 * Retrieves expenses by category and person ID.
	 *
	 * @param category the category of expenses.
	 * @param personId the ID of the person.
	 *
	 * @return a list of expenses belonging to the specified category for the given person.
	 *
	 * @throws NoExpensesFoundException if no expenses are found for the given category and person.
	 */
	public List<Expense> getExpensesByCategoryAndPersonId(final String category, final Long personId) throws NoExpensesFoundException, NoPersonFoundException {
		checkIfPersonIdExists(personId);
		List<Expense> expenses = expenseRepository.findByCategoryAndPersonId(Category.valueOf(category), personId);
		checkIfExpensesExists(expenses.isEmpty(), "No expenses found.");
		return expenses;
	}

	/**
	 * Checks if expenses exists in database
	 * @param expenses
	 * @param message
	 * @throws NoExpensesFoundException
	 */
	private static void checkIfExpensesExists(boolean expenses, String message) throws NoExpensesFoundException {
		if (expenses) {
			log.error(message);
			throw new NoExpensesFoundException(message);
		}
	}

	/**
	 * Checks if person exists in database
	 * @param personId
	 * @throws NoPersonFoundException
	 */
	private void checkIfPersonIdExists(Long personId) throws NoPersonFoundException {
		Optional<Person> person = personRepository.findById(personId);
		if (person.isEmpty()) {
			log.error("No person found in the DB with ID {}.", personId);
			throw new NoPersonFoundException("No person found in the DB with ID " + personId);
		}
	}

	/**
	 * Checks if single expense exists in database
	 * @param expenses
	 * @throws NoExpensesFoundException
	 */
	private static void checkIfExpenseExists(List<Expense> expenses) throws NoExpensesFoundException {
		checkIfExpensesExists(expenses.isEmpty(), "No expenses found.");
	}
}

package app.expenses_application.service;

import app.expenses_application.exception.MandatoryFieldsMissingException;
import app.expenses_application.exception.NoCategoriesFoundException;
import app.expenses_application.exception.NoExpensesFoundException;
import app.expenses_application.model.Category;
import app.expenses_application.model.Expense;
import app.expenses_application.repository.ExpenseRepository;
import app.expenses_application.validator.ExpenseFieldsValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
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

    /**
     * Adds a new expense.
     *
     * @param expense the expense to be added.
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
     * @throws NoExpensesFoundException if no expense is found with the given ID.
     */
    public void deleteById(final long id) throws NoExpensesFoundException {
        log.info("Looking for expense with {} id in the DB...", id);
        var expense = expenseRepository.findById(id);
        if (expense.isEmpty()) {
            log.error("No expense found with {} id.", id);
            throw new NoExpensesFoundException("No expense found with " + id + " id.");
        }
        expenseRepository.deleteById(id);
    }

    /**
     * Retrieves all expenses.
     *
     * @return a list of all expenses.
     * @throws NoExpensesFoundException if no expenses are found.
     */
    public List<Expense> getAll() throws NoExpensesFoundException {
        log.info("Looking for expenses in the DB...");
        var expenses = expenseRepository.findAll();
        if (expenses.isEmpty()) {
            log.error("No expenses found in the DB.");
            throw new NoExpensesFoundException("No expenses found in the DB.");
        }
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
     * @return the total expenses for the current month.
     * @throws NoExpensesFoundException if no expenses are found for the current month.
     */
    public double getCurrentMonthTotalExpenses(final Long personId) throws NoExpensesFoundException {
        LocalDate startOfMonth = YearMonth.now().atDay(1);
        LocalDate endOfMonth = YearMonth.now().atEndOfMonth();
        log.info("Logged person id is: {}", personId);
        List<Expense> expenses = expenseRepository.findAllByPersonIdAndDateBetween(personId, startOfMonth, endOfMonth);
        if (expenses.isEmpty()) {
            throw new NoExpensesFoundException("No expenses found for the current month.");
        }
        return expenses.stream()
                .mapToDouble(Expense::getPrice)
                .sum();
    }

    /**
     * Retrieves expenses by category and person ID.
     *
     * @param category  the category of expenses.
     * @param personId the ID of the person.
     * @return a list of expenses belonging to the specified category for the given person.
     * @throws NoExpensesFoundException if no expenses are found for the given category and person.
     */
    public List<Expense> getExpensesByCategoryAndPersonId(final String category, final Long personId) throws NoExpensesFoundException {
        List<Expense> expenses = expenseRepository.findByCategory(Category.valueOf(category));
        if (expenses.isEmpty()) {
            log.error("No expenses found for category: {}", category);
            throw new NoExpensesFoundException("No expenses found for category: " + category);
        }
        return expenseRepository.findByCategoryAndPersonId(Category.valueOf(category), personId);
    }
}

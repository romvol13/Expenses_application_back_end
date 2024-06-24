package app.expenses_application.controller;

import app.expenses_application.dto.ExpenseRequest;
import app.expenses_application.exception.MandatoryFieldsMissingException;
import app.expenses_application.exception.NoCategoriesFoundException;
import app.expenses_application.exception.NoExpensesFoundException;
import app.expenses_application.exception.NoPersonFoundException;
import app.expenses_application.model.Expense;
import app.expenses_application.service.DataMappingService;
import app.expenses_application.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class for handling expense-related requests.
 * This class provides endpoints for adding, deleting, and retrieving expenses.
 */

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "http://myngcode.s3-website.eu-central-1.amazonaws.com")
@RequestMapping("/api/expense")
@RequiredArgsConstructor
public class ExpenseController {

	private final ExpenseService expenseService;
	private final DataMappingService dataMappingService;

	/**
	 * Adds a new expense.
	 *
	 * @param request the expense request containing the expense details and person ID.
	 * @return a ResponseEntity with a success message.
	 * @throws MandatoryFieldsMissingException if mandatory fields are missing.
	 * @throws NoPersonFoundException if the person is not found.
	 */
	@Operation(summary = "Add new expense")
	@PostMapping("/add")
	public ResponseEntity<?> addExpense(@RequestBody final ExpenseRequest request) throws MandatoryFieldsMissingException, NoPersonFoundException {
		dataMappingService.setPersonIdToExpense(request.getExpense(), request.getPersonId());
		expenseService.add(request.getExpense());
		return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Expense added successfully.\"}");
	}

	/**
	 * Deletes an expense by its ID.
	 *
	 * @param id the ID of the expense to be deleted.
	 * @return a ResponseEntity with a success message.
	 * @throws NoExpensesFoundException if no expense is found with the given ID.
	 */
	@Operation(summary = "Delete expense by id")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable final Long id) throws NoExpensesFoundException {
		expenseService.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"Expense deleted successfully.\"}");
	}

	/**
	 * Retrieves all expenses for a specific person.
	 *
	 * @param personId the ID of the person whose expenses are to be retrieved.
	 * @return a ResponseEntity containing a list of expenses for the specified person.
	 * @throws NoExpensesFoundException if no expenses are found for the specified person.
	 */

	@Operation(summary = "Get all expenses for a person")
	@GetMapping("/getAll/{personId}")
	public ResponseEntity<?> getAll(@PathVariable final Long personId) throws NoExpensesFoundException, NoPersonFoundException {
		return ResponseEntity.status(HttpStatus.OK).body(expenseService.getAll(personId));
	}


	/**
	 * Retrieves all expense categories.
	 *
	 * @return a ResponseEntity containing a list of all categories.
	 */
	@Operation(summary = "Get all categories")
	@GetMapping("/getAllCategories")
	public ResponseEntity<List<String>> getAllCategories() throws NoCategoriesFoundException {
		return ResponseEntity.status(HttpStatus.OK).body(expenseService.getAllCategories());
	}

	/**
	 * Retrieves the total expenses for the current month for a given person.
	 *
	 * @param personId the ID of the person whose expenses are to be retrieved.
	 * @return a ResponseEntity containing the total expenses for the current month.
	 * @throws NoExpensesFoundException if no expenses are found for the given person ID.
	 */
	@Operation(summary = "Get current month expenses")
	@GetMapping("/currentMonthTotal/{personId}")
	public ResponseEntity<?> getCurrentMonthTotalExpenses(@PathVariable final Long personId) throws NoExpensesFoundException {
		double total = expenseService.getCurrentMonthTotalExpenses(personId);
		Map<String, Object> response = new HashMap<>();
		response.put("total", total);
		return ResponseEntity.ok(response);
	}

	/**
	 * Retrieves expenses by category and person ID.
	 *
	 * @param category the category of the expenses to be retrieved.
	 * @param personId the ID of the person whose expenses are to be retrieved.
	 * @return a ResponseEntity containing a list of expenses for the given category and person ID.
	 * @throws NoExpensesFoundException if no expenses are found for the given category and person ID.
	 */
	@Operation(summary = "Get expenses by category")
	@GetMapping("/byCategory/{category}/{personId}")
	public ResponseEntity<?> getExpensesByCategoryAndPersonId(@PathVariable final String category, @PathVariable final Long personId) throws NoExpensesFoundException, NoPersonFoundException {
		List<Expense> expenses = expenseService.getExpensesByCategoryAndPersonId(category, personId);
		return ResponseEntity.ok(expenses);
	}
}

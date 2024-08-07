package app.expenses_application;

import app.expenses_application.dto.LoginRequest;
import app.expenses_application.dto.LoginResponse;
import app.expenses_application.exception.MandatoryFieldsMissingException;
import app.expenses_application.exception.NoExpensesFoundException;
import app.expenses_application.exception.NoPersonFoundException;
import app.expenses_application.model.Category;
import app.expenses_application.model.Expense;
import app.expenses_application.model.Person;
import app.expenses_application.model.Role;
import app.expenses_application.repository.ExpenseRepository;
import app.expenses_application.repository.PersonRepository;
import app.expenses_application.service.AuthenticationService;
import app.expenses_application.service.DataMappingService;
import app.expenses_application.service.ExpenseService;
import app.expenses_application.util.JwtService;
import app.expenses_application.validator.ExpenseFieldsValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class ExpensesApplicationTests {

	@InjectMocks
	private DataMappingService dataMappingService;

	@Mock
	private ExpenseRepository expenseRepository;

	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private ExpenseService expenseService;

	@Mock
	private ExpenseFieldsValidator expenseFieldsValidator;

	@InjectMocks
	private AuthenticationService authenticationService;

	@Mock
	private JwtService jwtService;

	@Mock
	private AuthenticationManager authenticationManager;

//	@Test
//	void testIfExceptionThrownWhenNoExpenses() {
//		Mockito.when(expenseRepository.findAll()).thenReturn(Collections.emptyList());
//
//		NoExpensesFoundException exception1 = assertThrows(NoExpensesFoundException.class,
//				() -> expenseService.getAll());
//		NoExpensesFoundException exception2 = assertThrows(NoExpensesFoundException.class,
//				() -> expenseService.deleteById(5L));
//
//		assertEquals("No expenses found in the DB.", exception1.getMessage());
//		assertEquals("No expense found with 5 id.", exception2.getMessage());
//	}

	@Test
	void testIfNullPointerExceptionThrown() {
		NullPointerException exception = assertThrows(NullPointerException.class,
				() -> expenseService.add(null));

		assertEquals("Expense is null.", exception.getMessage());
	}

	@Test
	void testIfExceptionThrownWhenMissingMandatoryFields() {
		Expense expense = new Expense(5L, null, 20.0, LocalDate.now(), null, null);

		MandatoryFieldsMissingException exception = assertThrows(MandatoryFieldsMissingException.class,
				() -> expenseService.add(expense));

		assertEquals("All fields must be filled and price must be more than 0.", exception.getMessage());
	}

	@Test
	void testAddSuccess() throws MandatoryFieldsMissingException {
		Expense expense = new Expense();

		doReturn(true).when(expenseFieldsValidator).validateExpenseFields(any(Expense.class));

		expenseService.add(expense);

		Mockito.verify(expenseRepository).save(expense);
	}

//	@Test
//	void testGetCurrentMonthTotalExpensesWhenNoExpensesFound() {
//		// Given
//		long personId = 1L;
//		Mockito.when(expenseRepository.findAllByPersonIdAndDateBetween(Mockito.eq(personId), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
//				.thenReturn(Collections.emptyList());
//
//		// When
//		NoExpensesFoundException exception = assertThrows(NoExpensesFoundException.class,
//				() -> expenseService.getCurrentMonthTotalExpenses(personId));
//
//		// Then
//		assertEquals("No expenses found for the current month.", exception.getMessage());
//	}

//	@Test
//	void testGetExpensesByCategoryAndPersonIdWhenNoExpensesFound() {
//		// Given
//		String category = "FOOD";
//		long personId = 1L;
//
//		// When
//		NoExpensesFoundException exception = assertThrows(NoExpensesFoundException.class,
//				() -> expenseService.getExpensesByCategoryAndPersonId(category, personId));
//
//		// Then
//		assertEquals("No expenses found for category: FOOD", exception.getMessage());
//	}

	@Test
	void testAddExpenseWhenSuccess() throws MandatoryFieldsMissingException {
		// Given
		Expense expense = new Expense();
		expense.setCategory(Category.FOOD);
		expense.setDescription("Groceries");
		expense.setPrice(50.0);
		expense.setDate(LocalDate.now());
		expense.setPerson(new Person());

		Mockito.when(expenseFieldsValidator.validateExpenseFields(expense)).thenReturn(true);

		// When
		expenseService.add(expense);

		// Then
		Mockito.verify(expenseRepository).save(expense);
	}

	@Test
	void testDeleteExpenseWhenSuccess() throws NoExpensesFoundException {
		// Given
		long id = 1L;
		Expense expense = new Expense();
		expense.setId(id);
		Mockito.when(expenseRepository.findById(id)).thenReturn(Optional.of(expense));

		// When
		expenseService.deleteById(id);

		// Then
		Mockito.verify(expenseRepository).deleteById(id);
	}

	@Test
	void validateExpenseFieldsWhenMissingFields() {
		// Given
		Expense expense = new Expense();
		expense.setCategory(Category.FOOD);

		// When
		boolean isValid = expenseFieldsValidator.validateExpenseFields(expense);

		// Then
		assertFalse(isValid);
	}

	@Test
	void validateExpenseFieldsWhenPriceZero() {
		// Given
		Expense expense = new Expense();
		expense.setCategory(Category.FOOD);
		expense.setPrice(0.0);
		expense.setDate(LocalDate.now());

		// When
		boolean isValid = expenseFieldsValidator.validateExpenseFields(expense);

		// Then
		assertFalse(isValid);
	}

	@Test
	void validateExpenseFieldsWhenPriceNegative() {
		// Given
		Expense expense = new Expense();
		expense.setCategory(Category.FOOD);
		expense.setPrice(-10.0);
		expense.setDate(LocalDate.now());

		// When
		boolean isValid = expenseFieldsValidator.validateExpenseFields(expense);

		// Then
		assertFalse(isValid);
	}

	@Test
	void validateExpenseFieldsWhenNullFields() {
		// Given
		Expense expense = new Expense();
		expense.setCategory(null);
		expense.setPrice(50.0);
		expense.setDate(null);

		// When
		boolean isValid = expenseFieldsValidator.validateExpenseFields(expense);

		// Then
		assertFalse(isValid);
	}

	@Test
	void testSetPersonIdToExpenseSuccessful() throws NoPersonFoundException {
		// Given
		Long personId = 1L;
		Person person = new Person();
		person.setId(personId);
		Expense expense = new Expense();

		Mockito.when(personRepository.findById(personId)).thenReturn(Optional.of(person));

		// When
		dataMappingService.setPersonIdToExpense(expense, personId);

		// Then
		assertEquals(person, expense.getPerson());
	}

	@Test
	void testSetPersonIdToExpenseWhenNoPersonFound() {
		// Given
		Long personId = 1L;
		Expense expense = new Expense();

		Mockito.when(personRepository.findById(personId)).thenReturn(Optional.empty());

		// When/Then
		assertThrows(NoPersonFoundException.class,
				() -> dataMappingService.setPersonIdToExpense(expense, personId));
	}

	@Test
	void testSuccessfulAuthentication() {
		// Given
		String email = "test@example.com";
		String password = "password";
		LoginRequest request = new LoginRequest(email, password);
		Person person = new Person(1L, email, password, "Alex", Role.USER, true, null);
		String jwtToken = "jwtToken";

		// Mocking the repository and service calls
		Mockito.when(personRepository.findByEmail(email)).thenReturn(Optional.of(person));
		Mockito.when(jwtService.generateToken(person)).thenReturn(jwtToken);

		// Mocking the authentication manager call
		Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
				.thenAnswer(invocation -> null);

		// When
		LoginResponse response = authenticationService.authenticate(request);

		// Then
		assertEquals(jwtToken, response.getToken());
		assertEquals(person, response.getPerson());
	}
}

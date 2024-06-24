package app.expenses_application.repository;

import app.expenses_application.model.Category;
import app.expenses_application.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for managing expenses in the database.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /**
     * Retrieves all expenses for a specific person within a given date range.
     *
     * @param personId  the ID of the person.
     * @param startDate the start date of the date range.
     * @param endDate   the end date of the date range.
     * @return a list of expenses within the specified date range for the person.
     */

    List<Expense> findAllByPersonIdAndDateBetween(Long personId, LocalDate startDate, LocalDate endDate);

    /**
     * Retrieves all expenses belonging to a specific category.
     *
     * @param category the category of expenses to retrieve.
     * @return a list of expenses belonging to the specified category.
     */

    List<Expense> findByCategory(Category category);

    /**
     * Retrieves all expenses belonging to a specific category for a particular person.
     *
     * @param category  the category of expenses to retrieve.
     * @param personId the ID of the person.
     * @return a list of expenses belonging to the specified category for the person.
     */
    List<Expense> findByCategoryAndPersonId(Category category, Long personId);

    /**
     * Finds all expenses by the given person ID.
     *
     * @param personId the ID of the person whose expenses are to be retrieved.
     * @return a list of expenses for the specified person.
     */
    List<Expense> findByPersonId(Long personId);
}

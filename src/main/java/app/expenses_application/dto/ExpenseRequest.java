package app.expenses_application.dto;

import app.expenses_application.model.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for expense requests.
 * This class encapsulates the expense details and the associated person ID.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequest {

    private Expense expense;
    private Long personId;
}
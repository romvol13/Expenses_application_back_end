package app.expenses_application.service;

import app.expenses_application.exception.NoPersonFoundException;
import app.expenses_application.model.Expense;
import app.expenses_application.model.Person;
import app.expenses_application.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for mapping data between entities.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataMappingService {
    private final PersonRepository personRepository;

    /**
     * Sets the person ID to the expense.
     *
     * @param expense  the expense to be associated with the person.
     * @param personId the ID of the person.
     * @throws NoPersonFoundException if no person is found with the provided ID.
     */
    public void setPersonIdToExpense(final Expense expense, final Long personId) throws NoPersonFoundException {
        Optional<Person> personOptional = personRepository.findById(personId);
        if (personOptional.isEmpty()) {
            log.error("No person found with ID: {}", personId);
            throw new NoPersonFoundException("No person found with ID: " + personId);
        }
        expense.setPerson(personOptional.get());
    }
}

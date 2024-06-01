package app.expenses_application.validator;

import app.expenses_application.model.Person;
import org.springframework.stereotype.Service;

/**
 * Service class for validating person fields.
 */
@Service
public class PersonFieldsValidator {

    /**
     * Validates the fields of a person.
     *
     * @param person the person to validate.
     * @return true if all required fields are present and valid, false otherwise.
     */
    public boolean validatePersonFields(Person person) {
        return person.getEmail() != null && person.getRole() !=null && person.getName() !=null && person.getPassword() !=null;
    }
}

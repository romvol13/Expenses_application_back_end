package app.expenses_application.service;

import app.expenses_application.dto.PersonResponse;
import app.expenses_application.exception.MandatoryFieldsMissingException;
import app.expenses_application.exception.NoPersonFoundException;
import app.expenses_application.model.Person;
import app.expenses_application.repository.PersonRepository;
import app.expenses_application.validator.PersonFieldsValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing persons.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonFieldsValidator personFieldsValidator;
    private final PersonMappingService personMappingService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Adds a new person.
     *
     * @param person the person to be added.
     * @throws MandatoryFieldsMissingException if mandatory fields are missing in the person.
     */
    public void add(final Person person) throws MandatoryFieldsMissingException {
        if (person == null) {
            log.error("Person is null.");
            throw new NullPointerException("Person is null.");
        }
        if (!personFieldsValidator.validatePersonFields(person)) {
            log.error("All fields must be filled.");
            throw new MandatoryFieldsMissingException("All fields must be filled.");
        }

        String hashedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(hashedPassword);

        personRepository.save(person);
    }

    /**
     * Deletes a person by their ID.
     *
     * @param id the ID of the person to be deleted.
     * @throws NoPersonFoundException if no person is found with the given ID.
     */
    public void deleteById(final long id) throws NoPersonFoundException {
        log.info("Looking for person with {} id in the DB...", id);
        var person = personRepository.findById(id);

        if (person.isEmpty()) {
            log.error("No person found with {} id.", id);
            throw new NoPersonFoundException("No person found with " + id + " id.");
        }
        personRepository.deleteById(id);
    }

    /**
     * Retrieves all persons.
     *
     * @return a list of all persons.
     * @throws NoPersonFoundException if no persons are found.
     */
    public List<PersonResponse> getAll() throws NoPersonFoundException {
        log.info("Looking for person in the DB...");
        var personList = personRepository.findAll();

        if (personList.isEmpty()) {
            log.error("No person found in the DB.");
            throw new NoPersonFoundException("No person found in the DB.");
        }

        return personMappingService.mapToPersonResponse(personList);
    }
}

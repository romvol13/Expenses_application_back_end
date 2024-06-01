package app.expenses_application.controller;

import app.expenses_application.exception.MandatoryFieldsMissingException;
import app.expenses_application.exception.NoPersonFoundException;
import app.expenses_application.model.Person;
import app.expenses_application.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling person-related requests.
 * This class provides endpoints for adding, deleting, and retrieving persons.
 */

@RestController
@Slf4j
@RequestMapping("/api/person")
//@CrossOrigin(origins = "http://myngcode.s3-website.eu-central-1.amazonaws.com")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    /**
     * Adds a new person.
     *
     * @param person the person object containing the details of the person to be added.
     * @return a ResponseEntity with a success message.
     * @throws MandatoryFieldsMissingException if mandatory fields are missing in the person object.
     */
    @Operation(summary = "Add new person")
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody final Person person) throws MandatoryFieldsMissingException {
        personService.add(person);
        return ResponseEntity.status(HttpStatus.OK).body("Person added successfully.");
    }

    /**
     * Deletes a person by their ID.
     *
     * @param id the ID of the person to be deleted.
     * @return a ResponseEntity with a success message.
     * @throws NoPersonFoundException if no person is found with the given ID.
     */
    @Operation(summary = "Delete person by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable final long id) throws NoPersonFoundException {
        personService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Person deleted successfully.");
    }

    /**
     * Retrieves all persons.
     *
     * @return a ResponseEntity containing a list of all persons.
     * @throws NoPersonFoundException if no persons are found.
     */
    @Operation(summary = "Get all persons")
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() throws NoPersonFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(personService.getAll());
    }
}

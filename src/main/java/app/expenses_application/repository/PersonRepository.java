package app.expenses_application.repository;

import app.expenses_application.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repository interface for managing persons in the database.
 */

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * Retrieves a person by their email address.
     *
     * @param email the email address of the person.
     * @return an Optional containing the person if found, or empty if not found.
     */
    Optional<Person> findByEmail(String email);

    /**
     * Finds a person by their ID.
     *
     * @param id the ID of the person to be retrieved.
     * @return an Optional containing the person if found, or empty if not found.
     */
    Optional<Person> findById(Long id);
}

package app.expenses_application.service;

import app.expenses_application.dto.PersonResponse;
import app.expenses_application.model.Person;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for mapping Person entities to PersonResponse DTOs.
 */
@Service
public class PersonMappingService {

    /**
     * Maps a list of Person entities to a list of PersonResponse DTOs.
     *
     * @param personList the list of Person entities to be mapped.
     * @return a list of PersonResponse DTOs.
     */
    public List<PersonResponse> mapToPersonResponse(final List<Person> personList) {
        List<PersonResponse> personResponseList = new ArrayList<>();
        for (Person person : personList) {
            PersonResponse personResponse = new PersonResponse();
            personResponse.setId(person.getId());
            personResponse.setName(person.getName());
            personResponse.setEmail(person.getEmail());
            personResponse.setRole(person.getRole());
            personResponse.setPassword(person.getPassword());
            personResponseList.add(personResponse);
        }

        return personResponseList;
    }
}

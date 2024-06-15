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
            PersonResponse personResponse = PersonResponse.builder()
                    .id(person.getId())
                    .name(person.getName())
                    .email(person.getEmail())
                    .role(person.getRole())
                    .password(person.getPassword())
                    .build();
            personResponseList.add(personResponse);
        }

        return personResponseList;
    }
}

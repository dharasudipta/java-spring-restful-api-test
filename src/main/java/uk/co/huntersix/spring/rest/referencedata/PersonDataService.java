package uk.co.huntersix.spring.rest.referencedata;

import org.springframework.stereotype.Service;
import uk.co.huntersix.spring.rest.exception.BadRequestException;
import uk.co.huntersix.spring.rest.exception.PersonExistException;
import uk.co.huntersix.spring.rest.exception.PersonNotFoundException;
import uk.co.huntersix.spring.rest.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PersonDataService {
    public static List<Person> PERSON_DATA = Arrays.asList(
            new Person("Mary", "Smith"),
            new Person("Brian", "Smith"),
            new Person("Brian", "Archer"),
            new Person("Collin", "Brown"),
            new Person("Dan", "Brown")
    );

    public Person findPerson(String lastName, String firstName) throws PersonNotFoundException {
        return PERSON_DATA.stream()
                .filter(p -> p.getFirstName().equalsIgnoreCase(firstName)
                        && p.getLastName().equalsIgnoreCase(lastName))
                .findFirst().orElseThrow(() -> new PersonNotFoundException("Person Not Found", new Throwable("Person Not Found. Please check the data store or spelling in your request.")));//.collect(Collectors.toList());
    }

    public List<Person> findPersonByLastName(String lastName) throws PersonNotFoundException {
        List<Person> personList = PERSON_DATA.stream()
                .filter(p -> p.getLastName().equalsIgnoreCase(lastName)).collect(Collectors.toList());
        if (personList != null && personList.isEmpty()) {
            throw new PersonNotFoundException("Person Not Found", new Throwable("Person not found with supplied last name. Please check the data store or spelling in your request."));
        }
        return personList;
    }

    public Person addPerson(String lastName, String firstName) throws PersonExistException {
        if (firstName == null || firstName == "" || lastName == null || lastName == "") {
            throw new BadRequestException("Bad Request", new Throwable("Bad request. please review your request"));
        }
        Person person;
        try {
            person = findPerson(lastName, firstName);
            throw new PersonExistException("Person Exist Already", new Throwable("Please try with another person as person exist in the system already"));
        } catch (PersonNotFoundException ex) {
            person = new Person(firstName, lastName);
            List<Person> tempList = new ArrayList<>(PERSON_DATA);
            tempList.add(person);
            PERSON_DATA = new ArrayList<>(tempList);
            return person;
        }
    }

    public List<Person> findAll() {
        return PERSON_DATA;
    }
}

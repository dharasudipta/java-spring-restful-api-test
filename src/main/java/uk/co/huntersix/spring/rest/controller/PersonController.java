package uk.co.huntersix.spring.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uk.co.huntersix.spring.rest.exception.PersonNotFoundException;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

import java.util.List;

@RestController
public class PersonController {
    private PersonDataService personDataService;

    public PersonController(@Autowired PersonDataService personDataService) {
        this.personDataService = personDataService;
    }

    @GetMapping("/person/{lastName}/{firstName}")
    public Person person(@PathVariable(value = "lastName") String lastName,
                         @PathVariable(value = "firstName") String firstName) {
        return personDataService.findPerson(lastName, firstName);
    }

    @GetMapping("/person/{lastName}")
    public List<Person> personByLastName(@PathVariable(value = "lastName") String lastName) {
        return personDataService.findPersonByLastName(lastName);
    }

    @RequestMapping(value = "/person/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Person addPerson(@RequestBody Person person) {
        return personDataService.addPerson(person.getLastName(), person.getFirstName());
    }

    @GetMapping("/person/list")
    public List<Person> personList() {
        return personDataService.findAll();
    }
}
package uk.co.huntersix.spring.rest.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.co.huntersix.spring.rest.exception.PersonNotFoundException;
import uk.co.huntersix.spring.rest.model.Person;
import uk.co.huntersix.spring.rest.referencedata.PersonDataService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonDataService personDataService;

    @Test
    public void shouldReturnPersonFromService() throws Exception {
        when(personDataService.findPerson(any(), any())).thenReturn(new Person("Mary", "Smith"));
        this.mockMvc.perform(get("/person/smith/mary"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("firstName").value("Mary"))
                .andExpect(jsonPath("lastName").value("Smith"));
    }

    @Test
    public void shouldReturnPersonNotFound() throws Exception {
        when(personDataService.findPerson(any(), any())).thenThrow(new PersonNotFoundException("Person Not Found", new Throwable()));
        this.mockMvc.perform(get("/person/xyz/abcd"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("error").exists())
                .andExpect(jsonPath("message").value("Person Not Found"))
                .andExpect(jsonPath("path").value("/person/xyz/abcd"))
        ;
    }

    @Test
    public void shouldReturnSinglePersonByLastName() throws Exception {
        List<Person> personList = new ArrayList<>();
//        personList.add(new Person("Graham","Bell" ));
//        personList.add(new Person("Alicia","Bell" ));
        personList.add(new Person("Stephen", "Hawkins"));
        when(personDataService.findPersonByLastName(any())).thenReturn(personList);
        this.mockMvc.perform(get("/person/Hawkins"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Stephen")))
                .andExpect(jsonPath("$[0].lastName", is("Hawkins")));
    }

    @Test
    public void shouldReturnAllPersonByLastName() throws Exception {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Graham", "Bell"));
        personList.add(new Person("Alicia", "Bell"));
//        personList.add(new Person("Stephen", "Hawkins"));
        when(personDataService.findPersonByLastName(any())).thenReturn(personList);
        this.mockMvc.perform(get("/person/Bell"))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].firstName",is("Stephen")))
                .andExpect(jsonPath("$[0].firstName", is("Graham")))
                .andExpect(jsonPath("$[1].firstName", is("Alicia")))
                .andExpect(jsonPath("$[0].lastName", is("Bell")))
                .andExpect(jsonPath("$[1].lastName", is("Bell")))
                .andExpect(jsonPath("$.*", hasSize(2)))
        ;
    }

    @Test
    public void shouldReturnPersonNotFoundWithLastName() throws Exception {
        when(personDataService.findPersonByLastName(any())).thenThrow(new PersonNotFoundException("Person Not Found", new Throwable()));
        this.mockMvc.perform(get("/person/xyz"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("error").exists())
                .andExpect(jsonPath("message").value("Person Not Found"))
                .andExpect(jsonPath("path").value("/person/xyz"))
        ;
    }

    @Test
    public void shouldReturnNewPerson() throws Exception {
        when(personDataService.addPerson(any(), any())).thenReturn(new Person("Sudipta", "Dhara"));
        this.mockMvc.perform(post("/person/add").contentType(MediaType.APPLICATION_JSON_UTF8).content("{\n" +
                "    \"firstName\": \"Sudipta\",\n" +
                "    \"lastName\": \"Dhara\"\n" +
                "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("firstName").value("Sudipta"))
                .andExpect(jsonPath("lastName").value("Dhara"))
        ;
        ;
    }
}
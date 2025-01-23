package com.mongodb.advance.service;

import com.mongodb.advance.model.Person;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService {
    int savePerson(Person person);

    List<Person> findByNameStarts(String firstName);

    List<Person> getAll(Person person);

    String deleteByIdOnly(Integer personId);

    Person updatePerson(Person person);

    Iterable<Person> findByIdentity(Integer personId);

    List<Person> saveAllPerson(List<Person> person);

    List<Person> getAllByAge(Integer minAge, Integer maxAge);

    Page<Person> getAllBySearch(String firstName, String city, Integer minAge, Integer maxAge, Pageable p);

    List<Document> getPersonByOldestCity();
}

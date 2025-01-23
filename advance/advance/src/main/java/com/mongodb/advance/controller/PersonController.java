package com.mongodb.advance.controller;

import com.mongodb.advance.model.Person;
import com.mongodb.advance.service.PersonService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @PostMapping()
    public int save(@RequestBody Person person){
        return personService.savePerson(person);
    }

    @PostMapping(value="/saveAll")
    public List<Person> saveAll(@RequestBody List<Person> person){
        return personService.saveAllPerson(person);
    }



    @GetMapping(value="/{firstName}")
    public List<Person> getByName(@RequestParam("firstName") String firstName){
        return personService.findByNameStarts(firstName);
    }
    @GetMapping(value="/{personId}")
    public Iterable<Person> getByName(@PathVariable Integer personId){
        return personService.findByIdentity(personId);
    }

    @GetMapping()
    public List<Person> getAll(Person person){
        return personService.getAll(person);
    }

    @GetMapping("/age")
    public List<Person> getByAge(@RequestParam(required = false) Integer minAge,@RequestParam(required = false) Integer maxAge){
        return personService.getAllByAge(minAge,maxAge);
    }


    @DeleteMapping(value="/{personId}")
    public String delete(@PathVariable Integer personId){
        return personService.deleteByIdOnly(personId);
    }


    @PutMapping(value = "/{personId}")
    public Person update(@RequestBody Person person,@PathVariable Integer personId){
        return personService.updatePerson(person);
    }

    @GetMapping("/search")
    public Page<Person> getBySearch(@RequestParam(required = false) String firstName,
                                    @RequestParam(required = false) String city,
                                    @RequestParam(required = false) Integer minAge,
                                    @RequestParam(required = false) Integer maxAge,
                                    @RequestParam(value = "pageNUmber",defaultValue = "0") Integer pageNumber,
                                    @RequestParam(value = "pageSize",defaultValue = "5 ") Integer pageSize){

        Pageable p= PageRequest.of(pageNumber,pageSize);
        return personService.getAllBySearch(firstName,city,minAge,maxAge,p);
    }
    @GetMapping("/oldestPerson")
    public List<Document> findByOldestPerson(){
        return personService.getPersonByOldestCity();
    }

}

package com.mongodb.advance.service;

import com.mongodb.advance.model.Person;
import com.mongodb.advance.repository.PersonRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService{
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public int savePerson(Person person) {
        return personRepository.save(person).getPersonId();
    }

    @Override
    public List<Person> findByNameStarts(String firstName) {
        return personRepository.findNameStartsWith(firstName);
    }

    @Override
    public List<Person> getAll(Person person) {
        return personRepository.findAll();
    }

    @Override
    public String deleteByIdOnly(Integer personId) {
        personRepository.deleteById(personId);
        return "deleted successfully";
    }

    @Override
    public Person updatePerson(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Iterable<Person> findByIdentity(Integer personId) {
        return personRepository.findAllById(Collections.singleton(personId));
    }

    @Override
    public List<Person> saveAllPerson(List<Person> person) {
        return personRepository.saveAll(person);
    }

    @Override
    public List<Person> getAllByAge(Integer minAge, Integer maxAge) {
        return personRepository.findByAge(minAge,maxAge);
    }

    @Override
    public Page<Person> getAllBySearch(String firstName, String city, Integer minAge, Integer maxAge, Pageable p) {

        Query query=new Query().with(p);
        List<Criteria> criteria=new ArrayList<>();
         if(firstName!= null ){
             criteria.add(Criteria.where("firstName").is(firstName));
         }
         if(minAge!=null&&maxAge!=null){
             criteria.add(Criteria.where("age").gte(minAge).lte(maxAge));
         }
         if(city!=null){
             criteria.add(Criteria.where("address.city").is(city));

         }
         if(!criteria.isEmpty()){
             query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
         }

         Page<Person> people= PageableExecutionUtils.getPage(
                 mongoTemplate.find(query, Person.class),
                 p,()->mongoTemplate.count(query.skip(0).limit(0), Person.class)
         );

        return people;

    }

    @Override
    public List<Document> getPersonByOldestCity() {

        UnwindOperation unwindOperation= Aggregation.unwind("address");

        SortOperation sortOperation=Aggregation.sort(Sort.by(Sort.Direction.DESC,"age"));

        GroupOperation groupOperation=Aggregation.group("address.city").first(Aggregation.ROOT).as("oldestPerson");

        Aggregation aggregation=Aggregation.newAggregation(unwindOperation,sortOperation,groupOperation);

        List<Document> people=mongoTemplate.aggregate(aggregation, Person.class, Document.class).getMappedResults();


        return people;
    }
}

package com.mongodb.advance.repository;

import com.mongodb.advance.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends MongoRepository<Person,Integer> {
    @Query("{'firstName':?0}")
    List<Person> findNameStartsWith(String firstName);
    @Query(value = "{'age':{$gte:?0,$lte:?1}}",fields = "{'address':0}")
    List<Person> findByAge(Integer minAge,Integer maxAge);
  // {$and:[{'age':{$lt:?0}},{'age':{$gt:?1}}]}
// 'age':{$elemMatch :  { $lte: ?0, $gt: ?1 }}
}

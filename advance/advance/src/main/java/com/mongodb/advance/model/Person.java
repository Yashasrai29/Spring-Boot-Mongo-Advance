package com.mongodb.advance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(collection = "person")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {
    @Id
    private int personId;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private List<String> hobbies;
    private List<Address> address;

}

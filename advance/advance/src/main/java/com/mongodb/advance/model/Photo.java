package com.mongodb.advance.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "photo")
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Photo {
    @Id
    private String id;
    private String title;
    private Binary image;
}

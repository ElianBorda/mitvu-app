package com.unq.mitvu.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@ToString
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "tutores")
public class Tutor extends Usuario {

}

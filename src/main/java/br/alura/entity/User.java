package br.alura.entity;

import br.alura.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Document("users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String username;

    private String name;

    private String password;

    @Builder.Default
    private List<Role> roles = new ArrayList<>();
}

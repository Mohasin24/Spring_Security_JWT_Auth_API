package com.authentication.model;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Document(collation = "user_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class User
{
    @Id
    private String id;
    private String name;
    private String username;
    private String password;

    private List<GrantedAuthority> authorities;
}

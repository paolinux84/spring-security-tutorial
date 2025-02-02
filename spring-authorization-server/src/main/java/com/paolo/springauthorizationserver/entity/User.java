package com.paolo.springauthorizationserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data // includes getter, setter, construction, hash and equal
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

//    We don't use @Size (generic spring) or @Length (same as @Size, but Hibernate specific)
//    which are validations,
//    we use specific JPA annotation to ensure strict rule on
//    physical column
    @Column(length = 60)
    private String password;
    private String role;
    private boolean enabled = false;


}

package com.jwtscurity.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;


import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String username;
    private String password;

    @ElementCollection
    @CollectionTable(name = "authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authority")
    private List<GrantedAuthority> authorities;


}

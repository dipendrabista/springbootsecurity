package com.punojsoft.springbootsecurity.bootsecurity.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "tbl_roles")
@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue
    private long id;
    private String role;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> users;
}
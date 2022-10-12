package com.fatjon.eventmanager.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fatjon.eventmanager.model.event.Event;
import com.fatjon.eventmanager.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    //@JsonIgnore
    @Column
    @Length(min = 8)
    private String password;
    @Email
    private String email;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Collection<Role> roles = new ArrayList<>();
    @JsonIgnore
    @ManyToMany(mappedBy = "pjesmares")
    private List<Event> evente = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "createdBy")
    private Set<Event> event;
}
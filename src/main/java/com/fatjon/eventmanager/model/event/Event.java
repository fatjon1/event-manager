package com.fatjon.eventmanager.model.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fatjon.eventmanager.model.enums.EventCategory;
import com.fatjon.eventmanager.model.enums.EventStatus;
import com.fatjon.eventmanager.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SuperBuilder
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private EventCategory category;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME , pattern = ("dd/MM/yyyy HH:mm"))
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME , pattern = ("dd/MM/yyyy HH:mm"))
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime endDate;
    private String location;
    private String title;
    private String description;
    private EventStatus eventStatus;
  /*  @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    private User createdBy;*/
    @JsonIgnore
    @ManyToMany(mappedBy = "evente")
    private Set<User> pjesmares = new HashSet<>();
}

package edu.practice.resourceServer.model.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.*;

@Entity
@Table(name = "notes")
@JsonDeserialize(builder = Note.NoteBuilder.class)
@Builder(builderClassName = "NoteBuilder", toBuilder = true)
@EqualsAndHashCode(of = {"uuid"})
@ToString(exclude = {"editors", "body"})
public class Note {

    @Id
    @GeneratedValue
    @Getter
    @Setter(AccessLevel.NONE)
    private UUID uuid;

    @OneToOne
    @Getter
    @Setter(AccessLevel.NONE)
    private ApplicationUser author;

    @ManyToMany
    @Builder.Default
    @Getter
    @Setter(AccessLevel.NONE)
    private Collection<ApplicationUser> editors = new HashSet<>();

    @Lob
    @Getter
    @Setter
    private String header;

    @Lob
    @Getter
    @Setter
    private String body;

    @JsonPOJOBuilder(withPrefix = "")
    public static class NoteBuilder {

    }
}
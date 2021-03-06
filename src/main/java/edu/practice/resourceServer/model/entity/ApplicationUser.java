package edu.practice.resourceServer.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

@Entity
@Table(name = "users")
@JsonDeserialize(builder = ApplicationUser.ApplicationUserBuilder.class)
@Builder(builderClassName = "ApplicationUserBuilder", toBuilder = true)
@EqualsAndHashCode(of = {"username", "role", "enabled"})
@ToString(exclude = {"password"})
public class ApplicationUser {

    @Id
    @GeneratedValue
    @Getter
    @Setter(AccessLevel.NONE)
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    @Getter
    @Setter(AccessLevel.NONE)
    private ApplicationRole role;

    @Getter
    @Setter
    @Lob
    private String username;

    @Getter
    @Setter
    @Lob
    @JsonIgnore
    private String password;

    @Getter
    @Setter
    private boolean enabled;

    @OneToMany(mappedBy = "author")
    @Builder.Default
    @Getter
    @Setter(AccessLevel.NONE)
    private Collection<Note> ownNotes = new HashSet<>();

    @ManyToMany(mappedBy = "editors")
    @Builder.Default
    @Getter
    @Setter(AccessLevel.NONE)
    private Collection<Note> editNotes = new HashSet<>();

    @JsonPOJOBuilder(withPrefix = "")
    public static class ApplicationUserBuilder {

    }
}

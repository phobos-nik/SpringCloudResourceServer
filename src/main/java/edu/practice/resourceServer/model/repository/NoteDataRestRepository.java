package edu.practice.resourceServer.model.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import edu.practice.resourceServer.model.entity.Note;

@RepositoryRestResource(path = "notes", collectionResourceRel = "notes")
@PreAuthorize("hasRole('ROLE_USER')")
public interface NoteDataRestRepository extends JpaRepository<Note, UUID> {
    
}
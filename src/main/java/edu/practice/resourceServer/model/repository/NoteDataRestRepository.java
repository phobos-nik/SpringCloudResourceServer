package edu.practice.resourceServer.model.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import edu.practice.resourceServer.model.entity.Note;

@RepositoryRestResource(path = "notes", collectionResourceRel = "notes")
public interface NoteDataRestRepository extends JpaRepository<Note, UUID> {
    
}
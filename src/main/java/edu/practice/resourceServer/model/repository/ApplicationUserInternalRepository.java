package edu.practice.resourceServer.model.repository;

import edu.practice.resourceServer.model.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ApplicationUserInternalRepository extends JpaRepository<ApplicationUser, UUID> {
}

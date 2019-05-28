package edu.practice.resourceServer.model.repository;

import edu.practice.resourceServer.model.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(path = "users", collectionResourceRel = "users")
public interface ApplicationUserDataRestRepository extends JpaRepository<ApplicationUser, UUID> {

}

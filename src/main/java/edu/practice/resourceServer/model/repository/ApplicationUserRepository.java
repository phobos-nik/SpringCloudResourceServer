package edu.practice.resourceServer.model.repository;

import edu.practice.resourceServer.model.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

import javax.transaction.Transactional;

@RepositoryRestResource(path = "users", collectionResourceRel = "users")
@Transactional
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, UUID> {
    
    @Override
    @PreAuthorize("permitAll()")
    <S extends ApplicationUser> S save(final S entity);
    
}

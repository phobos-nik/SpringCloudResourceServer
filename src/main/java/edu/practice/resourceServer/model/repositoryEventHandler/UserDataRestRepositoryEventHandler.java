package edu.practice.resourceServer.model.repositoryEventHandler;

import edu.practice.resourceServer.model.entity.ApplicationUser;
import edu.practice.resourceServer.model.repository.ApplicationUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;

@RepositoryEventHandler(ApplicationUser.class)
public class UserDataRestRepositoryEventHandler {

    private ApplicationUserRepository applicationUserRepository;
    private PasswordEncoder passwordEncoder;    

    @Autowired
    public UserDataRestRepositoryEventHandler(ApplicationUserRepository applicationUserRepository,
                                                PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @HandleBeforeSave
    public void beforeApplicationUserSaveHandler(ApplicationUser applicationUser) {
        if (applicationUser.getPassword() == null || applicationUser.getPassword().equals("")) {
            final ApplicationUser storedUser = applicationUserRepository.getOne(applicationUser.getUuid());
            applicationUser.setPassword(storedUser.getPassword());
        }

        if (!applicationUser.getPassword().startsWith("$2$") ||
            !applicationUser.getPassword().startsWith("$2a$") ||
            !applicationUser.getPassword().startsWith("$2x$") ||
            !applicationUser.getPassword().startsWith("$2y$") ||
            !applicationUser.getPassword().startsWith("$2b$"))
            applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
    }
}

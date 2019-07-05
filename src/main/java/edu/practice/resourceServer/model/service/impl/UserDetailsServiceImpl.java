package edu.practice.resourceServer.model.service.impl;

import edu.practice.resourceServer.model.entity.ApplicationUser;
import edu.practice.resourceServer.model.entity.UserDetailsImpl;
import edu.practice.resourceServer.model.repository.ApplicationUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private ApplicationUserRepository applicationUserRepository;

    private MessageSource messageSource;

    @Autowired
    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository, MessageSource messageSource) {
        this.applicationUserRepository = applicationUserRepository;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUserProbe = ApplicationUser.builder().username(username).build();
        Example<ApplicationUser> persistedApplicationUserExample = Example.of(
                applicationUserProbe,
                ExampleMatcher.matching().withIgnoreCase("username"));
        Optional<ApplicationUser> persistedUserOptional = applicationUserRepository.findOne(persistedApplicationUserExample);
        if (!persistedUserOptional.isPresent())
            throw new UsernameNotFoundException(
                messageSource.getMessage(
                    "userDetailsServiceImpl.usernameNotFoundException.message",
                    new Object[] {
                        username
                    },
                    LocaleContextHolder.getLocale()));

        ApplicationUser persistedUser = persistedUserOptional.get();
        return UserDetailsImpl.builder()
                .authorities(persistedUser.getRole().getAuthorities())
                .password(persistedUser.getPassword())
                .username(persistedUser.getUsername())
                .enabled(persistedUser.isEnabled())
                .build();
    }
}

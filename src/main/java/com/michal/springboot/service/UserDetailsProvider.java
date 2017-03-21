package com.michal.springboot.service;

import com.michal.springboot.domain.Role;
import com.michal.springboot.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Mike on 2017-03-19.
 */
public class UserDetailsProvider implements UserDetails {

    private User user;

    @Autowired
    private UserService userService;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserDetailsProvider(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Set<Role> roles = new HashSet<>();
        user.getRoles().forEach(roles::add);

        log.info(String.format("%d user roles size",roles.size()));

        roles.forEach(r->authorities.add(new SimpleGrantedAuthority(r.getRoleName())));

        log.info(String.format("%d auth size",authorities.size()));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getEmail();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userService.isUserEnabled(user.getId());
    }
}

package com.michal.springboot.service;


import com.michal.springboot.domain.User;
import com.michal.springboot.forms.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Mike on 2017-03-19.
 */
public class UserDetailsProvider implements UserDetails {

    private User user;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserDetailsProvider(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();

        user.getRoles()
                .forEach(r->authorities.add(new SimpleGrantedAuthority(r.getRoleName())));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
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

        if(user.getStatus()== UserStatus.ACTIVE){
            return true;
        }else {
            return false;
        }

    }
}

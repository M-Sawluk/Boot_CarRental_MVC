package com.michal.springboot.service;

import com.michal.springboot.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Mike on 2017-03-19.
 */
@Service
public class UserLoginAdapter implements UserDetailsService {

    private UserService userService;

    public UserLoginAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByUserName(userName);
        if( user == null ){
            throw new UsernameNotFoundException(userName);
        }

        return new UserDetailsProvider(user);
    }
}

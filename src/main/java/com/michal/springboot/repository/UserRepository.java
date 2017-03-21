package com.michal.springboot.repository;


import com.michal.springboot.domain.User;
import com.michal.springboot.forms.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Long> {

	User findByUsername(String userName);

	String findByEmail(String email);


}

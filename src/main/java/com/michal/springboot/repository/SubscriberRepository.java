package com.michal.springboot.repository;


import com.michal.springboot.domain.Subscriber;
import org.springframework.data.repository.CrudRepository;

public interface SubscriberRepository extends CrudRepository<Subscriber,Long> {

	Subscriber findByEmail(String email);

}

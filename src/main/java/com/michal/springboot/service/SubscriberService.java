package com.michal.springboot.service;


import com.michal.springboot.domain.Subscriber;
import com.michal.springboot.repository.SubscriberRepository;
import org.springframework.stereotype.Service;


@Service
public class SubscriberService {

    private SubscriberRepository subscriberRepository;


    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public void addSubscriber(Subscriber subscriber) {

        subscriberRepository.save(subscriber);
    }

    public void deleteSubscriber(Subscriber subscriber) {

        subscriberRepository.delete(subscriber);

    }

    public Subscriber findSubscriberByEmail(String email) {

        return subscriberRepository.findByEmail(email);
    }

    public Subscriber getSubscriber(long id) {

        return subscriberRepository.findOne(id);
    }

    public void updateSubsriber(Subscriber subscriber) {

        subscriberRepository.save(subscriber);
    }

}

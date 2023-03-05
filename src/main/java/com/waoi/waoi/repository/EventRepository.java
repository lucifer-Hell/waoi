package com.waoi.waoi.repository;

import com.waoi.waoi.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event,String> {
    Event findByEvtId(String eventId);
}

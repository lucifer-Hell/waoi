package com.waoi.waoi.repository;

import com.waoi.waoi.model.UserState;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStateRepository extends MongoRepository<UserState,String> {
    UserState findByMobileNumber(String mobileNumber);
}

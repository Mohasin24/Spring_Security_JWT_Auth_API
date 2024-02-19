package com.authentication.repository;

import com.authentication.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User,String> {
    boolean existsByUsername(String username);
}

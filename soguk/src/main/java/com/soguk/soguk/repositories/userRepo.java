package com.soguk.soguk.repositories;

import com.soguk.soguk.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepo extends MongoRepository<User, String> {

    User findByNick(String nick);

    boolean existsByNick(String nick);

    boolean existsByEmail(String email);
}

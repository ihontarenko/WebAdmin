package io.startform.jpa;

import io.startform.jpa.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractRepository<User> {

    @Override
    public Class<User> getReflection() {
        return User.class;
    }

}

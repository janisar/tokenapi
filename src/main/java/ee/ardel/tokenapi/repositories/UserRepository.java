package ee.ardel.tokenapi.repositories;

import ee.ardel.tokenapi.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends MongoRepository<User, String> {

    User findByEmailAndPassword(String email, String password);
}

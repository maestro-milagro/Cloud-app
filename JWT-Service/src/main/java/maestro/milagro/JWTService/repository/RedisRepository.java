package maestro.milagro.JWTService.repository;

import maestro.milagro.JWTService.model.User;

import java.util.Map;

public interface RedisRepository {
    /**
     * Return all movies
     */
//    Map<User, String> findAllUsers();

    /**
     * Add key-value pair to Redis.
     */
    void add(User user, String token);

    void delete(User user);

    String findToken(User user);
}

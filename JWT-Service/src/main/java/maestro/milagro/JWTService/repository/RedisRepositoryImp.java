package maestro.milagro.JWTService.repository;

import jakarta.annotation.PostConstruct;
import maestro.milagro.JWTService.model.User;
import maestro.milagro.JWTService.model.UserAndTokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface RedisRepositoryImp extends KeyValueRepository<UserAndTokens, String>{

//    private final RedisTemplate<User, String> redisTemplate;
//    private HashOperations<User, Integer, String> hashOperations;
//
//
//    @Autowired
//    public RedisRepositoryImp(RedisTemplate<User, String> redisTemplate){
//        this.redisTemplate = redisTemplate;
//    }
//
//    @PostConstruct
//    private void init(){
//        hashOperations = redisTemplate.opsForHash();
//    }
//
//    public void add(User user, String token) {
//        hashOperations.put(user, user.hashCode(), token);
//    }
//
//    public void delete(User user) {
//        hashOperations.delete(user, hashOperations.get(user, user.hashCode()));
//    }
//
//    public String findToken(User user){
//        return hashOperations.get(user, user.hashCode());
//    }

//    public Map<User, String> findAllUsers(){
//        return hashOperations.entries(hashOperations.keys());
//    }
}

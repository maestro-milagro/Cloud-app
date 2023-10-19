package maestro.milagro.JWTService.repository;

import maestro.milagro.JWTService.model.UserAndTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<UserAndTokens, String> {
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

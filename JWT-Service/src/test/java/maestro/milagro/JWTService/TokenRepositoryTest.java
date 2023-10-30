package maestro.milagro.JWTService;

import jakarta.transaction.Transactional;
import maestro.milagro.JWTService.model.User;
import maestro.milagro.JWTService.model.UserAndTokens;
import maestro.milagro.JWTService.repository.TokenRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class TokenRepositoryTest {
    @Autowired
    private TokenRepository repository;
    User user = new User("Sergey@gmail.com", "123");
    User user1 = new User("Sergey1@gmail.com", "1234");
    UserAndTokens userAndTokens1 = new UserAndTokens(user, "a", "b");
    UserAndTokens userAndTokens2 = new UserAndTokens(user1, "b", "q");

    @Test
    void findByAccessToken(){
        repository.save(userAndTokens1);

        UserAndTokens result = repository.findByAccessToken(userAndTokens1.getAccessToken()).get();

        Assertions.assertEquals(userAndTokens1, result);
    }
    @Test
    void findByUser(){
        repository.save(userAndTokens1);

        UserAndTokens result = repository.findByUser(user).get();

        Assertions.assertEquals(userAndTokens1, result);
    }
    @Transactional
    @Test
    void deleteByAccessTokenTest(){
        repository.save(userAndTokens2);
        repository.deleteByAccessToken(userAndTokens2.getAccessToken());

        Boolean result = repository.findByAccessToken(userAndTokens2.getAccessToken()).isEmpty();

        Assertions.assertEquals(true, result);
    }

}

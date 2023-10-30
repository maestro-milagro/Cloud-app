package maestro.milagro.UserService;

import maestro.milagro.UserService.model.User;
import maestro.milagro.UserService.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    public void findByLoginAndPasswordTest(){
        User user = new User("Sergey@gmail.com", "123");
        userRepository.save(user);

        User result = userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword()).get();

        Assertions.assertEquals(user, result);
    }
}

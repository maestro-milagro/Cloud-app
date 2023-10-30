package maestro.milagro.UserService;

import jakarta.security.auth.message.AuthException;
import maestro.milagro.UserService.client.JWTClient;
import maestro.milagro.UserService.controller.Controller;
import maestro.milagro.UserService.exceptions.BedCredentials;
import maestro.milagro.UserService.model.ResponseLog;
import maestro.milagro.UserService.model.User;
import maestro.milagro.UserService.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@SpringBootTest
public class ControllerTest {
    JWTClient jwtClient = Mockito.mock(JWTClient.class);
    UserRepository userRepository = Mockito.mock(UserRepository.class);

    Controller controller = new Controller(jwtClient, userRepository);
    @Test
    public void loginTest() throws AuthException, BedCredentials {
        User user = new User("Sergey@gmail.com", "123");
        String authToken = "a";
        ResponseLog oldResponseLog = new ResponseLog(authToken);
        ResponseLog newResponseLog = new ResponseLog(authToken);
        Mockito.when(userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword())).thenReturn(Optional.of(user));
        Mockito.when(jwtClient.oldUser(user)).thenReturn(new ResponseEntity<>(oldResponseLog, HttpStatus.OK));
        Mockito.when(jwtClient.newUser(user)).thenReturn(new ResponseEntity<>(newResponseLog, HttpStatus.OK));

        String oldResult = controller.login(user).getBody().getAuthToken();
        String newResult = controller.login(user).getBody().getAuthToken();

        Assertions.assertEquals(authToken, oldResult);
        Assertions.assertEquals(authToken, newResult);
    }
}

package maestro.milagro.JWTService;

import jakarta.security.auth.message.AuthException;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import maestro.milagro.JWTService.model.ResponseLog;
import maestro.milagro.JWTService.model.User;
import maestro.milagro.JWTService.model.UserAndTokens;
import maestro.milagro.JWTService.repository.TokenRepository;
import maestro.milagro.JWTService.service.AuthService;
import maestro.milagro.JWTService.service.JWTProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class AuthServiceTest {
    private final TokenRepository tokenRepository = Mockito.mock(TokenRepository.class);
    private final JWTProvider jwtProvider = Mockito.mock(JWTProvider.class);
    AuthService service = new AuthService(tokenRepository, jwtProvider);
    User user = new User("Sergey@gmail.com", "123");
    UserAndTokens userAndTokens1 = new UserAndTokens(user, "a", "b");
    @Test
    public void loginTest() {
        String accessToken = "a";
        String refreshToken = "b";
        Mockito.when(jwtProvider.generateAccessToken(user)).thenReturn(accessToken);
        Mockito.when(jwtProvider.generateRefreshToken(user)).thenReturn(refreshToken);
        Mockito.when(tokenRepository.save(userAndTokens1)).thenReturn(userAndTokens1);

        String result = service.login(user).getAccessToken();

        Assertions.assertEquals(accessToken, result);
    }
    @Test
    public void findUserTest(){
        String accessToken = "a";
        Mockito.when(jwtProvider.validateAccessToken(accessToken)).thenReturn(true);
        Mockito.when(tokenRepository.findByAccessToken(accessToken)).thenReturn(Optional.of(userAndTokens1));

        User result = service.findUser(accessToken);

        Assertions.assertEquals(user, result);
    }
    @Test
    public void getAccessTokenTest() {
        String accessToken = "c";
        String newAccessToken = "d";
        Mockito.when(tokenRepository.findByAccessToken(accessToken)).thenReturn(Optional.of(userAndTokens1));
        Mockito.when(tokenRepository.save(userAndTokens1)).thenReturn(userAndTokens1);
        Mockito.when(jwtProvider.validateAccessToken(tokenRepository.findByAccessToken(accessToken).get().getRefreshToken())).thenReturn(true);
        Mockito.when(jwtProvider.generateAccessToken(user)).thenReturn(newAccessToken);

        String result = "d";

        Assertions.assertEquals(newAccessToken, result);
    }
    @Test
    public void refreshTest() {
        String accessToken = "c";
        String newAccessToken = "d";
        String refreshToken = "b";
        Mockito.when(tokenRepository.findByAccessToken(accessToken)).thenReturn(Optional.of(userAndTokens1));
        Mockito.when(tokenRepository.save(userAndTokens1)).thenReturn(userAndTokens1);
        Mockito.when(jwtProvider.validateAccessToken(tokenRepository.findByAccessToken(accessToken).get().getRefreshToken())).thenReturn(true);
        Mockito.when(jwtProvider.generateAccessToken(user)).thenReturn(newAccessToken);
        Mockito.when(jwtProvider.generateRefreshToken(user)).thenReturn(refreshToken);

        String result = "d";

        Assertions.assertEquals(newAccessToken, result);
    }
    @Test
    public void getOldTokenTest() {
        String accessToken = "a";
        Mockito.when(jwtProvider.validateAccessToken(accessToken)).thenReturn(true);

        String result = "a";

        Assertions.assertEquals(accessToken, result);
    }
}

package maestro.milagro.JWTService;

import io.jsonwebtoken.Jwts;
import lombok.NonNull;
import maestro.milagro.JWTService.model.User;
import maestro.milagro.JWTService.service.JWTProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
@SpringBootTest
public class JWTProviderTest {
    @Autowired
    private JWTProvider jwtProvider;
    User user = new User("Sergey@gmail.com", "123");
    String oldAccessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnaGYiLCJleHAiOjE2OTg1MTQ0NjUsIlBhc3N3b3JkIjoiNTQ2NiJ9.19TkuQqF0On5fT480Ucmn0vupe9Bqo9m4xxewVVFcskH_DBirVeE37vI0c-rv27V60FvgUtnN-5Epj1x4bkgfw";
    String oldRefreshToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbGV4ZXkiLCJleHAiOjE3MDA5Mjc1MzN9.9LWM5zTa3O8yYF0gOHwnm2_A5oBlofTmIKlYF5smIA_pQLkUmtVxekRS2R-5Bknf7-msBZUeDAom4nX0qtAob";
    @Test
    public void validateAccessTokenTest() {
        Boolean result = jwtProvider.validateAccessToken(oldAccessToken);

        Assertions.assertEquals(false, result);
    }
    @Test
    public void validateRefreshTokenTest() {
        Boolean result = jwtProvider.validateRefreshToken(oldRefreshToken);

        Assertions.assertEquals(false, result);
    }
    @Test
    public void generateAccessTokenTest() {
        String accessToken = jwtProvider.generateAccessToken(user);

        Boolean result = jwtProvider.validateAccessToken(accessToken);

        Assertions.assertEquals(true, result);
    }
    @Test
    public void generateRefreshTokenTest() {
        String refreshToken = jwtProvider.generateRefreshToken(user);

        Boolean result = jwtProvider.validateRefreshToken(refreshToken);

        Assertions.assertEquals(true, result);
    }
}

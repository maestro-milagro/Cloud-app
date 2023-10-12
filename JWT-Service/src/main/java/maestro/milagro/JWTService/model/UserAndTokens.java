package maestro.milagro.JWTService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("user&token")
@AllArgsConstructor
@NoArgsConstructor
public class UserAndTokens {
    User user;
    String accessToken;
    @Id
    String refreshToken;
}

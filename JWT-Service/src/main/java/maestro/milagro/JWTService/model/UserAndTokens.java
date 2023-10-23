package maestro.milagro.JWTService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndTokens {
    private User user;
    private String accessToken;
    private String refreshToken;
}

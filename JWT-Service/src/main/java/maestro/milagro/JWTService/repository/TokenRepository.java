package maestro.milagro.JWTService.repository;

import maestro.milagro.JWTService.model.User;
import maestro.milagro.JWTService.model.UserAndTokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<UserAndTokens, String> {
    Optional<UserAndTokens> findByAccessToken(String accessToken);
    Optional<UserAndTokens> findByUser(User user);
    void deleteByAccessToken(String accessToken);
}

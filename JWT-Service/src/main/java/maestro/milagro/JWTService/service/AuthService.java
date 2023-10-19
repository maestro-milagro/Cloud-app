package maestro.milagro.JWTService.service;

import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import maestro.milagro.JWTService.model.ResponseLog;
import maestro.milagro.JWTService.model.User;
import maestro.milagro.JWTService.model.UserAndTokens;
import maestro.milagro.JWTService.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final TokenRepository redisRepository;
    private final JWTProvider jwtProvider;
    @Autowired
    public AuthService(TokenRepository redisRepository, JWTProvider jwtProvider){
        this.redisRepository = redisRepository;
        this.jwtProvider = jwtProvider;
    }

    public boolean validateAccess(String accessToken){
        return jwtProvider.validateAccessToken(accessToken);
    }
    public boolean validateRef(String refreshToken){
        return jwtProvider.validateRefreshToken(refreshToken);
    }

    public ResponseLog login(@NonNull User user) {
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);
        redisRepository.save(new UserAndTokens(user, accessToken, refreshToken));
        return new ResponseLog(accessToken);
    }

    public ResponseLog getAccessToken(@NonNull String accessToken) throws AuthException {
        String refreshToken = redisRepository.findById(accessToken).get().getRefreshToken();
            if (refreshToken != null) {
                final User user = redisRepository.findById(accessToken).get().getUser();
                if (user == null) {
                    throw new AuthException("Пользователь не найден");
                }
                final String newAccessToken = jwtProvider.generateAccessToken(user);
                return new ResponseLog(newAccessToken);
            }
        return new ResponseLog(null);
    }

    public ResponseLog refresh(@NonNull String accessToken) throws AuthException {
        String refreshToken = redisRepository.findById(accessToken).get().getRefreshToken();
            if (refreshToken != null) {
                final User user = redisRepository.findById(accessToken).get().getUser();
                if (user == null) {
                    throw new AuthException("Пользователь не найден");
                }
                final String newAccessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                redisRepository.save(new UserAndTokens(user, newAccessToken, newRefreshToken));
                return new ResponseLog(newAccessToken);
            }
        throw new AuthException("Невалидный JWT токен");
    }

}

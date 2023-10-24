package maestro.milagro.JWTService.service;

import io.jsonwebtoken.Claims;
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
    private final TokenRepository tokenRepository;
    private final JWTProvider jwtProvider;
    @Autowired
    public AuthService(TokenRepository tokenRepository, JWTProvider jwtProvider){
        this.tokenRepository = tokenRepository;
        this.jwtProvider = jwtProvider;
    }

    public boolean validateAccess(String accessToken){
        return jwtProvider.validateAccessToken(accessToken);
    }
    public boolean validateRef(String refreshToken){
        return jwtProvider.validateRefreshToken(refreshToken);
    }
    public User findUser(String accessToken){
        if(jwtProvider.validateAccessToken(accessToken)) {
            return tokenRepository.findByAccessToken(accessToken).get().getUser();
        }
        return new User(null, null);
    }
    public ResponseLog getOldToken(User user) throws AuthException {
        String accessToken = tokenRepository.findByUser(user).get().getAccessToken();
        if(jwtProvider.validateAccessToken(accessToken)){
            return new ResponseLog(accessToken);
        }
        if(getAccessToken(accessToken).getAccessToken() != null){
            return getAccessToken(accessToken);
        }
        tokenRepository.deleteByAccessToken(accessToken);
        return refresh(accessToken);
    }

    public ResponseLog login(@NonNull User user) {
        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);
        tokenRepository.save(new UserAndTokens(user, accessToken, refreshToken));
        return new ResponseLog(accessToken);
    }

    public ResponseLog getAccessToken(@NonNull String accessToken) throws AuthException {
        String refreshToken = tokenRepository.findByAccessToken(accessToken).get().getRefreshToken();
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final User user = tokenRepository.findByAccessToken(accessToken).get().getUser();
            if (user == null) {
                throw new AuthException("Пользователь не найден");
            }
            final String newAccessToken = jwtProvider.generateAccessToken(user);
            tokenRepository.save(new UserAndTokens(tokenRepository.findByAccessToken(accessToken).get().getUser(), newAccessToken, refreshToken));
            tokenRepository.deleteByAccessToken(accessToken);
            return new ResponseLog(newAccessToken);
        }
        return new ResponseLog(null);
    }

    public ResponseLog refresh(@NonNull String accessToken) throws AuthException {
        String refreshToken = tokenRepository.findByAccessToken(accessToken).get().getRefreshToken();
            if (jwtProvider.validateRefreshToken(refreshToken)) {
                final User user = tokenRepository.findByAccessToken(accessToken).get().getUser();
                if (user == null) {
                    throw new AuthException("Пользователь не найден");
                }
                final String newAccessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                tokenRepository.save(new UserAndTokens(user, newAccessToken, newRefreshToken));
                return new ResponseLog(newAccessToken);
            }
        throw new AuthException("Невалидный JWT токен");
    }

}

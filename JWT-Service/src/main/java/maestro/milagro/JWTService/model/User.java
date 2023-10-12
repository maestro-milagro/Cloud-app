package maestro.milagro.JWTService.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    String login;
    String password;
}

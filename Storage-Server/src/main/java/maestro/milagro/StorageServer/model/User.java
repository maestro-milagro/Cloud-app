package maestro.milagro.StorageServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class User {
//    private String login;
//    private String password;
//}
public record User(String login, String password){}


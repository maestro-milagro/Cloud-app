package maestro.milagro.UserService.model;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="\"user\"")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String login;
    private String password;
}

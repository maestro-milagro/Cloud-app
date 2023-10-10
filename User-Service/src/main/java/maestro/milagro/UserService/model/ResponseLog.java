package maestro.milagro.UserService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseLog {
    @JsonProperty("auth-token") String authToken;
}

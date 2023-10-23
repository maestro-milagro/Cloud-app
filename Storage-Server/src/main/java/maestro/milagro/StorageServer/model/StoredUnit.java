package maestro.milagro.StorageServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoredUnit {
    private String filename;
    private User user;
    private File file;
}

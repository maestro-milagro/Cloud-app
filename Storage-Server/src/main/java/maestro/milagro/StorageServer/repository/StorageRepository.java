package maestro.milagro.StorageServer.repository;

import maestro.milagro.StorageServer.model.StoredUnit;
import maestro.milagro.StorageServer.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends MongoRepository<StoredUnit, String> {
    void deleteByFilename(String filename);
    Optional<StoredUnit> findByFilenameAndUser(String filename, User user);
    List<StoredUnit> findByUser(User user);

}

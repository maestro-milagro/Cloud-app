package maestro.milagro.StorageServer.repository;

import maestro.milagro.StorageServer.model.File;
import maestro.milagro.StorageServer.model.StoredUnit;
import maestro.milagro.StorageServer.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StorageRepository extends MongoRepository<StoredUnit, String> {
    void deleteByFilename(String filename);
    Optional<StoredUnit> findByFilenameAndUser(String filename, User user);
}

package maestro.milagro.StorageServer.repository;

import maestro.milagro.StorageServer.model.File;
import maestro.milagro.StorageServer.model.StoredUnit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StorageRepository extends MongoRepository<StoredUnit, String> {
    void deleteByFilename(String filename);
}

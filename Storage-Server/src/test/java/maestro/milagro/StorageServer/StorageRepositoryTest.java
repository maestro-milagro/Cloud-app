package maestro.milagro.StorageServer;

import maestro.milagro.StorageServer.model.MyFile;
import maestro.milagro.StorageServer.model.StoredUnit;
import maestro.milagro.StorageServer.model.User;
import maestro.milagro.StorageServer.repository.StorageRepository;
import org.bson.types.Binary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
@SpringBootTest
public class StorageRepositoryTest {
    @Autowired
    StorageRepository repository;
    String filename = "text.txt";
    String filename1 = "text1.txt";
    User user = new User("Sergey@gmail.com", "223");
    User user1 = new User("Sergey@gmail1.com", "2231");
    MyFile myFile = new MyFile("d", new Binary(filename.getBytes()));
    StoredUnit storedUnit = new StoredUnit(filename, user, myFile);
    StoredUnit storedUnit1 = new StoredUnit(filename1, user1, myFile);

    @Test
    public void findByUserTest(){
        repository.save(storedUnit);

        StoredUnit result = repository.findByUser(user).get(0);

        Assertions.assertEquals(storedUnit, result);
    }

    @Test
    public void deleteByFilenameTest(){
        repository.save(storedUnit);
        repository.deleteByFilename(storedUnit.getFilename());

        Boolean result = repository.findByUser(storedUnit.getUser()).isEmpty();

        Assertions.assertEquals(true, result);
    }
    @Test
    @Disabled
    public void findByFilenameAndUserTest(){
        repository.save(storedUnit1);

        StoredUnit result = repository.findByFilenameAndUser(filename1, user1).get();
        repository.deleteByFilename(filename1);

        Assertions.assertEquals(storedUnit, result);
    }
}

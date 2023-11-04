package maestro.milagro.StorageServer;

import jakarta.security.auth.message.AuthException;
import maestro.milagro.StorageServer.config.security.JWTClient;
import maestro.milagro.StorageServer.exceptions.BedCredentials;
import maestro.milagro.StorageServer.exceptions.UnauthorizedException;
import maestro.milagro.StorageServer.model.ListUnit;
import maestro.milagro.StorageServer.model.MyFile;
import maestro.milagro.StorageServer.model.StoredUnit;
import maestro.milagro.StorageServer.model.User;
import maestro.milagro.StorageServer.repository.StorageRepository;
import maestro.milagro.StorageServer.service.StorageService;
import org.bson.types.Binary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class StorageServiceTest {
    StorageRepository repository = Mockito.mock(StorageRepository.class);
    JWTClient jwtClient = Mockito.mock(JWTClient.class);
    StorageService service = new StorageService(repository, jwtClient);
    String accessToken = "a";
    String filename = "text.txt";
    User user = new User("Sergey@gmail.com", "123");
    MyFile myFile = new MyFile("d", new Binary(filename.getBytes()));
    StoredUnit storedUnit = new StoredUnit(filename, user, myFile);
    @Test
    public void saveFileTest() throws AuthException, IOException, UnauthorizedException, NoSuchAlgorithmException, BedCredentials {
        String expected = "Success upload";
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);
        Mockito.when(jwtClient.getUser(accessToken)).thenReturn(user);
        Mockito.when(multipartFile.getBytes()).thenReturn(myFile.getHash().getBytes());

        String result = service.saveFile(accessToken, filename, multipartFile).getBody();

        Assertions.assertEquals(expected, result);
    }
    @Test
    public void downloadFileTest() throws UnauthorizedException, AuthException, BedCredentials {
        Resource expected = new ByteArrayResource(myFile.getFile().getData());
        Mockito.when(jwtClient.getUser(accessToken)).thenReturn(user);
        Mockito.when(repository.findByFilenameAndUser(filename, user)).thenReturn(Optional.of(storedUnit));

        Resource result = service.downloadFile(accessToken, filename).getBody();

        Assertions.assertEquals(expected, result);
    }
    @Test
    public void getAllWithLimitTest() throws UnauthorizedException, AuthException, BedCredentials {
        int limit = 2;
        List<ListUnit> expected = new ArrayList<>();
        expected.add(new ListUnit(filename, myFile.getFile().length()));
        Mockito.when(jwtClient.getUser(accessToken)).thenReturn(user);
        Mockito.when(repository.findByUser(user)).thenReturn(List.of(storedUnit));

        List<ListUnit> result = service.getAllWithLimit(accessToken, limit).getBody();

        Assertions.assertEquals(expected, result);

    }
}

package maestro.milagro.StorageServer.service;

import jakarta.security.auth.message.AuthException;
import maestro.milagro.StorageServer.config.security.JWTClient;
import maestro.milagro.StorageServer.exceptions.BedCredentials;
import maestro.milagro.StorageServer.exceptions.UnauthorizedException;
import maestro.milagro.StorageServer.model.MyFile;
import maestro.milagro.StorageServer.model.ListUnit;
import maestro.milagro.StorageServer.model.StoredUnit;
import maestro.milagro.StorageServer.model.User;
import maestro.milagro.StorageServer.repository.StorageRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {
    private final StorageRepository repository;
    private final JWTClient jwtClient;
    @Autowired
    public StorageService(StorageRepository repository, JWTClient jwtClient){
        this.jwtClient = jwtClient;
        this.repository = repository;
    }
    public String saveFile(String authToken, String filename, MultipartFile file) throws AuthException, UnauthorizedException, BedCredentials, IOException, NoSuchAlgorithmException {
        if(authToken == null || filename == null || file == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.login() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        byte[] data = file.getBytes();
        byte[] bytes = MessageDigest.getInstance("MD5").digest(data);
        String hash = new BigInteger(1, bytes).toString(16);
        repository.save(new StoredUnit(filename, user, new MyFile(hash, new Binary(BsonBinarySubType.BINARY,file.getBytes()))));
        return "Success uploaded";
    }
    public String deleteFile(String authToken, String filename) throws BedCredentials, AuthException, UnauthorizedException {
        if(authToken == null || filename == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.login() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        repository.deleteByFilename(filename);

        return "Success deleted";
    }
    public ResponseEntity<Resource> downloadFile(String authToken, String filename) throws BedCredentials, AuthException, UnauthorizedException {
        if(authToken == null || filename == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.login() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        MyFile myFile = repository.findByFilenameAndUser(filename, user).get().getMyFile();
        Resource resource = new ByteArrayResource(myFile.file().getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; hash=\"" + myFile.hash() + "\"")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(resource);
    }
    public List<ListUnit> getAllWithLimit(String authToken, Integer limit) throws BedCredentials, UnauthorizedException, AuthException {
        if(authToken == null || limit == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.login() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        List<StoredUnit> list1 = repository.findByUser(user);
        List<ListUnit> list = new ArrayList<>();
        if(list1.isEmpty()) {
            return list;
        }
        for (StoredUnit s : list1) {
            list.add(new ListUnit(s.getFilename(), s.getMyFile().file().length()));
        }
        if(limit > list.size()){
            return list;
        }
        return  list.subList(0, limit);
    }
    public void editFilename(String authToken, String filename, String name) throws BedCredentials, UnauthorizedException, AuthException {
        if(authToken == null || filename == null){
            throw new BedCredentials("Error input data");
        }
        User user = jwtClient.getUser(authToken);
        if(user.login() == null){
            throw new UnauthorizedException("Unauthorized error");
        }
        StoredUnit storedUnit = repository.findByFilenameAndUser(filename, user).get();
        storedUnit.setFilename(name);
        repository.save(storedUnit);
        repository.deleteByFilename(filename);
    }

}

package maestro.milagro.StorageServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class MyFile {
//    private String hash;
//    private Binary file;
//}
public record MyFile (String hash, Binary file){}

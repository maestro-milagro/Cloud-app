package maestro.milagro.StorageServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//public class ListUnit {
//    String filename;
//    long size;
//}
public record ListUnit(String filename, long size){}

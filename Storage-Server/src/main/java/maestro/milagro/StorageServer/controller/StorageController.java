package maestro.milagro.StorageServer.controller;

import jakarta.security.auth.message.AuthException;
import jakarta.ws.rs.QueryParam;
import maestro.milagro.StorageServer.exceptions.BedCredentials;
import maestro.milagro.StorageServer.exceptions.UnauthorizedException;
import maestro.milagro.StorageServer.model.MyFile;
import maestro.milagro.StorageServer.model.ListUnit;
import maestro.milagro.StorageServer.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
public class StorageController {
    @Autowired
    StorageService service;
    @PostMapping("/file")
    public ResponseEntity<String> saveFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename, @RequestParam("file") MultipartFile file) throws UnauthorizedException, AuthException, BedCredentials, IOException {
        byte[] bytes = file.getBytes();
//        File file1 = new File(filename);
//        BufferedOutputStream stream =
//                new BufferedOutputStream(new FileOutputStream(file1));
//        stream.write(bytes);
//        stream.close();
//        System.out.println(authToken);
//        System.out.println(filename);
//        System.out.println(file);
        StringBuilder s1 = new StringBuilder();
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);
             BufferedInputStream bis = new BufferedInputStream(in)) {
            int c;
            while ((c = bis.read()) != -1) {
//                System.out.print((char) c);
//                s1.append(Integer.toBinaryString(c));
                s1.append((char) c);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        String s2 = s1.toString();
        System.out.println(s2);
        System.out.println(s1.length());
        MyFile myFile1 = new MyFile(filename, s1.toString());
        return service.saveFile(authToken, filename, myFile1);
    }
    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename) throws UnauthorizedException, AuthException, BedCredentials {
        return service.deleteFile(authToken, filename);
    }
    @GetMapping("/file")
    public ResponseEntity<String> downloadFile(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename) throws UnauthorizedException, AuthException, BedCredentials {
        return service.downloadFile(authToken, filename);
    }
    @GetMapping("/list")
    public ResponseEntity<List<ListUnit>> getList(@RequestHeader("auth-token") String authToken, @QueryParam("limit") int limit) throws UnauthorizedException, AuthException, BedCredentials {
        return service.getAllWithLimit(authToken, limit);
    }
    @PutMapping("/file")
    public ResponseEntity<String> editFilename(@RequestHeader("auth-token") String authToken, @QueryParam("filename") String filename, @RequestBody String name) throws UnauthorizedException, AuthException, BedCredentials {
        service.editFilename(authToken, filename, name);
        return new ResponseEntity<>("Success upload", HttpStatus.OK);
    }
}

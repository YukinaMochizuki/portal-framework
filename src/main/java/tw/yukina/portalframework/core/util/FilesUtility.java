package tw.yukina.portalframework.core.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;

public class FilesUtility {

    public static Path getOrCreateDirectory(String pathString) throws IOException {
        Path path = Paths.get(pathString);
        if(!Files.exists(path))Files.createDirectories(path);
        return path;
    }

    public static Path getOrCreateFile(String pathString) throws IOException {
        Path path = Paths.get(pathString);
        if(!Files.exists(path))Files.createFile(path);
        return path;
    }
}
